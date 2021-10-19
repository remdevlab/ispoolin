package org.remdev.executor.execution

import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class ExecutionTest {

    fun subTask(scope: CoroutineScope): Job {
        val job = scope.launch(Dispatchers.IO) {
            println("SubTask started.")
            delay(500L)
            println("SubTask ending.")
        }
        job.invokeOnCompletion(object : CompletionHandler {
            override fun invoke(cause: Throwable?) {
                println("SubTask completed.")
            }
        })
        return job
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `order with context correct`() = runBlockingTest {
        val duration = measureTime {
            runBlocking {
                val job = subTask(this)
                println("Waiting for SubTask")
                job.join()
            }
        }
        println("Duration: $duration")
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `order with context incorrect`() = runBlockingTest {
        val duration = measureTime {
            runBlocking {
                val job = withContext(Dispatchers.IO) {
                    subTask(this)
                }
                println("Waiting for SubTask")
                job.join()
            }
        }
        println("Duration: $duration")
    }

    @Test
    fun `wrong order coroutine dispatcher io`() {
        val scope = CoroutineScope(Dispatchers.IO)
        val counter = AtomicInteger(-1)
        var result = false
        runBlockingTest {
            repeat(1000) {
                scope.launch {
                    if (result) {
                        return@launch
                    }
                    println("Comparing: old value = ${counter.get()}, new value = $it")
                    result = counter.get() >= it
                    counter.set(it)
                }
            }
        }
        assertTrue(result)
    }

    @Test
    fun `wrong order coroutine dispatcher default`() {
        val scope = CoroutineScope(Dispatchers.Default)
        val counter = AtomicInteger(-1)
        var result = false
        runBlockingTest {
            repeat(1000) {
                scope.launch {
                    if (result) {
                        return@launch
                    }
                    println("Comparing: old value = ${counter.get()}, new value = $it")
                    result = counter.get() >= it
                    counter.set(it)
                }
            }
        }
        assertTrue(result)
    }

    @Test
    fun `correct order coroutine dispatcher main`() {
        val scope = CoroutineScope(Executors.newSingleThreadExecutor().asCoroutineDispatcher())
        val counter = AtomicInteger(-1)
        var result = false
        runBlocking {
            repeat(1000) {
                val job = scope.launch {
                    if (result) {
                        return@launch
                    }
                    println("Comparing: old value = ${counter.get()}, new value = $it")
                    result = counter.get() >= it
                    counter.set(it)
                }
                job.invokeOnCompletion(object : CompletionHandler {
                    override fun invoke(cause: Throwable?) {
                        println("SubTask completed for $it")
                    }
                })
                job.join()
            }
        }
        assertFalse(result)
    }
}

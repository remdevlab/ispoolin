package org.remdev.executor.internal.impl

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.yield
import org.remdev.executor.internal.ConflateExecutor
import java.util.concurrent.atomic.AtomicReference

class ConflateExecutorImpl : ConflateExecutor {
    private val activeJob: AtomicReference<Deferred<Unit>?> = AtomicReference(null)

    override suspend fun conflate(block: suspend () -> Unit) {
        activeJob.get()?.cancelAndJoin()

        coroutineScope {
            val newJob = async(start = CoroutineStart.LAZY) { block() }
            newJob.invokeOnCompletion {
                activeJob.compareAndSet(newJob, null)
            }

            while (true) {
                if (!activeJob.compareAndSet(null, newJob)) {
                    activeJob.get()?.cancelAndJoin()
                    yield()
                } else {
                    newJob.await()
                    break
                }
            }
        }
    }
}

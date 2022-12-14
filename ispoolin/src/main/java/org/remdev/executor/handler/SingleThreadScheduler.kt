package org.remdev.executor.handler

import org.remdev.executor.TaskScheduler
import org.remdev.executor.task.UseCase
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class SingleThreadScheduler : TaskScheduler {
    companion object {
        const val POOL_SIZE = 1

        const val MAX_POOL_SIZE = 1

        const val TIMEOUT = 0L
    }

    // private val mHandler = Handler()

    private var mThreadPoolExecutor: ThreadPoolExecutor

    init {
        mThreadPoolExecutor = ThreadPoolExecutor(
            POOL_SIZE, MAX_POOL_SIZE,
            TIMEOUT, TimeUnit.MILLISECONDS,
            LinkedBlockingQueue()
        )
    }

    override fun execute(runnable: Runnable) {
        mThreadPoolExecutor.execute(runnable)
    }

    override fun <V : UseCase.ResponseValue> notifyResponse(
        response: V,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        // mHandler.post { useCaseCallback.onSuccess(response) }
        useCaseCallback.onSuccess(response)
    }

    override fun <V : UseCase.ResponseValue> onError(
        error: UseCase.ErrorData,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        // mHandler.post { useCaseCallback.onError(error) }
        useCaseCallback.onError(error)
    }
}

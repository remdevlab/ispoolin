package org.remdev.executor.handler

import org.remdev.executor.TaskScheduler
import org.remdev.executor.task.UseCase
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class MultiThreadScheduler : TaskScheduler {
    companion object {
        const val POOL_SIZE = 2

        const val MAX_POOL_SIZE = 4

        const val TIMEOUT = 30L
    }

    private var mThreadPoolExecutor: ThreadPoolExecutor

    init {
        mThreadPoolExecutor = ThreadPoolExecutor(
            POOL_SIZE, MAX_POOL_SIZE, TIMEOUT,
            TimeUnit.SECONDS, ArrayBlockingQueue<Runnable>(POOL_SIZE)
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

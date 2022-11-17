package org.remdev.executor.handler

import org.remdev.executor.TaskScheduler
import org.remdev.executor.task.UseCase

class ThisThreadScheduler : TaskScheduler {

    override fun execute(runnable: Runnable) {
        runnable.run()
    }

    override fun <V : UseCase.ResponseValue> notifyResponse(
        response: V,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        useCaseCallback.onSuccess(response)
    }

    override fun <V : UseCase.ResponseValue> onError(
        error: UseCase.ErrorData,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        useCaseCallback.onError(error)
    }
}

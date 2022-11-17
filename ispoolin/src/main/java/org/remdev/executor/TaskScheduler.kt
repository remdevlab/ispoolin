package org.remdev.executor

import org.remdev.executor.task.UseCase

interface TaskScheduler {
    fun execute(runnable: Runnable)

    fun <V : UseCase.ResponseValue> notifyResponse(
        response: V,
        useCaseCallback: UseCase.UseCaseCallback<V>
    )

    fun <V : UseCase.ResponseValue> onError(
        error: UseCase.ErrorData,
        useCaseCallback: UseCase.UseCaseCallback<V>
    )
}

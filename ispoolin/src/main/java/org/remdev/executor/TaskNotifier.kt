package org.remdev.executor

import org.remdev.executor.task.UseCase

interface TaskNotifier {
    suspend fun <V : UseCase.ResponseValue> notifyResponse(
        response: V,
        useCaseCallback: UseCase.UseCaseCallback<V>
    )

    suspend fun <V : UseCase.ResponseValue> onError(
        error: UseCase.ErrorData,
        useCaseCallback: UseCase.UseCaseCallback<V>
    )
}

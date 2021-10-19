package org.remdev.executor.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import org.remdev.executor.TaskScheduler
import org.remdev.executor.task.UseCase

class ThisThreadScheduler : TaskScheduler {

    override suspend fun execute(action: suspend () -> Unit) {
        action.invoke()
    }

    override suspend fun invoke(action: suspend () -> Unit): Job = CoroutineScope(currentCoroutineContext()).launch {
        action.invoke()
    }

    override suspend fun <V : UseCase.ResponseValue> notifyResponse(
        response: V,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        useCaseCallback.onSuccess(response)
    }

    override suspend fun <V : UseCase.ResponseValue> onError(
        error: UseCase.ErrorData,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        useCaseCallback.onError(error)
    }
}

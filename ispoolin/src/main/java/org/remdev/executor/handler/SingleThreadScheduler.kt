package org.remdev.executor.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import org.remdev.executor.TaskScheduler
import org.remdev.executor.task.UseCase

class SingleThreadScheduler : TaskScheduler {

    @OptIn(ObsoleteCoroutinesApi::class)
    private var context = newSingleThreadContext("THREAD:ISPOOLIN:SingleThreadScheduler")

    override suspend fun execute(action: suspend () -> Unit) = withContext(context) {
        action.invoke()
    }

    override suspend fun invoke(action: suspend () -> Unit): Job = CoroutineScope(context).launch {
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

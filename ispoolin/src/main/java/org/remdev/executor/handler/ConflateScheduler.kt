package org.remdev.executor.handler

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import org.remdev.executor.TaskScheduler
import org.remdev.executor.internal.ConflateExecutor
import org.remdev.executor.internal.impl.ConflateExecutorImpl
import org.remdev.executor.task.UseCase

class ConflateScheduler : TaskScheduler {

    private var executor: ConflateExecutor = ConflateExecutorImpl()

    @OptIn(ObsoleteCoroutinesApi::class)
    private var context = newSingleThreadContext("THREAD:ISPOOLIN:ConflateScheduler")

    override suspend fun execute(action: suspend () -> Unit) = executor.conflate {
        action.invoke()
    }

    override suspend fun invoke(action: suspend () -> Unit): Job = CoroutineScope(context).launch {
        executor.conflate { action.invoke() }
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

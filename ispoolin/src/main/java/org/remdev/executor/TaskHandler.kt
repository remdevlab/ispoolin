package org.remdev.executor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.remdev.executor.task.UseCase
import java.util.concurrent.Executors
import java.util.logging.Level

class TaskHandler(private val mUseCaseScheduler: TaskScheduler) : TaskExecutor, TaskNotifier {
    private val context = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Deprecated(
        message = "put values to usecase constructor",
        replaceWith = ReplaceWith("execute(useCase: UseCase, callback: UseCase.UseCaseCallback)")
    )
    override fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
        useCase: UseCase<T, R>,
        values: T,
        callback: UseCase.UseCaseCallback<R>
    ) {
        useCase.requestValues = values
        execute(useCase, UiCallbackWrapper(callback, this))
    }

    override fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
        useCase: UseCase<T, R>,
        callback: UseCase.UseCaseCallback<R>
    ) {
        CoroutineScope(context).launch {
            run(useCase, callback)
        }
    }

    override fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(useCase: UseCase<T, R>) {
        execute(useCase, UiCallbackWrapper(UseCase.SilentCallback(), this))
    }

    override suspend fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> run(
        useCase: UseCase<T, R>,
        callback: UseCase.UseCaseCallback<R>
    ) {
        useCase.useCaseCallback = UiCallbackWrapper(callback, this)
        mUseCaseScheduler.execute {
            doAction(useCase)
        }
    }

    override suspend fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> invoke(
        useCase: UseCase<T, R>,
        callback: UseCase.UseCaseCallback<R>
    ): Job {
        useCase.useCaseCallback = UiCallbackWrapper(callback, this)
        return mUseCaseScheduler.invoke {
            doAction(useCase)
        }
    }

    private suspend fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> doAction(useCase: UseCase<T, R>) {
        Ispoolin.logger.log(Level.INFO, "Start executing task : ${useCase.taskName}")
        try {
            useCase.run()
        } catch (e: Throwable) {
            Ispoolin.logger.log(Level.INFO, e.toString(), e)
            useCase.useCaseCallback?.onError(UseCase.ExecutionError(e.toString(), e))
        }
        Ispoolin.logger.log(Level.INFO, "Finish executing task : ${useCase.taskName}")
    }

    override suspend
    fun <V : UseCase.ResponseValue> notifyResponse(
        response: V,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback)
    }

    override suspend
    fun <V : UseCase.ResponseValue> onError(
        error: UseCase.ErrorData,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        mUseCaseScheduler.onError(error, useCaseCallback)
    }

    private class UiCallbackWrapper<V : UseCase.ResponseValue>(
        private val mCallback: UseCase.UseCaseCallback<V>,
        private val mUseCaseHandler: TaskNotifier
    ) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) = runBlocking {
            mUseCaseHandler.notifyResponse(response, mCallback)
        }

        override fun onError(error: UseCase.ErrorData) = runBlocking {
            mUseCaseHandler.onError(error, mCallback)
        }
    }

    companion object {
        fun getInstance(scheduler: TaskScheduler): TaskHandler {
            return TaskHandler(scheduler)
        }
    }
}

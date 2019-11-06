package org.remdev.executor

import org.remdev.executor.task.UseCase
import java.util.logging.Level


class TaskHandler(private val mUseCaseScheduler: TaskScheduler) {

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
        useCase: UseCase<T, R>, values: T, callback: UseCase.UseCaseCallback<R>
    ) {
        useCase.requestValues = values
        useCase.useCaseCallback = UiCallbackWrapper(callback, this)

        mUseCaseScheduler.execute(Runnable {
            Ispoolin.logger.log(Level.INFO, "Start executing task : ${useCase.taskName}")
            try {
                useCase.run()
            } catch (e: Throwable) {
                Ispoolin.logger.log(Level.INFO, e.toString(), e)
            }
            Ispoolin.logger.log(Level.INFO, "Finish executing task : ${useCase.taskName}")
        })
    }

    fun <V : UseCase.ResponseValue> notifyResponse(response: V, useCaseCallback: UseCase.UseCaseCallback<V>) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback)
    }

    private fun <V : UseCase.ResponseValue> notifyError(
        error: UseCase.ErrorData,
        useCaseCallback: UseCase.UseCaseCallback<V>
    ) {
        mUseCaseScheduler.onError(error, useCaseCallback)
    }

    private class UiCallbackWrapper<V : UseCase.ResponseValue>(
        private val mCallback: UseCase.UseCaseCallback<V>,
        private val mUseCaseHandler: TaskHandler
    ) : UseCase.UseCaseCallback<V> {

        override fun onSuccess(response: V) {
            mUseCaseHandler.notifyResponse(response, mCallback)
        }

        override fun onError(error: UseCase.ErrorData) {
            mUseCaseHandler.notifyError(error, mCallback)
        }
    }

    companion object {
        fun getInstance(scheduler: TaskScheduler): TaskHandler {
            return TaskHandler(scheduler)
        }
    }
}
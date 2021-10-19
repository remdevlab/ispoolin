package org.remdev.executor

import kotlinx.coroutines.Job
import org.remdev.executor.task.UseCase

interface TaskExecutor {
    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
        useCase: UseCase<T, R>,
        values: T,
        callback: UseCase.UseCaseCallback<R>
    )

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
        useCase: UseCase<T, R>,
        callback: UseCase.UseCaseCallback<R>
    )

    fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> execute(
        useCase: UseCase<T, R>
    )

    suspend fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> run(
        useCase: UseCase<T, R>,
        callback: UseCase.UseCaseCallback<R>
    )

    suspend fun <T : UseCase.RequestValues, R : UseCase.ResponseValue> invoke(
        useCase: UseCase<T, R>,
        callback: UseCase.UseCaseCallback<R>
    ): Job
}

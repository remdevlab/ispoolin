package org.remdev.executor.task

import org.remdev.executor.Ispoolin
import java.util.Date
import java.util.logging.Level

abstract class UseCase<Q : UseCase.RequestValues, P : UseCase.ResponseValue>(
    var requestValues: Q? = null,
    val taskType: Int,
    val taskName: String,
    val createdTime: Long,
    val priority: TaskPriority
) : Comparable<UseCase<Q, P>> {

    constructor(requestValues: Q? = null, taskType: Int, taskName: String) : this(
        requestValues,
        taskType,
        taskName,
        Date().time,
        TaskPriority.MIDDLE
    )

    var useCaseCallback: UseCaseCallback<P>? = null

    suspend fun run() {
        if (validate(requestValues)) {
            invoke(requestValues)
        } else {
            Ispoolin.logger.log(Level.INFO, UseCase::class.simpleName, "Task ignored because invalid")
        }
    }

    open suspend fun validate(requestValues: Q?): Boolean {
        return true
    }

    abstract suspend fun invoke(requestValue: Q?)

    /**
     * Data passed to a request.
     */
    interface RequestValues

    class NOTHING_IN : RequestValues

    /**
     * Data received from a request.
     */
    interface ResponseValue

    class NOTHING_OUT : ResponseValue

    interface UseCaseCallback<R> {
        fun onSuccess(response: R)
        fun onError(error: ErrorData)
    }

    open class SilentCallback<R>() : UseCaseCallback<R> {
        override fun onSuccess(response: R) {
            Ispoolin.logger.log(Level.INFO, "TASK  onSuccess : $response")
            onAny()
        }

        override fun onError(error: ErrorData) {
            Ispoolin.logger.log(Level.WARNING, "TASK  onError : $error")
            onAny()
        }

        open fun onAny() {
        }
    }

    open class ErrorData(val message: String, val code: Int, val exception: Throwable? = null) {
        override fun toString(): String {
            return "ErrorData(message='$message', code=$code, exception=$exception)"
        }
    }

    internal class ExecutionError(message: String, exception: Throwable? = null) :
        ErrorData(message, Int.MAX_VALUE, exception)

    override fun compareTo(other: UseCase<Q, P>): Int {
        var result = other.priority.compareTo(priority)
        if (result == 0) {
            result = other.createdTime.compareTo(createdTime)
        }
        return result
    }

    override fun toString(): String {
        return "Task(taskType='$taskType', taskName='$taskName', createdTime=$createdTime, taskPriority=$priority)"
    }
}

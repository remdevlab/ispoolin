package org.remdev.executor

import kotlinx.coroutines.Job

interface TaskScheduler : TaskNotifier {
    suspend fun execute(action: suspend () -> Unit)
    suspend fun invoke(action: suspend () -> Unit): Job
}

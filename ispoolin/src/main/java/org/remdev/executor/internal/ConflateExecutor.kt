package org.remdev.executor.internal

internal interface ConflateExecutor {
    /**
     * @param block is executed after all previous tasks are canceled.
     *
     * When several coroutines conflate at the time, only one will run and
     * the others will be cancelled.
     */
    suspend fun conflate(block: suspend () -> Unit)
}

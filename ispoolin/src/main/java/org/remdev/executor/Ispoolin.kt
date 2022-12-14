package org.remdev.executor

import org.remdev.executor.handler.SingleThreadScheduler
import org.remdev.executor.handler.ThisThreadScheduler
import java.util.logging.Logger

class Ispoolin {

    var single: TaskHandler = TaskHandler.getInstance(SingleThreadScheduler())
    var multi: TaskHandler = TaskHandler.getInstance(SingleThreadScheduler())
    var ui: TaskHandler = TaskHandler.getInstance(SingleThreadScheduler())
    var current: TaskHandler = TaskHandler.getInstance(ThisThreadScheduler())

    fun setupUiHandler(scheduler: TaskScheduler) {
        ui = TaskHandler.getInstance(scheduler)
    }

    fun setupSingleThreadHandler(scheduler: TaskScheduler) {
        single = TaskHandler.getInstance(scheduler)
    }

    fun setupMultiThreadHandler(scheduler: TaskScheduler) {
        multi = TaskHandler.getInstance(scheduler)
    }

    fun setupCurrentThreadHandler(scheduler: TaskScheduler) {
        current = TaskHandler.getInstance(scheduler)
    }

    companion object {
        const val TAG = "Ispoolin"
        internal val logger = Logger.getLogger("Ispoolin")
    }
}

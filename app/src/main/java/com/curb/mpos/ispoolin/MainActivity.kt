package com.curb.mpos.ispoolin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.remdev.executor.Ispoolin
import org.remdev.executor.task.UseCase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Ispoolin().current.execute(TestUseCase(), UseCase.NOTHING_IN(),object : UseCase.UseCaseCallback<UseCase.NOTHING_OUT>{
            override fun onSuccess(response: UseCase.NOTHING_OUT) {
                Log.w("ISPOOLIN", "on success")
            }

            override fun onError(error: UseCase.ErrorData) {
                Log.w("ISPOOLIN", "on failed")
            }

        })
    }

    class TestUseCase: UseCase<UseCase.NOTHING_IN, UseCase.NOTHING_OUT>(1,"test") {
        override fun invoke(requestValue: NOTHING_IN?) {
            Log.w("ISPOOLIN", "executing")
            useCaseCallback?.onSuccess(NOTHING_OUT())
        }
    }
}

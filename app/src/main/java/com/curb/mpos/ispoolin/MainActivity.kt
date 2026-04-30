package com.curb.mpos.ispoolin

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.remdev.android.prefz.Prefz
import org.remdev.executor.Ispoolin
import org.remdev.executor.task.UseCase

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val helper = Prefz.getInstanceByName("test1", this)
        if (helper.getBoolean("test1", false)) {
            Toast.makeText(this, "test1", Toast.LENGTH_SHORT).show()
        } else {
            helper.putBoolean("test1", true)
        }
        Ispoolin().current.execute(
            TestUseCase(), UseCase.NOTHING_IN(),
            object : UseCase.UseCaseCallback<UseCase.NOTHING_OUT> {
                override fun onSuccess(response: UseCase.NOTHING_OUT) {
                    Log.w("ISPOOLIN", "on success")
                }

                override fun onError(error: UseCase.ErrorData) {
                    Log.w("ISPOOLIN", "on failed")
                }
            }
        )
    }

    class TestUseCase : UseCase<UseCase.NOTHING_IN, UseCase.NOTHING_OUT>(NOTHING_IN(), 1, "test") {
        override suspend fun invoke(requestValue: NOTHING_IN?) {
            Log.w("ISPOOLIN", "executing")
            useCaseCallback?.onSuccess(NOTHING_OUT())
        }
    }
}

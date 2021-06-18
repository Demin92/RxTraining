package ru.demin.rxtraining

import android.annotation.SuppressLint
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.IOException

object ListOfSingleToSingleOfList {

    @SuppressLint("CheckResult")
    fun tryList() {
        val singles = List(5) { index -> index }.map {
            Single.create<Int> { emitter ->
                if (it == 3) {
                    emitter.onError(IOException())
                } else {
                    Thread.sleep(2000)
                    log("emit $it")
                    emitter.onSuccess(it)
                }
            }
        }
        Single.concat(singles).toList().subscribeOn(Schedulers.io()).subscribe({
            log("success result is $it")
        }, {
            log("error $it")
        })
    }
}
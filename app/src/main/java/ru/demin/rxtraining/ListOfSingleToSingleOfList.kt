package ru.demin.rxtraining

import android.annotation.SuppressLint
import io.reactivex.Single
import java.io.IOException

object ListOfSingleToSingleOfList {

    @SuppressLint("CheckResult")
    fun tryList() {
        val singles = List(5) { index -> index }.map {
            Single.create<Int> { emitter ->
                if (it == 3) emitter.onError(IOException()) else emitter.onSuccess(it)
            }
        }
        Single.concat(singles).toList().subscribe({
            log("success result is $it")
        }, {
            log("error $it")
        })
    }
}
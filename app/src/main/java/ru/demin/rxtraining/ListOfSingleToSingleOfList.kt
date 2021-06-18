package ru.demin.rxtraining

import android.annotation.SuppressLint
import io.reactivex.Single

object ListOfSingleToSingleOfList {

    @SuppressLint("CheckResult")
    fun tryList() {
        val singles = List(5) { index -> index }.map { Single.create<Int> { emitter -> emitter.onSuccess(it) } }
        Single.concat(singles).toList().subscribe({
            log("success result is $it")
        }, {})
    }
}
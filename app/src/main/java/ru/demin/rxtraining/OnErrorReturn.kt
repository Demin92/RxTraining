package ru.demin.rxtraining

import android.annotation.SuppressLint
import io.reactivex.Observable
import java.lang.RuntimeException


object OnErrorReturn {

    private val param = true
    private val firstObservable = Observable.create<Int> { emitter ->
        if (param) emitter.onError(RuntimeException("test"))
        else emitter.onNext(10)
    }

    private val secondObservable = Observable.range(100, 110).map {
        if (it < 103) it
        else throw RuntimeException("second observer")
    }.onErrorReturn { 100500 }

    @SuppressLint("CheckResult")
    fun tryOnErrorReturn() {
        firstObservable
            .onErrorReturn { 1 }
            .onErrorReturn { 2 }
            .flatMap { secondObservable }
            .onErrorReturn { 10 }//относится ко 2му Observable
            .subscribe({
                log("$it")
            }, {
                log("Error")
            },{
                log("onComplete")
            })
    }
}
package ru.demin.rxtraining

import android.annotation.SuppressLint
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit
import kotlin.math.log
import kotlin.text.Typography.times

object RetryWhen {
    private var emitCount = 0

    /*Observable который выдаёт либо ошибку, если подписывались меньше 5 раз, либо число*/
    private val observable = Observable.create<Int> { emitter ->
        emitCount++
        log("emit $emitCount")
        if (emitCount < 5) emitter.onError(RuntimeException("test"))
        else (emitter.onNext(3))
    }

    /*retryWhen с задержкой и предикатом*/
    @SuppressLint("CheckResult")
    fun tryRetry() {
        log("Start")
        observable
            .retryWhen {
                it.map { throwable -> if (throwable.message == "testq") throwable else throw throwable }
                    .delay(1, TimeUnit.SECONDS)
            }
            .subscribe({
                log("Success $it")
            }, {
                log("Error")
            },{
                log("onComplete")
            })
    }

}
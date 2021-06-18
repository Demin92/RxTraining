package ru.demin.rxtraining

import android.annotation.SuppressLint
import io.reactivex.Observable
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit

object RetryWhen {
    private var emitCount = 0

    /*Observable который выдаёт либо ошибку с временем retry,
     если подписывались меньше 5 раз, а потом кидает ошибку через несколько подписок*/
    private val observable = Observable.create<Int> { emitter ->
        log("again subscribe")
        emitCount++
        log("emit $emitCount")
        if (emitCount < 5) emitter.onError(RetryException(emitCount.toLong()))
        else {
            repeat(5) {
                emitCount++
                emitter.onNext(emitCount)
                if (emitCount > 8) throw IOException()
            }
        }
    }

    /*retryWhen с задержкой и предикатом*/
    @SuppressLint("CheckResult")
    fun tryRetry() {
        log("Start")
        observable
            .retryWhen{ observable->
                observable.flatMap { throwable ->
                    log("retry")
                    if (throwable is RetryException) Observable.timer(throwable.delay, TimeUnit.SECONDS)
                    else throw throwable
                }

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

class RetryException(val delay: Long): Exception()
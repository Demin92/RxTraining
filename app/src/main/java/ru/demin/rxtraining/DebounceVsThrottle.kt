package ru.demin.rxtraining

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object DebounceVsThrottle {
    fun tryIt() {
        val observable = Observable.create<Int> { emitter ->
            repeat(10) {
                Thread.sleep(1000)
                emitter.onNext(it)
            }
        }

        log("start")

        val debounceDisposable = observable
            .debounce(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ log("debounce onNext $it") }, { log("error $it") })

        val disposableJust = observable
            .throttleLast(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ log("throttle onNext $it") }, { log("error $it") })
    }
}
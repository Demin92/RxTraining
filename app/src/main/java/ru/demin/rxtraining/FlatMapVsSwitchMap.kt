package ru.demin.rxtraining

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object FlatMapVsSwitchMap {
    fun tryIt() {
        val observable = Observable.create<Int> { emitter ->
            repeat(10) {
                Thread.sleep(1000)
                emitter.onNext(it)
            }
        }

        log("start")

        val debounceFlatMap = observable
            .flatMap {
                Observable.fromCallable { it }
                    .delay(2, TimeUnit.SECONDS)
                    .doOnDispose { log("flatMap disposed $it") }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ log("flatMap onNext $it") }, { log("error $it") })

        val disposableSwitchMap = observable
            .switchMap {
                Observable.fromCallable { it }
                    .delay(2, TimeUnit.SECONDS)
                    .doOnDispose { log("switchMap disposed $it") }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ log("switchMap onNext $it") }, { log("error $it") })
    }
}
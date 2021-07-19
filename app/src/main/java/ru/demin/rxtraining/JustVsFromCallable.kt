package ru.demin.rxtraining

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object JustVsFromCallable {
    fun tryIt() {
        val singleFromCallable = Single.fromCallable {
            log("singleFromCallable ${Thread.currentThread().name}")
            1
        }

        val singleJust = Single.just(
            1.also{ log("singleJust ${Thread.currentThread().name}") }
        )

        log("start")

        val disposable = singleFromCallable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ log("success singleFromCallable $it") }, { log("error $it") })

        val disposableJust = singleJust
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ log("success singleJust $it") }, { log("error $it") })
    }
}
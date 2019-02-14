package ru.demin.rxtraining

import android.annotation.SuppressLint
import io.reactivex.Observable
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit

object TryConcatWith {
    private val firstObservable = Observable.intervalRange(0, 5, 0, 1, TimeUnit.SECONDS)
    private val secondObservable = Observable.intervalRange(5, 5, 1, 1, TimeUnit.SECONDS)

    /**
     * Объеденяет все элементы выпускаемые обоими Observable, не перемешивая их.
     * То есть с начала все элементы первого, потом все элементы второго
     */
    @SuppressLint("CheckResult")
    fun tryConcatWith() {
        secondObservable.concatWith(firstObservable)
            .subscribe {
                log("$it")
            }
    }

    /**
     * Ошибка прерывает цепочку как обычно
     */
    @SuppressLint("CheckResult")
    fun tryConcatWithError() {
        secondObservable
            .map { if (it == 7L) throw IllegalArgumentException("7 nooooo!") else it }
            .concatWith(firstObservable)
            .subscribe({
                log("$it")
            },
                {
                    log("Error $it")
                })
    }
}
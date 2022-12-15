package ru.demin.rxtraining

import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object ZipInParallel {
    fun tryIt() {
        val list = mutableListOf<Int>().apply {
            repeat(10) { add(it) }
        }

        Single.zip(
            list.map { singleNum ->
                Single.fromCallable {
                    repeat(10) {
                        Thread.sleep(100)
                        Log.d("Povarity", "$singleNum $it ${Thread.currentThread().name}")
                    }
                    return@fromCallable singleNum
                }//если убрать subscribeOn, то всё будет выполняться на одном потоке последовательно
                    .subscribeOn(Schedulers.io())
            }
        ) {
            it.toList()
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Povarity", "finish $it")
            }, {})
    }
}
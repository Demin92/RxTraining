package ru.demin.rxtraining

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import ru.demin.rxtraining.OnErrorReturn.tryOnErrorReturn

class MainActivity : AppCompatActivity() {

    private val behaviorSubject : BehaviorSubject<Int> = BehaviorSubject.create()

    private val single = Single.fromCallable {
        Thread.sleep(5000)
        behaviorSubject.onNext(1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        single.subscribeOn(Schedulers.io()).subscribe()

        subscribe.setOnClickListener {
            Log.d("Povarity", "Click!")
            behaviorSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                      Log.d("Povarity", "get result = $it on ${Thread.currentThread().name}")
                }, {

                })
        }
    }
}

package ru.demin.rxtraining

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.demin.rxtraining.TryConcatWith.tryConcatWithError

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tryConcatWithError()
    }
}

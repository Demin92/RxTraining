package ru.demin.rxtraining

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.demin.rxtraining.OnErrorReturn.tryOnErrorReturn

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tryOnErrorReturn()
    }
}

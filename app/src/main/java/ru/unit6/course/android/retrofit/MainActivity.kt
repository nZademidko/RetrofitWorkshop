package ru.unit6.course.android.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.unit6.course.android.retrofit.ui.main.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.itemContainer, MainFragment.newInstance())
                .commitNow()
        }
    }
}
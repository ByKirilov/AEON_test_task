package com.example.aeon_test_task.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aeon_test_task.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(
                            R.id.fragment_container,
                            AuthFragment.newInstance(false),
                            "tag"
                    )
                    .commit()
        }
    }
}
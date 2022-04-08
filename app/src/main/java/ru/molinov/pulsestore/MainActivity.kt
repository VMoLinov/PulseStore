package ru.molinov.pulsestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.molinov.pulsestore.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment(), "false")
            .commit()
    }
}

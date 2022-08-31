package it.bailettitommaso.lupus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Lupus_NoActionBar)
        setContentView(R.layout.landing_activity)
    }
}
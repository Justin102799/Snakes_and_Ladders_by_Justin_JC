package ph.stacktrek.novare.snakesandladders.delacruz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ph.stacktrek.novare.snakesandladders.delacruz.databinding.ActivityGamepageBinding

class GamepageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startGame.setOnClickListener {
            val gotoMainActivity = Intent(
                applicationContext,
                MainActivity::class.java
            )

            startActivity(gotoMainActivity)
            finish()
        }
    }
}
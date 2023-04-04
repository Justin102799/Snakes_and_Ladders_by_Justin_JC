package ph.stacktrek.novare.snakesandladders.delacruz

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.snakesandladders.delacruz.adapter.PlayersAdapter
import ph.stacktrek.novare.snakesandladders.delacruz.databinding.ActivityMainBinding
import ph.stacktrek.novare.snakesandladders.delacruz.databinding.AddPlayerBinding
import ph.stacktrek.novare.snakesandladders.delacruz.model.Players
import ph.stacktrek.novare.snakesandladders.delacruz.model.PlayersDAO
import ph.stacktrek.novare.snakesandladders.delacruz.model.PlayersDAOStubImplementation
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.instructions -> changeFragment(InstructionsFragment())
                R.id.leader_boards -> changeFragment(LeaderboardsFragment())
                else -> {

                }
            }
            true
        }
    }
    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


}







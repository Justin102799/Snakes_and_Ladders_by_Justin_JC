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
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.snakesandladders.delacruz.adapter.PlayersAdapter
import ph.stacktrek.novare.snakesandladders.delacruz.databinding.ActivityUserProfileBinding
import ph.stacktrek.novare.snakesandladders.delacruz.databinding.AddPlayerBinding
import ph.stacktrek.novare.snakesandladders.delacruz.model.Players
import ph.stacktrek.novare.snakesandladders.delacruz.model.PlayersDAO
import ph.stacktrek.novare.snakesandladders.delacruz.model.PlayersDAOStubImplementation
import java.io.File
import java.io.FileOutputStream

class UserProfileActivity : AppCompatActivity() {


    private lateinit var playerAdapter: PlayersAdapter
    private lateinit var parentView: ViewGroup
    private lateinit var playerDAO: PlayersDAO
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var recyclerView: RecyclerView
    private var fragmentPage = 0
    private var numPlayers = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val frameLayout = findViewById<FrameLayout>(R.id.frame1)
        val textView = TextView(this)
        frameLayout.addView(textView)

        binding.addButton.setOnClickListener {
            if (numPlayers > 0) {
                showAddPlayerDialogue().show()
            } else {
                Toast.makeText(applicationContext, "Max players exceeded!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.startButton.setOnClickListener {

            if (numPlayers != 5) {
                fragmentPage = 1
                parentView = binding.playersList.parent as ViewGroup

                binding.addButton.visibility = View.INVISIBLE
                binding.startButton.visibility = View.INVISIBLE

                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()

                val fragment = GameProperFragment()

                val bundle = Bundle()
                bundle.putParcelableArrayList("key", playerDAO.getPlayers())
                fragment.arguments = bundle
                fragmentTransaction.replace(R.id.frame1, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

            } else {
                Toast.makeText(applicationContext, "Add player first", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        loadPlayers()


    }

    fun loadPlayers() {
        playerDAO = PlayersDAOStubImplementation()
        playerAdapter =
            PlayersAdapter(applicationContext, playerDAO.getPlayers() as ArrayList<Players>)
        with(binding.playersList) {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = playerAdapter

        }
    }

    fun setPlayerCount() {
        numPlayers = 5
    }

    fun showAddPlayerDialogue(): Dialog {
        return this!!.let {
            val builder = AlertDialog.Builder(it)
            var dialogueAddPlayerBinding: AddPlayerBinding =
                AddPlayerBinding.inflate(it.layoutInflater)
            with(builder) {

                var imagePath = "a"

                val image = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.c1
                )
                val file = File(applicationContext.filesDir, "default.jpg")
                val fileOutputStream = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()
                imagePath = file.absolutePath

                dialogueAddPlayerBinding.color1.setOnClickListener {
                    val image = BitmapFactory.decodeResource(
                        applicationContext.resources,
                        R.drawable.c1
                    )
                    val file = File(applicationContext.filesDir, "color1.jpg")
                    val fileOutputStream = FileOutputStream(file)
                    image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    imagePath = file.absolutePath

                }

                dialogueAddPlayerBinding.color2.setOnClickListener {
                    val image = BitmapFactory.decodeResource(
                        applicationContext.resources,
                        R.drawable.c2
                    )
                    val file = File(applicationContext.filesDir, "color2.jpg")
                    val fileOutputStream = FileOutputStream(file)
                    image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    imagePath = file.absolutePath

                }

                dialogueAddPlayerBinding.color3.setOnClickListener {
                    val image = BitmapFactory.decodeResource(
                        applicationContext.resources,
                        R.drawable.c3
                    )
                    val file = File(applicationContext.filesDir, "color3.jpg")
                    val fileOutputStream = FileOutputStream(file)
                    image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    imagePath = file.absolutePath

                }

                dialogueAddPlayerBinding.color4.setOnClickListener {
                    val image = BitmapFactory.decodeResource(
                        applicationContext.resources,
                        R.drawable.c4
                    )
                    val file = File(applicationContext.filesDir, "color4.jpg")
                    val fileOutputStream = FileOutputStream(file)
                    image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    imagePath = file.absolutePath

                }

                dialogueAddPlayerBinding.color5.setOnClickListener {
                    val image = BitmapFactory.decodeResource(
                        applicationContext.resources,
                        R.drawable.c5
                    )
                    val file = File(applicationContext.filesDir, "color5.jpg")
                    val fileOutputStream = FileOutputStream(file)
                    image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
                    fileOutputStream.flush()
                    fileOutputStream.close()
                    imagePath = file.absolutePath

                }

                setPositiveButton("ADD", DialogInterface.OnClickListener { dialog, id ->
                    val name = dialogueAddPlayerBinding.playerNameText.text.toString().trim()
                    if (name.isNotEmpty()) {

                        val player = Players(name)
                        player.imagePath = imagePath;
                        val playerDAO = PlayersDAOStubImplementation()


                        playerDAO.addPlayers(player)

                        playerAdapter.addPlayer(player)
                        numPlayers -= 1

                        println("heres the new player PPPPOSSITION ${player.position}")


                    } else {
                        Toast.makeText(context, "Player name  cannot be empty", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
                setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialog, id ->

                })
                setView(dialogueAddPlayerBinding.root)
                create()
            }
        }
    }

    override fun onBackPressed() {


        println("the page is ${fragmentPage}")

        if (fragmentPage == 1) {
            binding.addButton.visibility = View.VISIBLE
            binding.startButton.visibility = View.VISIBLE
            fragmentPage = 0;
        } else {
            fragmentPage = fragmentPage - 1;
        }


        recyclerView = binding.playersList

        if (recyclerView.parent == null) {
            binding.frame1.addView(recyclerView)

        }


        val fragmentManager = supportFragmentManager
        val backStackEntryCount = fragmentManager.backStackEntryCount



        if (backStackEntryCount > 0) {
            // Remove the latest fragment from the back stack
            fragmentManager.popBackStack(
                fragmentManager.getBackStackEntryAt(backStackEntryCount - 1).id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        } else {
            super.onBackPressed()
        }
    }

    fun updatePlayerList(playerList: List<Players>) {
        playerAdapter.playerList = playerList as ArrayList<Players>
        val playerDAO = PlayersDAOStubImplementation()

        playerAdapter.notifyDataSetChanged()
    }

    fun removeBoardFragment() {

        val fragmentManager = supportFragmentManager
        val backStackEntryCount = fragmentManager.backStackEntryCount
        binding.addButton.visibility = View.VISIBLE
        binding.startButton.visibility = View.VISIBLE


        if (backStackEntryCount > 0) {
            fragmentManager.popBackStack(
                fragmentManager.getBackStackEntryAt(backStackEntryCount - 1).id,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }

    }

    fun setWinner(players: Players) {
        val fragment = LeaderboardsFragment.newInstance("param1", "param2")
        fragment.addWinner(players, applicationContext)
    }

}

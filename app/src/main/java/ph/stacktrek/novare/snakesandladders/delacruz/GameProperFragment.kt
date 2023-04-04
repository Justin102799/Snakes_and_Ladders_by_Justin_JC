package ph.stacktrek.novare.snakesandladders.delacruz

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import ph.stacktrek.novare.snakesandladders.delacruz.databinding.FragmentGameProperBinding
import ph.stacktrek.novare.snakesandladders.delacruz.databinding.WinnerBinding
import ph.stacktrek.novare.snakesandladders.delacruz.model.Players

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameProperFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameProperFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var winner: Players
    private lateinit var binding: FragmentGameProperBinding
    private lateinit var player: Players;
    private var param1: String? = null
    private var param2: String? = null
    private var currentPlayer = 0;
    private var playersArrayList = ArrayList<Players>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val players = arguments?.getParcelableArrayList<Players>("key");
        if (players != null) {
            playersArrayList = players
        }

        var playerCount = players?.size?.minus(1)

        binding = FragmentGameProperBinding.inflate(inflater, container, false)
        val boardGameLayout = binding.boardGameLayout
        createBoard(boardGameLayout)
        updatePlayerboxes(boardGameLayout)

        if (players != null) {
            if (players.isNotEmpty()) {
                setCurrentPlayers(players?.get(currentPlayer) ?: player)
            }
        }

        binding.rollButton.setOnClickListener {

            val firstPosition = playersArrayList.get(currentPlayer).position;

            if (firstPosition != -1) {
                updatebox(playersArrayList.get(currentPlayer), boardGameLayout, requireContext())
            }

            playersArrayList.get(currentPlayer).lastPosition = playersArrayList.get(currentPlayer).position

            if (firstPosition != -1) {
                removeColor(
                    playersArrayList.get(currentPlayer).lastPosition,
                    boardGameLayout,
                    requireContext()
                )
            }

            var rollPosition = rollDice(playersArrayList.get(currentPlayer).position)

            if (rollPosition > 99) {
                var excess = rollPosition - 99;
                rollPosition = 99 - excess;
            }

            if (rollPosition == 4) {
                rollPosition = 34;
            }
            if (rollPosition == 8) {
                rollPosition = 50;
            }
            if (rollPosition == 22) {
                rollPosition = 41;
            }
            if (rollPosition == 47) {
                rollPosition = 85;
            }
            if (rollPosition == 61) {
                rollPosition = 82;
            }
            if (rollPosition == 68) {
                rollPosition = 90;
            }

            if (rollPosition == 94) {
                rollPosition = 37;
            }
            if (rollPosition == 86) {
                rollPosition = 65;
            }
            if (rollPosition == 81) {
                rollPosition = 19;
            }
            if (rollPosition == 55) {
                rollPosition = 7;
            }
            if (rollPosition == 48) {
                rollPosition = 6;
            }
            if (rollPosition == 35) {
                rollPosition = 4;
            }

            val userProfileActivity = activity as UserProfileActivity

            playersArrayList.get(currentPlayer).position = rollPosition
            updatePlayerboxes(boardGameLayout)

            if (rollPosition == 99) {

                winner = playersArrayList.get(currentPlayer)
                for (i in 0 until playersArrayList.size) {
                    playersArrayList[i].position = -1
                    userProfileActivity.updatePlayerList(playersArrayList)
                }
                updatePlayerboxes(boardGameLayout)
                userProfileActivity.updatePlayerList(playersArrayList)
                showWinner(binding,winner).show()

                playersArrayList.get(currentPlayer).position = -1
                updatePlayerboxes(boardGameLayout)

                val boardGameLayout = binding.boardGameLayout
                createBoard(boardGameLayout)
                binding.rollButton.isEnabled = false

                boardGameLayout.removeAllViews()
                createBoard(boardGameLayout)

            } else
                userProfileActivity.updatePlayerList(playersArrayList)

            if (playerCount == currentPlayer) {
                currentPlayer = 0;
            } else {
                currentPlayer += 1;
            }

            if (players != null) {
                if (!players.isEmpty()) {
                    setCurrentPlayers(players?.get(currentPlayer) ?: player)
                }
            }

        }

        return binding.root
    }

    private fun removeColor(position: Int, boardGameLayout: LinearLayout, context: Context) {

        val boardSize = 10
        val row = boardSize - 1 - (position / boardSize)
        val col = if (row % 2 == 0) boardSize - 1 - (position % boardSize) else position % boardSize
        val rowView = boardGameLayout.getChildAt(row) as LinearLayout
        val cellView = rowView.getChildAt(col)
        val boxLayout = cellView.findViewById<ConstraintLayout>(R.id.box1)

        boxLayout.setBackgroundResource(0)

        if (row % 2 == 0 && col % 2 == 0 || row % 2 != 0 && col % 2 != 0) {
            boxLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.box2))
        } else {
            boxLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.box1))
        }


        val textView = cellView.findViewById<TextView>(R.id.tile_text)
        val positionReal = position + 1
        textView.text = positionReal.toString()
    }

    fun showWinner(binding: FragmentGameProperBinding, players: Players): Dialog {

        return this!!.let {
            val builder = AlertDialog.Builder(requireContext())
            var winnerBinding: WinnerBinding =
                WinnerBinding.inflate(it.layoutInflater)
            with(builder) {
                setView(winnerBinding.root)
                val dialog = create()
                dialog.setOnShowListener {
                    val continueButton = dialog.findViewById<AppCompatButton>(R.id.button_continue)
                    winnerBinding.winnerText.text = players.name.toString() + " Wins!"
                    val userProfileActivity = activity as UserProfileActivity
                    continueButton.setOnClickListener {

                        userProfileActivity.removeBoardFragment()
                        playersArrayList.clear()
                        userProfileActivity.updatePlayerList(playersArrayList)
                        userProfileActivity.setPlayerCount()
                        userProfileActivity.setWinner(winner)
                        dialog.dismiss()

                    }
                }
                dialog
            }
        }
    }

    fun setCurrentPlayers(player: Players) {

        val bitmap = BitmapFactory.decodeFile(player.imagePath)

        binding.playerNameDisplay.text = player.name +"'s turn"

    }

    private fun rollDice(position: Int): Int {

        var rollNum = (1..6).random()
        binding.playerRollDisplay.text = rollNum.toString()

        return position + rollNum

    }

    private fun updatePlayerboxes(boardGameLayout: LinearLayout) {
        for (currentPlayer in playersArrayList) {
            if (currentPlayer.position != -1) {
                updatebox(currentPlayer, boardGameLayout, requireContext())
            }
        }
    }

    private fun updatebox(player: Players, boardGameLayout: LinearLayout, context: Context) {
        val boardSize = 10
        val row = boardSize - 1 - (player.position / boardSize)
        val col = if (row % 2 == 0) boardSize - 1 - (player.position % boardSize) else player.position % boardSize
        val rowView = boardGameLayout.getChildAt(row) as LinearLayout
        val cellView = rowView.getChildAt(col)
        val boxLayout = cellView.findViewById<ConstraintLayout>(R.id.box1)
        val textView = cellView.findViewById<TextView>(R.id.tile_text)
        textView.text = ""
        val bitmap = BitmapFactory.decodeFile(player.imagePath)
        val bitmapDrawable = BitmapDrawable(context.resources, bitmap)
        boxLayout.background = bitmapDrawable
        boxLayout.background = bitmapDrawable

    }

    @SuppressLint("MissingInflatedId")
    private fun createBoard(boardGameLayout: LinearLayout) {
        val boardSize = 10
        val box1 = ContextCompat.getColor(requireContext(), R.color.box2)
        val box2 = ContextCompat.getColor(requireContext(), R.color.box1)
        var count = 100
        var leftToRight = true;

        for (row
        in 0 until boardSize) {
            val rowView = LinearLayout(context)
            rowView.orientation = LinearLayout.HORIZONTAL

            if (row % 2 == 1) {

                if (count == 90 && row == 1) {
                    count = 81;
                }
                if (count == 70 && row == 3) {
                    count = 61;
                }
                if (count == 50 && row == 5) {
                    count = 41;
                }
                if (count == 30 && row == 7) {
                    count = 21;
                }
                if (count == 10 && row == 9) {
                    count = 1;
                }

                leftToRight = false;
            } else {
                leftToRight = true;

                if (count == 91 && row == 2) {
                    count = 80;
                }
                if (count == 71 && row == 4) {
                    count = 60;
                }
                if (count == 51 && row == 6) {
                    count = 40;
                }
                if (count == 31 && row == 8) {
                    count = 20;
                }

            }

            for (cell in 0 until boardSize) {
                val cellView = LayoutInflater.from(context).inflate(
                    R.layout.box, rowView, false
                )

                val textView = cellView.findViewById<TextView>(R.id.tile_text)
                textView.text = count.toString()

                if (row % 2 == 0 && cell % 2 == 0 || row % 2 != 0 && cell % 2 != 0) {
                    cellView.setBackgroundColor(box1)
                } else {
                    cellView.setBackgroundColor(box2)
                }

                if (leftToRight) {
                    count = count - 1;
                } else {
                    count = count + 1;
                }

                rowView.addView(cellView)
            }

            boardGameLayout.addView(rowView)
        }
    }


}

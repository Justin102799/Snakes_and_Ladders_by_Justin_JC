package ph.stacktrek.novare.snakesandladders.delacruz.adapter

import android.content.ContentValues
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ph.stacktrek.novare.snakesandladders.delacruz.databinding.PlayerDetailsBinding
import ph.stacktrek.novare.snakesandladders.delacruz.model.Players
import java.io.File

class PlayersAdapter(private val context: Context,
                    var playerList:ArrayList<Players>):
    RecyclerView.Adapter<PlayersAdapter.ViewHolder>() {

    fun addPlayer(player: Players){
        playerList.add(0, player)
        notifyItemInserted(0)
    }

    override fun onBindViewHolder(holder: PlayersAdapter.ViewHolder, position: Int) {
        holder.bindPlayers(playerList[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersAdapter.ViewHolder {
        val playerDetailsBinding = PlayerDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(playerDetailsBinding)
    }

    override fun getItemCount(): Int = playerList.size
    fun updatePlayerList(newPlayerList: ArrayList<Players>) {
        playerList = newPlayerList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val playerBinding: PlayerDetailsBinding):
        RecyclerView.ViewHolder(playerBinding.root){

        fun bindPlayers(players: Players){

            playerBinding.playerNameText.text = players.name

            if(players.position==-1){
                playerBinding.playerPosition.text = ""
            }

            if(players.position!=-1){
                var pos = players.position + 1
                playerBinding.playerPosition.text = pos.toString()
            }

            val imagePath = players.imagePath
            val file = File(imagePath)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                playerBinding.playerImage.setImageBitmap(bitmap)
            }

        }
    }
}
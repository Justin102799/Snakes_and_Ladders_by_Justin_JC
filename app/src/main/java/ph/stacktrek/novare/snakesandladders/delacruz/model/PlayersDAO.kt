package ph.stacktrek.novare.snakesandladders.delacruz.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream

interface PlayersDAO {

    fun addPlayers(player: Players)
    fun getPlayers(): ArrayList<Players>

}
class PlayersDAOStubImplementation: PlayersDAO {
    private var playerList: ArrayList<Players> = ArrayList()

    override fun addPlayers(player: Players) {
        playerList.add(player)
    }

    override fun getPlayers(): ArrayList<Players> = playerList

}

class PlayersDAOSQLLiteImplementation(var context: Context): PlayersDAO {

    override fun addPlayers(player: Players) {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(DatabaseHandler.TABLE_PLAYER_NAME, player.name)
        var status = db.insert(
            DatabaseHandler.TABLE_WINNER,
            null,
            contentValues
        )
        db.close()
    }

    override fun getPlayers(): ArrayList<Players> {
        val databaseHandler = DatabaseHandler(context)
        val db = databaseHandler.readableDatabase
        var result = ArrayList<Players>()
        var cursor: Cursor? = null

        val columns =  arrayOf(DatabaseHandler.TABLE_PLAYER_ID,
            DatabaseHandler.TABLE_PLAYER_NAME)

        try {
            cursor = db.query(
                DatabaseHandler.TABLE_WINNER,
                columns,
                null,
                null,
                null,
                null,
                "${DatabaseHandler.TABLE_PLAYER_ID} DESC",
                "5"
            )
        }catch (sqlException: SQLException){
            db.close()
            return result
        }

        var player: Players
        if(cursor.moveToFirst()){
            do{
                player = Players("")
                player.name = cursor.getString(1)
                player.userID = cursor.getInt(0).toString()

                result.add(player)
            }while(cursor.moveToNext())

        }
        return result
    }

}
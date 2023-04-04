package ph.stacktrek.novare.snakesandladders.delacruz.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context, DATABASENAME,null, DATABASEVERSION) {

    companion object {
        private val DATABASEVERSION = 1
        private val DATABASENAME = "winner_db"
        const val TABLE_WINNER= "player_winner"
        const val TABLE_PLAYER_ID = "player_id"
        const val TABLE_PLAYER_NAME = "name"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_WINNERS_TABLE =
            "CREATE TABLE $TABLE_WINNER " +
                    "($TABLE_PLAYER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TABLE_PLAYER_NAME TEXT)"

        db?.execSQL(CREATE_WINNERS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_WINNER")
        onCreate(db)
    }

}
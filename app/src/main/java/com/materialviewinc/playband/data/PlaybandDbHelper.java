package com.materialviewinc.playband.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.materialviewinc.playband.data.PlaybandContract.AlbumEntry;
import com.materialviewinc.playband.data.PlaybandContract.MusicEntry;


public class PlaybandDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "playband.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_ALBUM = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s TEXT)",
            AlbumEntry.TABLE_NAME, AlbumEntry.ID, AlbumEntry.TITLE, AlbumEntry.SUBTITLE, AlbumEntry.IMAGE);

    private static final String CREATE_TABLE_MUSIC = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s TEXT,%s INTEGER, FOREIGN KEY(%s) REFERENCES %s(%s) ON DELETE CASCADE )",
            MusicEntry.TABLE_NAME, MusicEntry.ID, MusicEntry.TITLE, MusicEntry.IMAGE,MusicEntry.ALBUM_ID, MusicEntry.ALBUM_ID, AlbumEntry.TABLE_NAME, AlbumEntry.ID);



    private static final String DROP_TABLE_ALBUM = "DROP TABLE " + AlbumEntry.TABLE_NAME;
    private static final String DROP_TABLE_MUSIC = "DROP TABLE " + MusicEntry.TABLE_NAME;

    public PlaybandDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ALBUM);
        db.execSQL(CREATE_TABLE_MUSIC);
        Log.d("TAGSQL", "Succfully inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_MUSIC);
        db.execSQL(DROP_TABLE_ALBUM);
        db.execSQL(CREATE_TABLE_ALBUM);
        db.execSQL(CREATE_TABLE_MUSIC);
    }

}

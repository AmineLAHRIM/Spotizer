package com.materialviewinc.playband.Activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import com.materialviewinc.playband.R;
import com.materialviewinc.playband.adapters.AlbumAdapter;
import com.materialviewinc.playband.classes.Album;
import com.materialviewinc.playband.classes.Music;
import com.materialviewinc.playband.data.PlaybandContract.AlbumEntry;
import com.materialviewinc.playband.data.PlaybandContract.MusicEntry;
import com.materialviewinc.playband.data.PlaybandDbHelper;
import com.materialviewinc.playband.utils.MusicController;

import java.io.InputStream;
import java.util.ArrayList;

public class AlbumListActivity extends AppCompatActivity {

    ImageButton btn_back;
    ListView listview_albums;
    String jsonLocation;
    int albumId, musicId;
    String albumTitle, albumSubitle, albumImage, musicTitle, musicRes;
    PlaybandDbHelper playbandDbHelper;
    SQLiteDatabase db;
    int lastrowid = FIRST_FETCH;
    private final static int FIRST_FETCH = -1;
    private static int FIRST_ID_FETCHED;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        init();
        listener();
        ArrayList<Album> Albums = new ArrayList<>();
        ArrayList<Music> Musics = new ArrayList<>();

        final String MY_QUERY = "SELECT * FROM " + AlbumEntry.TABLE_NAME + " as a INNER JOIN " + MusicEntry.TABLE_NAME + " as b on a." + AlbumEntry.ID + "=b." + MusicEntry.ALBUM_ID + " ORDER BY a." + AlbumEntry.ID;
        Cursor C = db.rawQuery(MY_QUERY, null);
        try {
            C.moveToFirst();
            albumId = C.getInt(0);
            albumTitle = C.getString(1);
            albumSubitle = C.getString(2);
            albumImage = C.getString(3);
            musicId = C.getInt(4);
            musicTitle = C.getString(5);
            musicRes = C.getString(6);
            FIRST_ID_FETCHED = albumId;
            lastrowid = albumId;
            Musics.add(new Music(musicId, musicRes, musicTitle));
            if (C.getCount() == 1) {
                Albums.add(new Album(albumId, albumTitle, albumSubitle, albumImage, Musics));
            } else {
                while (C.moveToNext()) {
                    albumId = C.getInt(0);
                    albumTitle = C.getString(1);
                    albumSubitle = C.getString(2);
                    albumImage = C.getString(3);
                    musicId = C.getInt(4);
                    musicTitle = C.getString(5);
                    musicRes = C.getString(6);


                    if (lastrowid != albumId && albumId != FIRST_ID_FETCHED) {
                        C.moveToPrevious();
                        albumTitle = C.getString(1);
                        albumSubitle = C.getString(2);
                        albumImage = C.getString(3);
                        Albums.add(new Album(C.getInt(0), albumTitle, albumSubitle, albumImage, Musics));
                        Musics = new ArrayList<>();
                        C.moveToNext();
                    }
                    Musics.add(new Music(musicId, musicRes, musicTitle));


                    lastrowid = albumId;
                }
                Albums.add(new Album(albumId, albumTitle, albumSubitle, albumImage, Musics));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        C.close();
        Log.d("TAGAlbums", "onCreate: " + Albums);
        Log.d("TAGAlbums", "onCreate: " + Albums);
        /*try {
            jsonLocation = loadJSONFromAsset();
            JSONObject jsonObject = new JSONObject(jsonLocation);
            JSONArray albums = jsonObject.getJSONArray("album");
            JSONObject albumJSONObject;
            JSONObject musicJSONObject;
            for (int i = 0; i <= albums.length(); i++) {
                Musics = new ArrayList<>();
                albumJSONObject = (JSONObject) albums.get(i);
                albumId = albumJSONObject.getInt("id");
                albumTitle = albumJSONObject.getString("title");
                albumSubitle = albumJSONObject.getString("subtitle");
                albumImage = albumJSONObject.getString("image");
                JSONArray musics = albumJSONObject.getJSONArray("music");
                for (int j = 0; j <= musics.length() - 1; j++) {
                    musicJSONObject = (JSONObject) musics.get(j);
                    musicId = musicJSONObject.getInt("id");
                    musicTitle = musicJSONObject.getString("title");
                    musicRes = musicJSONObject.getString("res");
                    Musics.add(new Music(musicId, musicRes, musicTitle));
                }
                Albums.add(new Album(albumId, albumTitle, albumSubitle, albumImage, Musics));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        AlbumAdapter arrayAdapter_Album = new AlbumAdapter(this, Albums);

        listview_albums.setAdapter(arrayAdapter_Album);
    }

    private void init() {
        btn_back = findViewById(R.id.btn_back);
        listview_albums = findViewById(R.id.listview_albums);

        //Database init
        playbandDbHelper = new PlaybandDbHelper(this);
        db = playbandDbHelper.getReadableDatabase();

    }

    private void listener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private String loadJSONFromAsset() {
        String json ;
        try {
            InputStream is = this.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicController musicController = MusicController.getInstance(AlbumListActivity.this);
        AlbumAdapter.permentSelectedPlay = null;

    }
}

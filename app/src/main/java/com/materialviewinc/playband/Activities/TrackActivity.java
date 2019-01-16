package com.materialviewinc.playband.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import com.materialviewinc.playband.R;
import com.materialviewinc.playband.adapters.MusicAdapter;
import com.materialviewinc.playband.classes.Album;
import com.materialviewinc.playband.classes.Music;
import com.materialviewinc.playband.utils.MusicController;

import java.util.ArrayList;

public class TrackActivity extends AppCompatActivity {
    ImageButton btn_back;
    ImageView img_album;
    ListView listview_tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        init();
        listener();
        Intent intent = getIntent();
        int ResourceImage = intent.getIntExtra("ResourceImage", -1);
        int idAlbum = intent.getIntExtra("idAlbum", -1);
        if (ResourceImage != -1) {
            img_album.setImageResource(ResourceImage);
        }

        ArrayList<Music> Musics = new ArrayList<>();
        Musics = Album.findAlbum(idAlbum).getMusics();
        MusicAdapter MusicAdapter;

        if (idAlbum != -1) {
            MusicAdapter = new MusicAdapter(this, Musics, idAlbum);
        } else {
            MusicAdapter = new MusicAdapter(this, Musics);
        }

        listview_tracks.setAdapter(MusicAdapter);

    }

    private void init() {
        btn_back = findViewById(R.id.btn_back);
        img_album = findViewById(R.id.img_album);
        listview_tracks = findViewById(R.id.listview_tracks);

    }

    private void listener() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicController musicController = MusicController.getInstance(TrackActivity.this);
//        musicController.VerifyPlayButton(0,musicController.getSelectedIdMusic(), MusicAdapter.selectedPlay);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicController musicController = MusicController.getInstance(TrackActivity.this);

        if(musicController.getSelectedIdMusic()==-1){
            MusicController.getInstance(this).resetAlbum();
        }
    }
}

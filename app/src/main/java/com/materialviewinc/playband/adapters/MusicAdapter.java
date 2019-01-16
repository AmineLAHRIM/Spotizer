package com.materialviewinc.playband.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.materialviewinc.playband.R;
import com.materialviewinc.playband.classes.Music;
import com.materialviewinc.playband.utils.MusicController;

import java.util.ArrayList;

public class MusicAdapter extends ArrayAdapter<Music> {

    private MusicController musicController = MusicController.getInstance(getContext());
    public static ImageButton selectedPlay;
    private int idNextMusic = -1;
    private static int idAlbum;

    public MusicAdapter(Context context, ArrayList<Music> data) {
        super(context, 0, data);
    }

    public MusicAdapter(Context context, ArrayList<Music> data, int idAlbum) {
        super(context, 0, data);
        //musicController.setSelectedIdAlbum(idAlbum);
        MusicAdapter.idAlbum = idAlbum;


    }

    @Override
    public int getPosition(Music item) {
        return super.getPosition(item);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final Music currentMusic = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_tracks, null);
        }
        final View view = convertView;
        ImageButton Play = (ImageButton) view.findViewById(R.id.imgbtn_play);
        ButtonPlayClick(Play, currentMusic, parent);
        TextView Title = view.findViewById(R.id.txt_musictitle);
        Title.setText(currentMusic.getTitle());
        musicController.VerifyPlayButton(0, currentMusic.getId(), Play);
        return view;
    }

    private void ButtonPlayClick(ImageButton Play, final Music currentMusic, final ViewGroup parent) {
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicController.RelaseMediaPlayer();
                musicController.setSelectedIdAlbum(idAlbum);

                final ImageButton imgbtnPlay = (ImageButton) v;

                if (selectedPlay == null) {
                    //first Click on Play Button
                    imgbtnPlay.setImageResource(R.drawable.pause);
                    selectedPlay = imgbtnPlay;
                    musicController.setSelectedIdAlbum(MusicAdapter.idAlbum);
                    musicController.setSelectedIdMusic(currentMusic.getId());
                    if (AlbumAdapter.selectedPlay != null) {
                        AlbumAdapter.selectedPlay.setImageResource(R.drawable.play);
                    }
                    AlbumAdapter.selectedPlay = AlbumAdapter.permentSelectedPlay;
                    AlbumAdapter.selectedPlay.setImageResource(R.drawable.pause);
                } else {
                    //Other Click on Diffrent Play Button
                    if (selectedPlay != v) {

                        selectedPlay.setImageResource(R.drawable.play);
                        imgbtnPlay.setImageResource(R.drawable.pause);
                        selectedPlay = imgbtnPlay;
                        musicController.setSelectedIdAlbum(MusicAdapter.idAlbum);
                        musicController.setSelectedIdMusic(currentMusic.getId());
                        if (AlbumAdapter.selectedPlay != null) {
                            AlbumAdapter.selectedPlay.setImageResource(R.drawable.play);
                        }
                        AlbumAdapter.selectedPlay = AlbumAdapter.permentSelectedPlay;
                        AlbumAdapter.selectedPlay.setImageResource(R.drawable.pause);
                    }
                    //Click on seem Play Button
                    else {
                        //musicController.resetAll();
                        musicController.resetMusic();
                    }
                }

                //PLAYING MUSIC
                //because if no id music in playing music the case of this activity not
                if (musicController.getSelectedIdMusic() != -1) {
                    musicController.MusicPlaying(null, parent, getPosition(currentMusic));
                }

            }
        });
    }


}



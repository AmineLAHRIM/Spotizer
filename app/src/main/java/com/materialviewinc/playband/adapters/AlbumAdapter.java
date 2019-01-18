package com.materialviewinc.playband.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.materialviewinc.playband.Activities.TrackActivity;
import com.materialviewinc.playband.R;
import com.materialviewinc.playband.classes.Album;
import com.materialviewinc.playband.utils.MusicController;

import java.util.ArrayList;

public class AlbumAdapter extends ArrayAdapter<Album> {

    public static ImageButton selectedPlay;
    public static ImageButton permentSelectedPlay;
    int idNextMusic = -1;
    public MusicController musicController = MusicController.getInstance(getContext());

    public AlbumAdapter(Context context, ArrayList data) {
        super(context, 0, data);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final Album currentAlbum = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_albums, null);
        }
        final View view = convertView;
        String albumImagename = currentAlbum.getImageAlbum();
        final int ResourceImage = getContext().getResources().getIdentifier(albumImagename, "drawable", getContext().getPackageName());
        if (ResourceImage != -1) {
            ImageView imageView = convertView.findViewById(R.id.img_album);
            imageView.setImageResource(ResourceImage);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentTrack = new Intent(getContext(), TrackActivity.class);
                    intentTrack.putExtra("ResourceImage", ResourceImage);
                    intentTrack.putExtra("idAlbum", currentAlbum.getId());

                    permentSelectedPlay = view.findViewById(R.id.imgbtn_play);
                    getContext().startActivity(intentTrack);
                    //((Activity) getContext()).finish();
                }
            });

        }
        TextView Title = convertView.findViewById(R.id.txt_albumtitle);
        TextView SubTitle = convertView.findViewById(R.id.txt_albumsubtitle);
        ImageButton Play = convertView.findViewById(R.id.imgbtn_play);

        ButtonPlayClick(Play, currentAlbum);


        musicController.VerifyPlayButton(currentAlbum.getId(), -1, Play);

        Title.setText(currentAlbum.getTitle());
        SubTitle.setText(currentAlbum.getSubTitle());
        return convertView;
    }


    private void ButtonPlayClick(ImageButton Play, final Album currentAlbum) {
        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageButton imgbtnPlay = (ImageButton) v;
                musicController.RelaseMediaPlayer();

                if (selectedPlay == null) {
                    imgbtnPlay.setImageResource(R.drawable.pause);
                    selectedPlay = imgbtnPlay;
                    musicController.setSelectedIdAlbum(currentAlbum.getId());
                } else {
                    if (selectedPlay != v) {
                        selectedPlay.setImageResource(R.drawable.play);
                        imgbtnPlay.setImageResource(R.drawable.pause);
                        selectedPlay = imgbtnPlay;
                        musicController.setSelectedIdAlbum(currentAlbum.getId());

                    } else {
                        musicController.resetAll();
                    }
                }


                //MUSIC PLAYING
                musicController.MusicPlaying(currentAlbum, null, -1);

            }
        });
    }


    private void VerifyPlayButton(Album currentAlbum, ImageButton Play) {
        int playedIdAlbum = musicController.getSelectedIdAlbum();
        int playedIdMusic = musicController.getSelectedIdMusic();

        if (playedIdAlbum != -1 && playedIdMusic != -1) {
            if (playedIdAlbum == currentAlbum.getId()) {
                //this condition for if we return to | the Activity or the View Row | Verify if there is a Played Music to Change Image of Play to Pause
                Play.setImageResource(R.drawable.pause);
                selectedPlay = Play;
            } else {
                //this condition for this a Recycle view that every 3 row had
                // the seem ID for the Views in that row like the Image of
                // Play of 0 is also of 3
                Play.setImageResource(R.drawable.play);
            }
        }
    }


}

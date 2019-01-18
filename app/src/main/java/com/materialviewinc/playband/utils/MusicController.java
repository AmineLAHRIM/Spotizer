package com.materialviewinc.playband.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import com.materialviewinc.playband.Activities.AlbumListActivity;
import com.materialviewinc.playband.Activities.TrackActivity;
import com.materialviewinc.playband.R;
import com.materialviewinc.playband.adapters.AlbumAdapter;
import com.materialviewinc.playband.adapters.MusicAdapter;
import com.materialviewinc.playband.classes.Album;
import com.materialviewinc.playband.classes.Music;

public class MusicController {

    private int selectedIdAlbum = -1;
    private int selectedIdMusic = -1;
    private static Context context;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
    private MediaPlayer mediaPlayer;
    private Music playedMusic;
    private Album playedAlbum;
    private int idNextMusic = -1;
    int resultAudioFocus = -1;


    public int getSelectedIdAlbum() {
        return selectedIdAlbum;
    }

    public void setSelectedIdAlbum(int selectedIdAlbum) {
        this.selectedIdAlbum = selectedIdAlbum;
    }

    public int getSelectedIdMusic() {
        return selectedIdMusic;
    }

    public void setSelectedIdMusic(int selectedIdMusic) {
        this.selectedIdMusic = selectedIdMusic;
    }

    public Music getPlayedMusic() {
        return playedMusic;
    }

    public void setPlayedMusic(Music playedMusic) {
        this.playedMusic = playedMusic;
    }

    public Album getPlayedAlbum() {
        return playedAlbum;
    }

    public void setPlayedAlbum(Album playedAlbum) {
        this.playedAlbum = playedAlbum;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public int getResultAudioFocus() {
        return resultAudioFocus;
    }

    public void setResultAudioFocus(int resultAudioFocus) {
        this.resultAudioFocus = resultAudioFocus;
    }

    private static MusicController ourInstance;

    public static MusicController getInstance(Context context) {
        //because Context can change with Activity
        MusicController.context = context;

        if (ourInstance == null) {
            ourInstance = new MusicController(MusicController.context);
            ourInstance.listener();

        }

        return ourInstance;
    }

    private MusicController(Context context) {

    }

    public void resetMusic() {
        this.selectedIdMusic = -1;
        this.idNextMusic = -1;
        if (MusicAdapter.selectedPlay != null) {
            MusicAdapter.selectedPlay.setImageResource(R.drawable.play);
            MusicAdapter.selectedPlay = null;
        }
        RelaseMediaPlayer();
    }

    public void resetAlbum() {
        this.selectedIdAlbum = -1;
        this.idNextMusic = -1;
        if (AlbumAdapter.selectedPlay != null) {
            AlbumAdapter.selectedPlay.setImageResource(R.drawable.play);
            AlbumAdapter.selectedPlay = null;
        }
        AlbumAdapter.permentSelectedPlay = null;
        RelaseMediaPlayer();

    }

    //Reset Music Audio and Album Audio
    public void resetAll() {
        this.resetMusic();
        this.resetAlbum();
    }

    public void NextMusic() {

    }

    public void listener() {
        //how ever have relation with context but it inisilase just one time
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    mediaPlayer.start();
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    RelaseMediaPlayer();
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    mediaPlayer.pause();
                }
            }
        };
    }

    public void RelaseMediaPlayer() {
        if (mediaPlayer != null) {
            //stop and clean this mp MediaPlayer
            mediaPlayer.release();
            mediaPlayer = null;

            if (audioManager != null) {
                //Abandon the audioManager for if you quite the activity the audioManager will disapear
                audioManager.abandonAudioFocus(onAudioFocusChangeListener);
            }

        }
    }

    public void MusicPlaying(Album currentAlbum, final ViewGroup parent, int position) {
        if (currentAlbum == null) {
            currentAlbum = Album.findAlbum(selectedIdAlbum);
        }


        final Album theAlbum = currentAlbum;
        if (AlbumAdapter.selectedPlay != null || MusicAdapter.selectedPlay != null) {

//                final Music currentMusic=null;
            if ((Activity) context instanceof AlbumListActivity) {

                if (idNextMusic == -1) {
                    //getFirstMusicResourceInAlbum
//                        currentMusic = currentAlbum.getMusics().get(0);
                    selectedIdMusic = currentAlbum.getMusics().get(0).getId();
                } else {
                    //getNextMusicResourceInAlbum
//                        currentMusic = Album.findMusicByIdInAlbum(currentAlbum.getId(), idNextMusic);
                    selectedIdMusic = Album.findMusicByIdInAlbum(currentAlbum.getId(), idNextMusic).getId();
                }
            } else if ((Activity) context instanceof TrackActivity) {
                //this condition was checked in first function call MusicAdapter
            }


            final Music currentMusic = Album.findMusicByIdInAlbum(selectedIdAlbum, selectedIdMusic);
            String musicResName = currentMusic.getAudio();
            int musicResource = context.getResources().getIdentifier(musicResName, "raw", context.getPackageName());

            if (position == -1) {
                position = Album.getPositionMusicFromAlbum(selectedIdAlbum, selectedIdMusic);
            }
            final int currentposition = position;
            //playMusic
            //Audio Part
            resultAudioFocus = audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            if (resultAudioFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                this.mediaPlayer = MediaPlayer.create(context, musicResource);
                this.mediaPlayer.start();
            }

            //MusicOnCompletion
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    idNextMusic = Album.nextMusic(selectedIdAlbum, currentMusic.getId());
                    Activity currentActivity = (Activity) context;
                    //go to the next music in the Album
                    if (idNextMusic != -1) {
                        selectedIdMusic = idNextMusic;
                        if (currentActivity instanceof AlbumListActivity) {

                            MusicPlaying(theAlbum, null, -1);

                        } else if (currentActivity instanceof TrackActivity) {

                            //GetTheNextButtonPlay
                            ViewGroup father;
                            if (parent == null) {
                                father = (ViewGroup) ((Activity) context).findViewById(R.id.listview_tracks);
                            } else {
                                father = parent;
                            }

                            ListView listView = (ListView) father;
                            int firstPosition = listView.getFirstVisiblePosition() - listView.getHeaderViewsCount();
                            int firstvisibleposition = listView.getFirstVisiblePosition();
                            int lastvisibleposition = listView.getLastVisiblePosition();
                            int Nextposition = currentposition + 1;

                            //RefrechTheView

                            if (MusicAdapter.selectedPlay != null) {
                                MusicAdapter.selectedPlay.setImageResource(R.drawable.play);
                                //check if NextPosition Between first Visibe position and last Visible Position
                                if (Nextposition >= firstvisibleposition && Nextposition <= lastvisibleposition) {

                                    View NextRow = listView.getChildAt(Nextposition - firstvisibleposition);
                                    ImageButton NextPlay = (ImageButton) NextRow.findViewById(R.id.imgbtn_play);

                                    MusicAdapter.selectedPlay = NextPlay;
                                    NextPlay.setImageResource(R.drawable.pause);
                                }
                            }
                            MusicPlaying(theAlbum, null, Nextposition);

                        }
                    }
                    //if is the last music in the Album
                    else {

//                        resetAll();
                        if (currentActivity instanceof AlbumListActivity) {
                            resetAll();
                        } else if (currentActivity instanceof TrackActivity) {
                            resetMusic();
                        }
                        idNextMusic = -1;
                    }


                }
            });
            selectedIdAlbum = currentAlbum.getId();
            selectedIdMusic = currentMusic.getId();
        }


    }


    public void VerifyPlayButton(int currentAlbumId, int currentMusicId, ImageButton Play) {

        if (selectedIdAlbum != -1 && selectedIdMusic != -1) {
            if ((Activity) context instanceof AlbumListActivity) {
                if (selectedIdAlbum == currentAlbumId) {
                    //this condition for if we return to | the Activity or the View Row | Verify if there is a Played Music to Change Image of Play to Pause
                    Play.setImageResource(R.drawable.pause);
                    AlbumAdapter.selectedPlay = Play;
                } else {
                    //this condition for this a Recycle view that every 3 row had
                    // the seem ID for the Views in that row like the Image of
                    // Play of 0 is also of 3
                    Play.setImageResource(R.drawable.play);
                }
            } else if ((Activity) context instanceof TrackActivity) {
                if (selectedIdMusic == currentMusicId) {
                    //this condition for if we return to | the Activity or the View Row | Verify if there is a Played Music to Change Image of Play to Pause
                    if (Play != null) {
                        Play.setImageResource(R.drawable.pause);
                        MusicAdapter.selectedPlay = Play;
                    }

                } else {
                    //this condition for this a Recycle view that every 3 row had
                    // the seem ID for the Views in that row like the Image of
                    // Play of 0 is also of 3
                    if (Play != null) {
                        Play.setImageResource(R.drawable.play);
                    }
                }
            }

        }
    }

}

package com.example.david.lang;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.os.RemoteCallbackList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.lang.Word;
import com.example.android.lang.WordAdapter;

import java.util.ArrayList;

public class Numbers extends AppCompatActivity {
    private MediaPlayer mMediaplayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaplayer.pause();
                mMediaplayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaplayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }

        }
    };
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> english_Number = new ArrayList<Word>();

        english_Number.add(new Word("One", "Un", R.drawable.apple1, R.raw.one));
        english_Number.add(new Word("Two", "Deux", R.drawable.apple2, R.raw.two));
        english_Number.add(new Word("Three", "Trois", R.drawable.apple3, R.raw.three));
        english_Number.add(new Word("Four", "Quatre", R.drawable.apple4, R.raw.four));
        english_Number.add(new Word("Five", "Cinq", R.drawable.apple5, R.raw.five));
        english_Number.add(new Word("Six", "Six", R.drawable.apple6, R.raw.six));
        english_Number.add(new Word("Seven", "Sept", R.drawable.apple7, R.raw.seven));
        english_Number.add(new Word("Eight", "Huit", R.drawable.apple8, R.raw.eight));
        english_Number.add(new Word("Nine", "Neuf", R.drawable.apple9, R.raw.nine));
        english_Number.add(new Word("Ten", "Dix", R.drawable.apple10, R.raw.ten));


        WordAdapter adapter = new WordAdapter(this, english_Number, R.color.cat1);
        ListView listView = (ListView) findViewById(R.id.list_rootview);
        listView.setAdapter(adapter);

        //Creating an onclicklistener to display when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = english_Number.get(position);
                //release the meida player resources
                releaseMediaPlayer();
                //Request audio focus for playback
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                     mMediaplayer = MediaPlayer.create(Numbers.this, word.getmAudioResourceid());
                     mMediaplayer.start();
                     //sets up a listener to release the mediaplayer when not in use
                     mMediaplayer.setOnCompletionListener(mCompletionListener);
            }}
        });
    }
         @Override
         protected void onStop() {
            super.onStop();
            releaseMediaPlayer();
    }
        private void releaseMediaPlayer() {
            if(mMediaplayer!=null){
                //regardless of the current state of the mediaplayer we want it to release its resources
                mMediaplayer.release();
                //setting the media player to null tells us that the mediaplayer isnt playing any audio
                mMediaplayer=null;
                mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            }
        }
    }


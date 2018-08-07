package com.example.david.lang;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.lang.Word;
import com.example.android.lang.WordAdapter;

import java.util.ArrayList;

public class Family extends AppCompatActivity {

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

        english_Number.add(new Word("Father", "Pere(le)", R.drawable.father, R.raw.father));
        english_Number.add(new Word("Mother", "Mere(la)", R.drawable.mother, R.raw.mother));
        english_Number.add(new Word("Brother", "Frere(le)", R.drawable.brother, R.raw.brother));
        english_Number.add(new Word("Sister", "Soeur(la)", R.drawable.sister, R.raw.sister));
        english_Number.add(new Word("Cousin", "Cousine", R.drawable.cousin, R.raw.cousin));
        english_Number.add(new Word("Niece", "Niece(la)", R.drawable.niece, R.raw.niece));
        english_Number.add(new Word("Nephew", "Neveu(le)", R.drawable.nephew, R.raw.nephew));
        english_Number.add(new Word("Grandpa", "Grand-pere(le)", R.drawable.grandpa, R.raw.grandpa));
        english_Number.add(new Word("Grandma", "Grand-mere(la)", R.drawable.grandma, R.raw.grandma));
        english_Number.add(new Word("Son", "Fils(le)", R.drawable.son, R.raw.son));


        WordAdapter adapter = new WordAdapter(this, english_Number, R.color.Cat2);
        ListView listView = (ListView) findViewById(R.id.list_rootview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = english_Number.get(position);
                //releasing the mediaplayer before being used
                releaseMediaPlayer();
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                     mMediaplayer = MediaPlayer.create(Family.this, word.getmAudioResourceid());
                     mMediaplayer.start();
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


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

public class Phrases extends AppCompatActivity {

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
    //creating an Oncompletion glaobal method to release media player resources
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

        english_Number.add(new Word("How are you?", "Comment allez vous?",R.raw.how_are_you));
            english_Number.add(new Word("I am very well, thank you.", "Je suis Tres bien, Merci.",
                    R.raw.i_am_very_well_thank_you));
        english_Number.add(new Word("Can I help you?", "Puis-je vous aider?",R.raw.can_i_help_you));
        english_Number.add(new Word("I am sorry.", "Je suis desole",R.raw.i_am_sorry));
        english_Number.add(new Word("I wish you good luck!", "Je te souhaite bonne chance!",
                R.raw.i_wish_you_good_luck));
        english_Number.add(new Word("Have a safe trip.", "Bon voyage.",R.raw.have_a_safe_trip));
        english_Number.add(new Word("You're Welcome.", "De rien.",R.raw.youre_welcome));
        english_Number.add(new Word("Am I disturbing you?", "Est-ce que je te derange?",
                R.raw.am_i_disturbing_you));
        english_Number.add(new Word("May I speak?", "Puis-je parler?",R.raw.may_i_speak));
        english_Number.add(new Word("Thank you for your hospitality.",
                "Je vous remercie pour votre hospitalite.",R.raw.thank_you_for_your_hospitality));


        WordAdapter adapter = new WordAdapter(this, english_Number,R.color.cat4);
        ListView listView = (ListView) findViewById(R.id.list_rootview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Word word=english_Number.get(position);
                releaseMediaPlayer();
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaplayer= MediaPlayer.create(Phrases.this,word.getmAudioResourceid());
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

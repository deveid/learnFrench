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

public class Colors extends AppCompatActivity {

    private  MediaPlayer mMediaplayer;
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

        final ArrayList<Word> english_Number=new ArrayList<Word>();

        english_Number.add(new Word("Red","Rouge",R.drawable.red,R.raw.red));
        english_Number.add(new Word("Yellow","Jaune",R.drawable.yellow,R.raw.yellow));
        english_Number.add(new Word("Brown","Marron",R.drawable.brown,R.raw.brown));
        english_Number.add(new Word("Grey","Gris(e)",R.drawable.grey,R.raw.grey));
        english_Number.add(new Word("White","Blanc(he)",R.drawable.white,R.raw.white));
        english_Number.add(new Word("Green","Vert(e)",R.drawable.green,R.raw.green));
        english_Number.add(new Word("Blue","Bleu(e)",R.drawable.blue,R.raw.blue));
        english_Number.add(new Word("Black","Noir(e)",R.drawable.black,R.raw.black));
        english_Number.add(new Word("Purple","Violet",R.drawable.purple,R.raw.purple));
        english_Number.add(new Word("Silver","Argent",R.drawable.silver,R.raw.silver));


        WordAdapter adapter=new WordAdapter(this,english_Number,R.color.cat3);
        ListView listView=(ListView)findViewById(R.id.list_rootview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Word word=english_Number.get(position);
                //releasing the mediaplayer before being used
                releaseMediaPlayer();
                int result=mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                mMediaplayer= MediaPlayer.create(Colors.this,word.getmAudioResourceid());
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

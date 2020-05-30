package com.example.android.dictionery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Phrases extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(focusChange== AudioManager.AUDIOFOCUS_GAIN)
            {
                mMediaPlayer.start();
            }
            else if(focusChange== AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    };
    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener(){
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);
        mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<com.example.android.dictionery.word> words = new ArrayList<com.example.android.dictionery.word>();
        words.add(new com.example.android.dictionery.word("Where are you going?", "minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new com.example.android.dictionery.word("What is your name?", "tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        words.add(new com.example.android.dictionery.word("My name is...", "oyaaset...",R.raw.phrase_my_name_is));
        words.add(new com.example.android.dictionery.word("How are you feeling?", "michәksәs?",R.raw.phrase_how_are_you_feeling));
        words.add(new com.example.android.dictionery.word("I’m feeling good.", "kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new com.example.android.dictionery.word("Are you coming?", "әәnәs'aa?",R.raw.phrase_are_you_coming));
        words.add(new com.example.android.dictionery.word("Yes,I'm coming.", "hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        words.add(new com.example.android.dictionery.word("I'm coming.", "әәnәm",R.raw.phrase_im_coming));
        words.add(new com.example.android.dictionery.word("Let's go", "yoowutis",R.raw.phrase_lets_go));
        words.add(new com.example.android.dictionery.word("Come here", "әnni'nem",R.raw.phrase_come_here));

        // Create an {@link com.example.android.dictionery.colors.WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words,R.color.category_phrases);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list_phrases);
//        TextView texttview=(TextView)findViewById(R.id.ani);
//        texttview.setVisibility(8);
        // Make the {@link ListView} use the {@link com.example.android.dictionery.colors.WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word Words=words.get(position);
                int result =mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    //Log.v("NumbersActivity", "Current word: " + Words);
                    mMediaPlayer = MediaPlayer.create(Phrases.this, Words.getmAudioResourseId());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }
    }

}
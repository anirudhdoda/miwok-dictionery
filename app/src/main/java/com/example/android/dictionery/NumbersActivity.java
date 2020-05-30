package com.example.android.dictionery;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
         mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words

        final ArrayList<com.example.android.dictionery.word> words = new ArrayList<com.example.android.dictionery.word>();
        words.add(new com.example.android.dictionery.word("one", "lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new com.example.android.dictionery.word("two", "otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new com.example.android.dictionery.word("three", "tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new com.example.android.dictionery.word("four", "oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new com.example.android.dictionery.word("five", "massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new com.example.android.dictionery.word("six", "temmokka",R.drawable.number_six,R.raw.family_older_sister));
        words.add(new com.example.android.dictionery.word("seven", "kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new com.example.android.dictionery.word("eight", "kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new com.example.android.dictionery.word("nine", "wo’e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new com.example.android.dictionery.word("ten", "na’aacha",R.drawable.number_ten,R.raw.number_ten));

        // Create an {@link com.example.android.dictionery.colors.WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words,R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link com.example.android.dictionery.colors.WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word Words=words.get(position);
                releaseMediaPlayer();
                int result =mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                   // Log.v("NumbersActivity", "Current word: " + Words);
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, Words.getmAudioResourseId());
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
    /**
     * Clean up the media player by releasing its resources.
     */
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
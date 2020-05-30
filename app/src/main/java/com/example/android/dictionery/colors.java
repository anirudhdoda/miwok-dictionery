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

        public class colors extends AppCompatActivity {
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
                setContentView(R.layout.activity_colors);
                mAudioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);

                // Create a list of words
                final ArrayList<com.example.android.dictionery.word> words = new ArrayList<com.example.android.dictionery.word>();
                words.add(new com.example.android.dictionery.word("red", "weṭeṭṭi",R.drawable.color_red,R.raw.color_red));
                words.add(new com.example.android.dictionery.word("green", "chokokki",R.drawable.color_green,R.raw.color_green));
                words.add(new com.example.android.dictionery.word("brown", "ṭakaakki",R.drawable.color_brown,R.raw.color_brown));
                words.add(new com.example.android.dictionery.word("gray", "ṭopoppi",R.drawable.color_gray,R.raw.color_gray));
                words.add(new com.example.android.dictionery.word("black", "kululli",R.drawable.color_black,R.raw.color_black));
                words.add(new com.example.android.dictionery.word("white", "kelelli",R.drawable.color_white,R.raw.color_white));
                words.add(new com.example.android.dictionery.word("dusty yellow", "ṭopiisә",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
                words.add(new com.example.android.dictionery.word("mustard yellow", "chiwiiṭә",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));



                // Create an {@link com.example.android.dictionery.colors.WordAdapter}, whose data source is a list of {@link Word}s. The
                // adapter knows how to create list items for each item in the list.
                WordAdapter adapter = new WordAdapter(this, words,R.color.category_colors);

                // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
                // There should be a {@link ListView} with the view ID called list, which is declared in the
                // activity_numbers.xml layout file.
                ListView listView = (ListView) findViewById(R.id.list_colors);

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
                            //Log.v("NumbersActivity", "Current word: " + Words);
                            mMediaPlayer = MediaPlayer.create(colors.this, Words.getmAudioResourseId());
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

        // Create an {@link com.example.android.dictionery.colors.WordAdapter}, whose data source is a list of {@link Word}s. The
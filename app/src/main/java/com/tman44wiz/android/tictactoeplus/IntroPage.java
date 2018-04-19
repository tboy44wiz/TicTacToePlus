package com.tman44wiz.android.tictactoeplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class IntroPage extends Activity{

    private ImageView exitView, speakerImageView;
    private Button gotItButton;

    Vibrator vibrator;

    MediaPlayer musicTrack;

    private Boolean speaker = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        vibrator =  (Vibrator) getSystemService(VIBRATOR_SERVICE);

        musicTrack = MediaPlayer.create(IntroPage.this, R.raw.music_track);
        musicTrack.setLooping(true);
        musicTrack.start();

        gotItButton = findViewById(R.id.gotIt_Button);
        speakerImageView = findViewById(R.id.speaker_ImageView);
        exitView = findViewById(R.id.exit_ImageView);


        speakerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (speaker) {
                    speakerImageView.setImageResource(R.drawable.speaker_on);
                    musicTrack.pause();
                    speaker = false;
                }
                else {
                    speakerImageView.setImageResource(R.drawable.speaker_off);
                    musicTrack.start();
                    speaker = true;
                }
            }
        });

        exitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(100);

                final AlertDialog.Builder exitAlertBuilder = new AlertDialog.Builder(IntroPage.this);
                exitAlertBuilder.setIcon(R.drawable.exit);
                exitAlertBuilder.setTitle("Close Game?");
                exitAlertBuilder.setMessage("Are you sure you want to close this game?");
                exitAlertBuilder.setCancelable(true);

                exitAlertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        IntroPage.this.finish();
                    }
                });

                AlertDialog alertDialog = exitAlertBuilder.create();
                alertDialog.show();
            }
        });

        gotItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivityIntent = new Intent(IntroPage.this, MainActivity.class);
                startActivity(mainActivityIntent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        musicTrack.stop();
        super.onDestroy();
    }
}

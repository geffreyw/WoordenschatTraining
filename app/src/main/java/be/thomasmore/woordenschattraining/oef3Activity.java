package be.thomasmore.woordenschattraining;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class oef3Activity extends AppCompatActivity {

    List<String> woorden = Arrays.asList("duikbril", "klimtouw", "kroos", "riet");
    int i = 0;
    MediaPlayer ring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef3);

        speelUitleg();
    }

    public void speelUitleg() {
        ring = MediaPlayer.create(oef3Activity.this, getResources().getIdentifier("oef3_" + woorden.get(i), "raw", getPackageName()));
        ring.start();

        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                speelZin();
            }
        });
    }

    public void speelZin() {
        ring = MediaPlayer.create(oef3Activity.this, getResources().getIdentifier("oef3_" + woorden.get(i) + "_goed", "raw", getPackageName()));
        ring.start();
    }

    public void controleerAntwoord(View v) {
        String antwoord = v.getTag().toString();

    }

    public void herhaalZin(View view) {
        speelZin();
    }
}

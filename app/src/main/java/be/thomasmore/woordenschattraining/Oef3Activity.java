package be.thomasmore.woordenschattraining;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Oef3Activity extends AppCompatActivity {

    List<String> woorden = Arrays.asList("duikbril", "klimtouw", "kroos", "riet");
    List<String> goedOfFout = Arrays.asList("_goed", "_fout");
    static Random random = new Random();
    int randomNumber  = random.nextInt(2);
    int i = 0;
    MediaPlayer ring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef3);

//        speelUitleg();
        speelZin();
    }

    public void speelUitleg() {
        ring = MediaPlayer.create(Oef3Activity.this, getResources().getIdentifier("oef3_" + woorden.get(i), "raw", getPackageName()));
        ring.start();

        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                speelZin();
            }
        });
    }

    public void speelZin() {
        ring = MediaPlayer.create(Oef3Activity.this, getResources().getIdentifier("oef3_" + woorden.get(i) + goedOfFout.get(randomNumber), "raw", getPackageName()));
        ring.start();
    }

    public void controleerAntwoord(View v) {
            String antwoord = v.getTag().toString();
        String correcteAntwoord = goedOfFout.get(randomNumber);
        if (antwoord.equals(correcteAntwoord)){
            if (correcteAntwoord.equals("_goed")){
                ring = MediaPlayer.create(Oef3Activity.this, getResources().getIdentifier("oef3_goed", "raw", getPackageName()));
                ring.start();
            }
            else if(correcteAntwoord.equals("_fout")){
                ring = MediaPlayer.create(Oef3Activity.this, getResources().getIdentifier("oef3_goedfout", "raw", getPackageName()));
                ring.start();
            }
            ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    volgende();
                }
            });
        }
        else {
            ring = MediaPlayer.create(Oef3Activity.this, getResources().getIdentifier("oef3_fout", "raw", getPackageName()));
            ring.start();
            ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    volgende();
                }
            });
        }
    }

    public void volgende(){
        i++;
        if (i == 4) {
            startOef4();
        }
        else {
            randomNumber = random.nextInt(2);
            speelZin();
        }
    }

    public void startOef4(){
        Intent intent = new Intent(this, Oef4Activity.class);
        startActivity(intent);
    }

    public void herhaalZin(View view) {
        speelZin();
    }
}

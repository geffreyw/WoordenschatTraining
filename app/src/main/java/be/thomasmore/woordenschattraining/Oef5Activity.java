package be.thomasmore.woordenschattraining;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Oef5Activity extends AppCompatActivity {

    List<String> woordenOpties = Arrays.asList("duikbril", "klimtouw", "kroos");

    String[][] woorden = new String[][]{
            {"duikbril1", "fiets", "duikbril2", "duikbril3"},
            {"klimtouw1", "klimtouw2", "klimtouw3", "wipwap"},
            {"eend", "kroos1", "kroos2", "kroos3"}
    };

    int i = 0;
    int aantalKeren = 0;
    String geselecteerdeAfbeelding;
    String juisteAntwoord;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef5);

        maakLayout();
        speelUitleg();

    }

    public void maakLayout() {
        ImageView image1 = (ImageView) findViewById(R.id.afbeelding1);
        ImageView image2 = (ImageView) findViewById(R.id.afbeelding2);
        ImageView image3 = (ImageView) findViewById(R.id.afbeelding3);
        ImageView image4 = (ImageView) findViewById(R.id.afbeelding4);

        image1.setImageResource(getResources().getIdentifier(("oef5_" + woorden[i][0]), "drawable", getPackageName()));
        image1.setTag(woorden[i][0]);

        image2.setImageResource(getResources().getIdentifier(("oef5_" + woorden[i][1]), "drawable", getPackageName()));
        image2.setTag(woorden[i][1]);

        image3.setImageResource(getResources().getIdentifier(("oef5_" + woorden[i][2]), "drawable", getPackageName()));
        image3.setTag(woorden[i][2]);

        image4.setImageResource(getResources().getIdentifier(("oef5_" + woorden[i][3]), "drawable", getPackageName()));
        image4.setTag(woorden[i][3]);
    }

    private void speelUitleg() {
        MediaPlayer audio = MediaPlayer.create(Oef5Activity.this, getResources().getIdentifier("oef4_uitleg", "raw", getPackageName()));
        audio.start();

    }

    public void selecteer(View v) {
        geselecteerdeAfbeelding = v.getTag().toString();
        v.setVisibility(View.INVISIBLE);
    }

    public void onClickGroeneKader(View view) {
        juisteAntwoord = woordenOpties.get(i);
        aantalKeren++;

        if (geselecteerdeAfbeelding.contains(juisteAntwoord)) {
            MediaPlayer audio = MediaPlayer.create(Oef5Activity.this, getResources().getIdentifier("oef3_goed", "raw", getPackageName()));
            audio.start();
        }
        else {
            MediaPlayer audio = MediaPlayer.create(Oef5Activity.this, getResources().getIdentifier("oef3_fout", "raw", getPackageName()));
            audio.start();
        }

        if(aantalKeren==4){
            volgende();
        }
    }

    public void onClickRodeKader(View view) {
        juisteAntwoord = woordenOpties.get(i);
        aantalKeren++;

        if (!geselecteerdeAfbeelding.contains(juisteAntwoord)) {
            MediaPlayer audio = MediaPlayer.create(Oef5Activity.this, getResources().getIdentifier("oef3_goed", "raw", getPackageName()));
            audio.start();
        }
        else{
            MediaPlayer audio = MediaPlayer.create(Oef5Activity.this, getResources().getIdentifier("oef3_fout", "raw", getPackageName()));
            audio.start();
        }

        if(aantalKeren==4){
            volgende();
        }
    }

    private void volgende() {
        Intent intent = new Intent(this, Oef6_1Activity.class);
        startActivity(intent);
    }
}

package be.thomasmore.woordenschattraining;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Oef4Activity extends AppCompatActivity {

    String[][] woorden = new String[][]{
            {"duikbril", "ogen", "zee", "zwemmen", "schrijven"},
            {"klimtouw", "klimmen", "sterk", "turnzaal", "zwembad"},
            {"kroos", "groen", "vijver", "lamp", "De eend"}
    };

    List<String> fotos;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef4);

        maakLayout();
        speelUitleg();
    }

    public void speelUitleg() {
        MediaPlayer audio = MediaPlayer.create(Oef4Activity.this, getResources().getIdentifier("oef4_uitleg", "raw", getPackageName()));
        audio.start();

        audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                zegWoord();
            }
        });
    }

    public void maakLayout(){
        TextView doelwoord = (TextView) findViewById(R.id.doelwoord);
        doelwoord.setText(woorden[i][0]);

        TextView woord1 = (TextView) findViewById(R.id.woord1);
        woord1.setText(woorden[i][1]);

        TextView woord2 = (TextView) findViewById(R.id.woord2);
        woord2.setText(woorden[i][2]);

        TextView woord3 = (TextView) findViewById(R.id.woord3);
        woord3.setText(woorden[i][3]);

        TextView woord4 = (TextView) findViewById(R.id.woord4);
        woord4.setText(woorden[i][4]);

        ImageView image1 = (ImageView) findViewById(R.id.afbeelding1);
        image1.setImageResource(getResources().getIdentifier(("oef4_"+woorden[i][1]), "drawable", getPackageName()));

        ImageView image2 = (ImageView) findViewById(R.id.afbeelding2);
        image2.setImageResource(getResources().getIdentifier(("oef4_"+woorden[i][2]), "drawable", getPackageName()));

        ImageView image3 = (ImageView) findViewById(R.id.afbeelding3);
        image3.setImageResource(getResources().getIdentifier(("oef4_"+woorden[i][3]), "drawable", getPackageName()));

        ImageView image4 = (ImageView) findViewById(R.id.afbeelding4);
        image4.setImageResource(getResources().getIdentifier(("oef4_"+woorden[i][4]), "drawable", getPackageName()));
    }

    public void zegWoord(){
        MediaPlayer audio = MediaPlayer.create(Oef4Activity.this, getResources().getIdentifier("voormeting_"+woorden[i][0], "raw", getPackageName()));
        audio.start();
    }

    public void toevoegen(View view) {
    }
}

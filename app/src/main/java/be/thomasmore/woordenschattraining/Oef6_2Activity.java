package be.thomasmore.woordenschattraining;

import android.content.Intent;
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

public class Oef6_2Activity extends AppCompatActivity {

    List<String> woorden = Arrays.asList("duikbril", "klimtouw", "kroos", "riet");
    int i=0;
    MediaPlayer audio;
    List<String> fotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef6_2);

        leesFotos();
        maakLayout();
    }

    private void leesFotos() {
        fotos = new ArrayList<String>();

        Field[] drawables = be.thomasmore.woordenschattraining.R.drawable.class.getFields();
        for (Field f : drawables) {
            if (f.getName().startsWith("voormeting_"+ woorden.get(i))) {
                fotos.add(f.getName());
                i++;
                if(i==4){
                    i=0;
                }
            }
        }
    }

    public void maakLayout(){
        TextView woord = (TextView) findViewById(R.id.woord);
        woord.setText(woorden.get(i));


        ImageView image = (ImageView) findViewById(R.id.afbeelding);
        image.setImageResource(getResources().getIdentifier(fotos.get(i), "drawable", getPackageName()));

        speelZin();
    }

    public void speelZin() {
        audio = MediaPlayer.create(Oef6_2Activity.this, getResources().getIdentifier("voormeting_" + woorden.get(i), "raw", getPackageName()));
        audio.start();
    }

    public void volgendWoord(View v){
        i++;
        if(i == 4){
            startNameting();
        }
        else {
            maakLayout();
        }
    }

    public void herhaalZin(View view) {
        speelZin();
    }

    public void startNameting(){
        Intent intent = new Intent(this, VoormetingActivity.class);
        startActivity(intent);
    }
}

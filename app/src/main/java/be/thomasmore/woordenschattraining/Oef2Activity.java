package be.thomasmore.woordenschattraining;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Oef2Activity extends AppCompatActivity {

    List<String> woorden = Arrays.asList("duikbril","klimtouw", "kroos", "riet");
    List<String> fotos;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef2);

        leesFotos();
        toonFoto();
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

    public void toonFoto() {
        TextView woord = (TextView) findViewById(R.id.woord);
        woord.setText(woorden.get(i).toUpperCase());


        ImageView image = (ImageView) findViewById(R.id.afbeelding);
        image.setImageResource(getResources().getIdentifier(fotos.get(i), "drawable", getPackageName()));

        speelUitleg();

    }

    public void speelUitleg(){
        MediaPlayer ring= MediaPlayer.create(Oef2Activity.this, getResources().getIdentifier("oef2_"+woorden.get(i), "raw", getPackageName()));
        ring.start();

    }

    public void volgendWoord(View v){
        i++;
        if(i == 4){
            startOef3();
        }
        else {
            toonFoto();
        }
    }

    public void startOef3(){
        Intent intent = new Intent(this, Oef3Activity.class);
        startActivity(intent);
    }

    public void herhaalUitleg(View v){
        MediaPlayer ring= MediaPlayer.create(Oef2Activity.this, getResources().getIdentifier("uitleg_"+woorden.get(i), "raw", getPackageName()));
        ring.start();
    }
}

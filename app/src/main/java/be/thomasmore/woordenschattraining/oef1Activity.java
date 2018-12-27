package be.thomasmore.woordenschattraining;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class oef1Activity extends AppCompatActivity {

    private DatabaseHelper db;

    List<String> woorden = Arrays.asList("duikbril","klimtouw", "kroos", "riet");

    List<String> fotos;
    int i = 0;

    Test test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef1);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        test = db.getTest(bundle.getLong("testId"));

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
        MediaPlayer ring= MediaPlayer.create(oef1Activity.this, getResources().getIdentifier("uitleg_"+woorden.get(i), "raw", getPackageName()));
        ring.start();

    }

    public void volgendWoord(View v){
        i++;
        if(i == 4){
            startOef2();
        }
        else {
            toonFoto();
        }
    }

    public void startOef2(){
        Intent intent = new Intent(this, oef2Activity.class);
        startActivity(intent);
    }

    public void herhaalUitleg(View v){
        speelUitleg();
    }
}
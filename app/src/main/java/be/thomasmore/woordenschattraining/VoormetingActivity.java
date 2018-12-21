package be.thomasmore.woordenschattraining;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class VoormetingActivity extends AppCompatActivity {
    int KOLOM = 2;
    int RIJ = 2;
    int AANTAL = RIJ * KOLOM;
    int vraag = 0;
    int score = 0;

    ImageView fotos[] = new ImageView[AANTAL];
    List<String> woorden = Arrays.asList("duikbril", "klimtouw", "kroos", "riet", "val", "kompas", "steil", "zwaan", "kamp", "zaklamp");

    List<String> filenames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voormeting);

        /*Bundle bundle = getIntent().getExtras();
        toon(bundle.getLong("id") + "");*/
    }

    private void leesFotos()
    {
        filenames = new ArrayList<String>();
        Field[] drawables = be.thomasmore.woordenschattraining.R.drawable.class.getFields();
        for (Field f : drawables) {
            if (f.getName().startsWith("voormeting_")) {
                filenames.add(f.getName());
            }
        }
    }

    private void maakLayout()
    {
        List<String> fotosVraag = new ArrayList<String>();
        Collections.shuffle(filenames);

        for (int i = 0; i < filenames.size(); i++){
            if(filenames.get(i).contains(woorden.get(vraag))){
                List<String> tmpList = new ArrayList<String>(filenames);
                tmpList.remove(i);
                fotosVraag.add(filenames.get(i));
                fotosVraag.addAll(tmpList.subList(0, 3));
            }
        }
        Collections.shuffle(fotosVraag);
        int k = 0;
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.layout_voormeting);
        mainLayout.removeAllViews();
        for (int i = 0; i < RIJ; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            mainLayout.addView(linearLayout);
            for (int j = 0; j < KOLOM; j++) {
                ImageView imageView = new ImageView(this);

                LinearLayout.LayoutParams imageLayoutParams =
                        new LinearLayout.LayoutParams(600,500);
                imageLayoutParams.leftMargin = 5;
                imageLayoutParams.topMargin = 5;
                imageView.setLayoutParams(imageLayoutParams);

                imageView.setTag(fotosVraag.get(k));

                imageView.setImageResource(getResources().getIdentifier(fotosVraag.get(k), "drawable", getPackageName()));

                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        controlleerAntwoord((ImageView)v);
                    }
                });

                fotos[k] = imageView;
                k++;
                linearLayout.addView(imageView);
            }
        }
        MediaPlayer ring= MediaPlayer.create(VoormetingActivity.this, getResources().getIdentifier(woorden.get(vraag), "raw", getPackageName()));
        ring.start();
    }

    private void controlleerAntwoord(ImageView v){
        String antwoord = v.getTag().toString();
        if(antwoord.contains(woorden.get(vraag))){
            score++;
        }
        vraag++;
        if (vraag >= filenames.size()){
            Intent intent = new Intent(this, ConditieActivity.class);
            startActivity(intent);
            finish();
        } else {
            maakLayout();
        }
    }

    private void toon(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

}

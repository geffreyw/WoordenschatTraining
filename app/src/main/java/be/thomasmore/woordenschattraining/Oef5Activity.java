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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Oef5Activity extends AppCompatActivity {

    private DatabaseHelper db;

    int KOLOM = 2;
    int RIJ = 2;
    int AANTAL = RIJ * KOLOM;

    List<String> woordenA = Arrays.asList("klimtouw", "kroos", "riet");
    List<String> woordenB = Arrays.asList("val", "kompas", "steil");
    List<String> woordenC = Arrays.asList("zwaan", "kamp", "zaklamp");

    String woord = "duikbril";

    List[][] mtrx = new List[][]{
            {woordenA, woordenB, woordenC},
            {woordenC, woordenA, woordenB},
            {woordenB, woordenC, woordenA},
    };

    List<List<String>> woordenAvraag = Arrays.asList(
            Arrays.asList("klimtouw1", "klimtouw2", "klimtouw3", "wipwap"),
            Arrays.asList("eend", "kroos1", "kroos2", "kroos3"),
            Arrays.asList("riet1", "riet2", "riet3", "dolfijn")
    );
    List<List<String>> woordenBvraag = Arrays.asList(
            Arrays.asList("val1", "val2", "val3", "courier"),
            Arrays.asList("stoel", "kompas1", "kompas2", "kompas3"),
            Arrays.asList("steil1", "steil2", "steil3", "appel")
    );
    List<List<String>> woordenCvraag = Arrays.asList(
            Arrays.asList("zwaan1", "zwaan2", "zwaan3", "kat"),
            Arrays.asList("kamp1", "kamp2", "kamp3", "bal"),
            Arrays.asList("zaklamp1", "zaklamp2", "zaklamp3", "banaan")
    );

    List[][] mtrxVraagWoorden = new List[][]{
            {woordenAvraag, woordenBvraag, woordenCvraag},
            {woordenCvraag, woordenAvraag, woordenBvraag},
            {woordenBvraag, woordenCvraag, woordenAvraag},
    };

    List<String> vraagWoorden = Arrays.asList("duikbril1", "fiets", "duikbril2", "duikbril3");

    int vraag;

    int juist = 0;
    int aantalKeren = 0;
    String geselecteerdeAfbeelding;

    Test test;

    MediaPlayer ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef5);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        test = db.getTest(bundle.getLong("testId"));

        Kind kind = db.getKind(test.getKindId());

        vraag = bundle.getInt("vraag");

        int x = test.getConditie() - 1;
        int y = (int) kind.getGroepId() - 1;

        if (vraag != 0) {
            woord = ((List<String>) mtrx[y][x]).get(vraag - 1);
            vraagWoorden = ((List<List<String>>) mtrxVraagWoorden[y][x]).get(vraag - 1);
        }

        maakLayout();
        speelUitleg();

    }

    public void maakLayout() {
        List<String> shufled = new ArrayList<String>(vraagWoorden);
        Collections.shuffle(shufled);

        int k = 0;
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.layout_vraagWoorden);
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
                        new LinearLayout.LayoutParams(300, 200);
                imageLayoutParams.leftMargin = 5;
                imageLayoutParams.topMargin = 5;
                imageView.setLayoutParams(imageLayoutParams);

                imageView.setTag(shufled.get(k));

                imageView.setImageResource(getResources().getIdentifier(("oef5_" + shufled.get(k)).replaceAll("\\s+", ""), "drawable", getPackageName()));

                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        selecteer((ImageView) v);
                    }
                });


                k++;
                linearLayout.addView(imageView);
            }
        }
    }

    private void speelUitleg() {
        MediaPlayer audio = MediaPlayer.create(Oef5Activity.this, getResources().getIdentifier("oef5_"+woord, "raw", getPackageName()));
        audio.start();
    }

    public void selecteer(ImageView v) {
        geselecteerdeAfbeelding = v.getTag().toString();
        v.setVisibility(View.INVISIBLE);
    }

    public void onClickGroeneKader(View view) {
        aantalKeren++;

        if (geselecteerdeAfbeelding.contains(woord)) {
            juist++;
        }

        if(aantalKeren==4){
            controleer();
        }
    }

    public void onClickRodeKader(View view) {
        aantalKeren++;

        if (!geselecteerdeAfbeelding.contains(woord)) {
            juist++;
        }

        if(aantalKeren==4){
            controleer();
        }
    }

    private void controleer() {
        GetestWoord getestWoord = new GetestWoord();
        getestWoord.setOefening("Oef5");
        getestWoord.setTestId(test.getId());
        getestWoord.setWoord(woord);
        getestWoord.setAntwoord(false);
        if(juist == 4){
            getestWoord.setAntwoord(true);
            ring = MediaPlayer.create(this, getResources().getIdentifier("oef4_alles_juist", "raw", getPackageName()));
            ring.start();

            ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    volgende();
                }
            });
        } else {
            ring = MediaPlayer.create(this, getResources().getIdentifier("oef4_fout", "raw", getPackageName()));
            ring.start();

            ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    juist = 0;
                    maakLayout();
                }
            });
        }
        db.insertGetestWoord(getestWoord);
    }

    private void volgende() {
        Bundle bundle = new Bundle();
        bundle.putLong("testId", test.getId());
        bundle.putInt("vraag", vraag);
        Intent intent = new Intent();
        switch (test.getConditie()) {
            case 1: intent = new Intent(this, Oef6_1Activity.class);
                break;
            case 2: intent = new Intent(this, Oef6_2Activity.class);
                break;
            case 3: intent = new Intent(this, Oef6_3Activity.class);
                break;
        }

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}

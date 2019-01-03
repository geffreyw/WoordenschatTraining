package be.thomasmore.woordenschattraining;

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
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Oef4Activity extends AppCompatActivity {

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
            Arrays.asList("klimmen", "sterk", "de turnzaal", "het zwembad"),
            Arrays.asList("groen", "in de vijver", "de eend", "de lamp"),
            Arrays.asList("de vijver", "de eend", "het bos", "de bril")
    );
    List<List<String>> woordenBvraag = Arrays.asList(
            Arrays.asList("de pijn", "naar voor", "de pleister", "de appel"),
            Arrays.asList("wandelen", "de rugzak", "de landkaart", "het bad"),
            Arrays.asList("de berg", "beklimmen", "de trap", "de bloem")
    );
    List<List<String>> woordenCvraag = Arrays.asList(
            Arrays.asList("de vijver", "vleugels", "wit", "het boek"),
            Arrays.asList("de tent", "kampvuur", "in de slaapzak", "de deur"),
            Arrays.asList("het licht", "de batterij", "in het donker", "het paard")
    );

    List[][] mtrxVraagWoorden = new List[][]{
            {woordenAvraag, woordenBvraag, woordenCvraag},
            {woordenCvraag, woordenAvraag, woordenBvraag},
            {woordenBvraag, woordenCvraag, woordenAvraag},
    };

    List<String> vraagWoorden = Arrays.asList("ogen", "in de zee", "zwemmen", "schrijven");

    int vraag;

    List<String> fotos;
    //int i = 0;
    List<String> antwoorden = new ArrayList<String>();

    Test test;

    MediaPlayer ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef4);

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

    public void speelUitleg() {
        ring = MediaPlayer.create(Oef4Activity.this, getResources().getIdentifier("oef4_uitleg", "raw", getPackageName()));
        ring.start();

        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                zegWoord();
            }
        });
    }

    private void maakLayout() {
        TextView doelwoord = (TextView) findViewById(R.id.doelwoord);
        doelwoord.setText(woord);

        List<String> shufled = new ArrayList<String>(vraagWoorden);
        Collections.shuffle(shufled);

        int k = 0;
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.layout_vraagWoorden);
        mainLayout.removeAllViews();
        for (int i = 0; i < RIJ; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            mainLayout.addView(linearLayout);
            for (int j = 0; j < KOLOM; j++) {
                TextView textView = new TextView(this);
                ImageView imageView = new ImageView(this);

                textView.setGravity(1);
                textView.setTextSize(30);

                LinearLayout.LayoutParams imageLayoutParams =
                        new LinearLayout.LayoutParams(600, 500);
                imageLayoutParams.leftMargin = 5;
                imageLayoutParams.topMargin = 5;
                imageView.setLayoutParams(imageLayoutParams);

                imageView.setTag(shufled.get(k));

                imageView.setImageResource(getResources().getIdentifier(("oef4_" + shufled.get(k)).replaceAll("\\s+", ""), "drawable", getPackageName()));

                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        toevoegen((ImageView) v);
                    }
                });

                textView.setText(shufled.get(k));

                k++;
                linearLayout.addView(textView);
                linearLayout.addView(imageView);
            }
        }
    }

    public void zegWoord() {
        MediaPlayer audio = MediaPlayer.create(Oef4Activity.this, getResources().getIdentifier("voormeting_" + woord, "raw", getPackageName()));
        audio.start();
    }

    public void toevoegen(ImageView v) {
        final String antwoord = v.getTag().toString();
        //toon(antwoord);

        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.layout_antwoorden);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView textView = new TextView(this);
        ImageView imageView = new ImageView(this);

        textView.setGravity(1);
        textView.setTextSize(30);

        LinearLayout.LayoutParams imageLayoutParams =
                new LinearLayout.LayoutParams(400, 300);
        imageLayoutParams.leftMargin = 5;
        imageLayoutParams.topMargin = 5;
        imageView.setLayoutParams(imageLayoutParams);

        imageView.setTag(antwoord);

        imageView.setImageResource(getResources().getIdentifier(("oef4_" + antwoord).replaceAll("\\s+", ""), "drawable", getPackageName()));

        textView.setText(antwoord);

        linearLayout.addView(textView);
        linearLayout.addView(imageView);

        mainLayout.addView(linearLayout);

        antwoorden.add(antwoord);

        if (antwoorden.size() >= 3){
            GetestWoord getestWoord = new GetestWoord();
            getestWoord.setOefening("Oef4");
            getestWoord.setTestId(test.getId());
            getestWoord.setWoord(woord);
            getestWoord.setAntwoord(false);
            List<String> juist = vraagWoorden.subList(0, 3);
            int aantalJuist = 0;
            for (int i = 0; i <= juist.size()-1; i++){
                if(antwoorden.contains(juist.get(i))){
                    aantalJuist++;
                }
            }
            if(aantalJuist == 3){
                getestWoord.setAntwoord(true);
                ring = MediaPlayer.create(Oef4Activity.this, getResources().getIdentifier("oef4_alles_juist", "raw", getPackageName()));
                ring.start();

                ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        volgende();
                    }
                });
            } else {
                ring = MediaPlayer.create(Oef4Activity.this, getResources().getIdentifier("oef4_fout", "raw", getPackageName()));
                ring.start();

                ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mainLayout.removeAllViews();
                        antwoorden = new ArrayList<String>();
                    }
                });
            }
            db.insertGetestWoord(getestWoord);
        }
    }

    private void volgende(){
        Bundle bundle = new Bundle();
        bundle.putLong("testId", test.getId());
        bundle.putInt("vraag", vraag);
        Intent intent = new Intent();
        switch (test.getConditie()) {
            case 1: intent = new Intent(Oef4Activity.this, Oef6_1Activity.class);
                break;
            case 2: intent = new Intent(Oef4Activity.this, Oef6_2Activity.class);
                break;
            case 3: intent = new Intent(Oef4Activity.this, Oef6_3Activity.class);
                break;
        }

        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }
}

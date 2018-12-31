package be.thomasmore.woordenschattraining;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Oef3Activity extends AppCompatActivity {

    private DatabaseHelper db;

    List<String> woordenA = Arrays.asList("klimtouw", "kroos", "riet");
    List<String> woordenB = Arrays.asList("val", "kompas", "steil");
    List<String> woordenC = Arrays.asList("zwaan", "kamp", "zaklamp");

    String woord = "duikbril";

    List[][] mtrx = new List[][]{
            {woordenA, woordenB, woordenC},
            {woordenC, woordenA, woordenB},
            {woordenB, woordenC, woordenA},
    };

    List<String> goedOfFout = Arrays.asList("_goed", "_fout");

    int vraag;

    int i = 0;

    Test test;

    MediaPlayer ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef3);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        test = db.getTest(bundle.getLong("testId"));

        Kind kind = db.getKind(test.getKindId());

        vraag = bundle.getInt("vraag");

        int x = test.getConditie() - 1;
        int y = (int) kind.getGroepId() - 1;

        if (vraag != 0) {
            woord = ((List<String>) mtrx[y][x]).get(vraag - 1);
        }

        Collections.shuffle(goedOfFout);

        speelUitleg();
    }

    public void speelUitleg() {
        ring = MediaPlayer.create(Oef3Activity.this, getResources().getIdentifier("oef3_" + woord, "raw", getPackageName()));
        ring.start();

        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                speelZin();
            }
        });
    }

    public void speelZin() {
        ring = MediaPlayer.create(Oef3Activity.this, getResources().getIdentifier("oef3_" + woord + goedOfFout.get(i), "raw", getPackageName()));
        ring.start();
    }

    public void controleerAntwoord(View v) {
        String antwoord = v.getTag().toString();
        GetestWoord getestWoord = new GetestWoord();
        getestWoord.setOefening("Oef3");
        getestWoord.setTestId(test.getId());
        getestWoord.setWoord(woord + goedOfFout.get(i));
        getestWoord.setAntwoord(false);
        String correcteAntwoord = goedOfFout.get(i);
        if (antwoord.equals(correcteAntwoord)){
            getestWoord.setAntwoord(true);
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
        db.insertGetestWoord(getestWoord);
    }

    public void volgende(){
        i++;
        if (i == 2) {
            ring.stop();
            Bundle bundle = new Bundle();
            bundle.putLong("testId", test.getId());
            bundle.putInt("vraag", vraag);
            Intent intent = new Intent();
            //todo geffrey: activitys goed zetten
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
        else {
            ring.stop();
            speelZin();

        }
    }

    public void herhaalZin(View view) {
        speelZin();
    }

    public void stopZin(View view) {
        ring.stop();
    }
}

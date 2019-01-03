package be.thomasmore.woordenschattraining;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Paint;
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

public class Oef6_3Activity extends AppCompatActivity {

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

    int vraag;

    List<String> lettergreepA = Arrays.asList("klim - touw", "kroos", "riet");
    List<String> lettergreeB = Arrays.asList("val", "kom - pas", "steil");
    List<String> lettergreeC = Arrays.asList("zwaan", "kamp", "zak - lamp");

    List[][] lettergrepenMtrx = new List[][]{
            {lettergreepA, lettergreeB, lettergreeC},
            {lettergreeC, lettergreepA, lettergreeB},
            {lettergreeB, lettergreeC, lettergreepA},
    };

    String lettergreepWoord = "duik - bril";

    String foto;
    int i = 0;

    Test test;

    MediaPlayer ring;

    private ImageView imageView;
    long animationDuration = 1800;
    int lengte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef6_3);
        imageView = (ImageView) findViewById(R.id.konijn);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        test = db.getTest(bundle.getLong("testId"));

        Kind kind = db.getKind(test.getKindId());

        vraag = bundle.getInt("vraag");

        int x = test.getConditie() - 1;
        int y = (int) kind.getGroepId() - 1;

        if (vraag != 0) {
            woord = ((List<String>) mtrx[y][x]).get(vraag - 1);
            lettergreepWoord = ((List<String>) lettergrepenMtrx[y][x]).get(vraag - 1);
        }

        leesFoto();
        speelUitleg();
    }


    private void leesFoto() {
        foto = "";

        Field[] drawables = be.thomasmore.woordenschattraining.R.drawable.class.getFields();
        for (Field f : drawables) {
            if (f.getName().startsWith("voormeting_"+ woord)) {
                foto = f.getName();
            }
        }

        ImageView image = (ImageView) findViewById(R.id.afbeelding);
        image.setImageResource(getResources().getIdentifier(foto, "drawable", getPackageName()));
    }

    public void maakLayout(){
        TextView woordDeel1 = (TextView) findViewById(R.id.woordDeel1);
        TextView woordDeel2 = (TextView) findViewById(R.id.woordDeel2);
        TextView koppelteken = (TextView) findViewById(R.id.koppelteken);


        if(lettergreepWoord.contains(" ")){
            String[] parts = lettergreepWoord.split("-");
            String part1 = parts[0];
            String part2 = parts[1];

            woordDeel1.setText(part1);
            woordDeel2.setText(part2);
            woordDeel2.setPaintFlags(woordDeel1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            koppelteken.setText("-");
        }
        else{

            woordDeel1.setText(lettergreepWoord);
            woordDeel2.setText("");
            koppelteken.setText("");
        }
        woordDeel1.setPaintFlags(woordDeel1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        woordDeel1.measure(0,0);
        lengte = woordDeel1.getMeasuredWidth();

    }

    public void speelUitleg() {
        ring = MediaPlayer.create(Oef6_3Activity.this, getResources().getIdentifier("oef6_3_" + woord, "raw", getPackageName()));
        ring.start();

        maakLayout();

        ring.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                speelZin();
            }
        });
    }

    public void speelZin() {
        ring = MediaPlayer.create(Oef6_3Activity.this, getResources().getIdentifier("oef6_3_klank_" + woord, "raw", getPackageName()));
        ring.start();

        if(lettergreepWoord.contains(" ")){
            handleAnimationDubbel();
        }
        else{
            handleAnimationEnkel();
        }
    }

    public void handleAnimationEnkel(){
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "y", 150,100,150);
        animatorY.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();
    }

    public void handleAnimationDubbel(){
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(imageView, "y", 150,100,150,100,150);
        animatorY.setDuration(animationDuration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorY);
        animatorSet.start();
    }

    public void volgendWoord(View v){
        ring.stop();
        Bundle bundle = new Bundle();
        bundle.putLong("testId", test.getId());
        Intent intent;
        if (vraag >= 3) {
            bundle.putString("meeting", "nameting");
            intent = new Intent(this, VoormetingActivity.class);
        } else {
            bundle.putInt("vraag", vraag+1);
            intent = new Intent(this, Oef1Activity.class);
        }
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void herhaalZin(View view) {
        speelUitleg();
    }
}

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

    List<String> woorden = Arrays.asList("duikbril", "klimtouw", "kroos", "riet");
    List<String> woordenLettergreep = Arrays.asList("duik - bril", "klim - touw", "kroos", "riet");
    int i=0;
    MediaPlayer audio;
    List<String> fotos;

    private ImageView imageView;
    long animationDuration = 1000;
    int lengte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef6_3);
        imageView = (ImageView) findViewById(R.id.konijn);

        leesFotos();
        speelUitleg();
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
        TextView woordDeel1 = (TextView) findViewById(R.id.woordDeel1);
        TextView woordDeel2 = (TextView) findViewById(R.id.woordDeel2);
        TextView koppelteken = (TextView) findViewById(R.id.koppelteken);

        if(woordenLettergreep.get(i).contains(" ")){
            String[] parts = woordenLettergreep.get(i).split("-");
            String part1 = parts[0];
            String part2 = parts[1];

            woordDeel1.setText(part1);
            woordDeel2.setText(part2);
            woordDeel2.setPaintFlags(woordDeel1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            koppelteken.setText("-");
            handleAnimationDubbel();
        }
        else{

            woordDeel1.setText(woordenLettergreep.get(i));
            woordDeel2.setText("");
            koppelteken.setText("");
            handleAnimationEnkel();
        }
        woordDeel1.setPaintFlags(woordDeel1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        woordDeel1.measure(0,0);
        lengte = woordDeel1.getMeasuredWidth();

        ImageView image = (ImageView) findViewById(R.id.afbeelding);
        image.setImageResource(getResources().getIdentifier(fotos.get(i), "drawable", getPackageName()));

        speelZin();
    }

    public void speelUitleg() {
        audio = MediaPlayer.create(Oef6_3Activity.this, getResources().getIdentifier("oef6_3_uitleg", "raw", getPackageName()));
        audio.start();

        audio.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                maakLayout();
            }
        });
    }

    public void speelZin() {
        audio = MediaPlayer.create(Oef6_3Activity.this, getResources().getIdentifier("voormeting_" + woorden.get(i), "raw", getPackageName()));
        audio.start();
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
        i++;
        if(i == 4){
            startNameting();
        }
        else {
            maakLayout();
        }
    }

    public void herhaalZin(View view) {
        maakLayout();
    }

    public void startNameting(){
        Intent intent = new Intent(this, VoormetingActivity.class);
        startActivity(intent);
    }
}

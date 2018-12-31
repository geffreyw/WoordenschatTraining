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

    String foto;

    Test test;

    int i = 0;

    MediaPlayer ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef2);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        test = db.getTest(bundle.getLong("testId"));

        Kind kind = db.getKind(test.getKindId());

        vraag = bundle.getInt("vraag");

        int x = test.getConditie() - 1;
        int y = (int) kind.getGroepId() - 1;

        if (vraag != 0){
            woord = ((List<String>) mtrx[y][x]).get(vraag-1);
        }

        leesFoto();
        toonFoto();
    }

    private void leesFoto() {
        foto = "";

        Field[] drawables = be.thomasmore.woordenschattraining.R.drawable.class.getFields();
        for (Field f : drawables) {
            if (f.getName().startsWith("voormeting_"+ woord)) {
                foto = f.getName();
            }
        }
    }

    public void toonFoto() {
        TextView woord = (TextView) findViewById(R.id.woord);
        woord.setText(woorden.get(i));

        ImageView image = (ImageView) findViewById(R.id.afbeelding);
        image.setImageResource(getResources().getIdentifier(foto, "drawable", getPackageName()));

        speelUitleg();

    }

    public void speelUitleg(){
        ring = MediaPlayer.create(Oef2Activity.this, getResources().getIdentifier("oef2_"+woord, "raw", getPackageName()));
        ring.start();

    }

    public void volgendWoord(View v){
        ring.stop();
        Bundle bundle = new Bundle();
        bundle.putLong("testId", test.getId());
        bundle.putInt("vraag", vraag);
        Intent intent = new Intent(this, Oef3Activity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void herhaalUitleg(View v){
        speelUitleg();
    }
}

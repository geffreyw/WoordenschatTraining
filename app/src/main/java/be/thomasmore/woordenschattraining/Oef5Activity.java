package be.thomasmore.woordenschattraining;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class Oef5Activity extends AppCompatActivity {

    List<String> woorden = Arrays.asList("duikbril","klimtouw", "kroos", "riet");
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oef5);
    }

    public void maakLayout(){
        ImageView image1 = (ImageView) findViewById(R.id.afbeelding1);
        image1.setImageResource(getResources().getIdentifier(("oef5_"+ woorden.get(i)+"1"), "drawable", getPackageName()));

        ImageView image2 = (ImageView) findViewById(R.id.afbeelding2);
        image2.setImageResource(getResources().getIdentifier(("oef5_"+ woorden.get(i)+"2"), "drawable", getPackageName()));

        ImageView image3 = (ImageView) findViewById(R.id.afbeelding3);
        image3.setImageResource(getResources().getIdentifier(("oef5_"+ woorden.get(i)+"3"), "drawable", getPackageName()));

        ImageView image4 = (ImageView) findViewById(R.id.afbeelding4);
        image4.setImageResource(getResources().getIdentifier(("oef5_"+ woorden.get(i)+"4"), "drawable", getPackageName()));
    }

    public void toevoegen(View view) {
    }
}

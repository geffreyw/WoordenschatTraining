package be.thomasmore.woordenschattraining;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoormetingActivity extends AppCompatActivity {
    int KOLOM = 2;
    int RIJ = 2;
    int AANTAL = RIJ * KOLOM;
    ImageView fotos[] = new ImageView[AANTAL];

    List<String> filenames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voormeting);

        leesFotos();
        maakLayout();
    }

    private void leesFotos()
    {
        filenames = new ArrayList<String>();
        Field[] drawables = be.thomasmore.woordenschattraining.R.drawable.class.getFields();
        for (Field f : drawables) {
            if (f.getName().startsWith("logo_")) {
                filenames.add(f.getName());
            }
        }
        Collections.shuffle(filenames);
    }

    private void maakLayout()
    {
        int k = 0;
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.layout_voormeting);
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
                        new LinearLayout.LayoutParams(300,300);
                imageLayoutParams.leftMargin = 5;
                imageLayoutParams.topMargin = 5;
                imageView.setLayoutParams(imageLayoutParams);

                imageView.setTag(filenames.get(k));

                imageView.setImageResource(getResources().getIdentifier(filenames.get(k), "drawable", getPackageName()));

                /*imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        toonVraag((ImageView)v);
                    }
                });*/

                fotos[k] = imageView;
                k++;
                linearLayout.addView(imageView);
            }
        }
    }

}

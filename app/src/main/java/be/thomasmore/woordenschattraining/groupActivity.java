package be.thomasmore.woordenschattraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class groupActivity extends AppCompatActivity {

    int gekozenGroep = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

    }

    public void onClickGroep(View v) {
        Button keuze = (Button) v;
        this.gekozenGroep = Integer.parseInt(keuze.getTag().toString());

    }

    public void toonPreteachingPlaat(View v) {
        Intent intent = new Intent(this, PreteachingActivity.class);
        startActivity(intent);
    }

}

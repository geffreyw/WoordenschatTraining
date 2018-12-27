package be.thomasmore.woordenschattraining;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PreteachingActivity extends AppCompatActivity {

    long testId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preteaching);

        Bundle bundle = getIntent().getExtras();
        testId = (int)bundle.getLong("testId");

        new CountDownTimer(1000, 1000) {
            public void onFinish() {
                MediaPlayer ring = MediaPlayer.create(PreteachingActivity.this, getResources().getIdentifier("preteachingplaat", "raw", getPackageName()));
                ring.start();
            }
            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();
    }

    public void bomen_onClick(View v){
        Bundle bundle = new Bundle();
        bundle.putLong("testId", testId);
        bundle.putInt("vraag", 0);

        Intent intent = new Intent(this, oef1Activity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}

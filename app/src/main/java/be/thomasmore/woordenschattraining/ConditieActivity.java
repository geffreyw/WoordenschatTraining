package be.thomasmore.woordenschattraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

public class ConditieActivity extends AppCompatActivity {

    private DatabaseHelper db;

    private long kindId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditie);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        kindId = (int)bundle.getLong("id");
    }

    public void onClickGroep(View v) {
        Test test = new Test();

        Button keuze = (Button) v;
        test.setConditie(Integer.parseInt(keuze.getTag().toString()));
        test.setKindId(kindId);
        test.setDatum(new Date().toString());

        long testId = db.insertTest(test);

        toonVoormeting(testId);
    }

    public void toonVoormeting(long testId) {
        Bundle bundle = new Bundle();
        bundle.putLong("testId", testId);
        bundle.putString("meeting", "voormeting");
        bundle.putInt("vraag", 1);

        Intent intent = new Intent(this, VoormetingActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void toon(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

}

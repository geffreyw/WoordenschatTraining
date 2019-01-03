package be.thomasmore.woordenschattraining;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ResultaatActivity extends AppCompatActivity {
    private DatabaseHelper db;

    private Test test;

    //todo Iemand: Overicht per oefening sorteren?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultaat);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        test = db.getTest(bundle.getLong("testId"));

        Date datum = new Date(test.getDatum());

        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");

        String titel = "Resultaat van " + db.getKind(test.getKindId()).getNaam() + " op " + fmtOut.format(datum);

        TextView resultaatTitelTextView = (TextView)findViewById(R.id.resultaatTitelTextView);
        resultaatTitelTextView.setText(titel);

        List<GetestWoord> getesteWoordFromTest = db.getGetesteWoordFromTest(test.getId());

        ArrayAdapter<GetestWoord> adapter =
                new ArrayAdapter<GetestWoord>(this,
                        android.R.layout.simple_list_item_1, getesteWoordFromTest);

        final ListView listViewGetesteWoorden =
                (ListView) findViewById(R.id.getesteWoordenListView);
        listViewGetesteWoorden.setAdapter(adapter);
    }



    private void toon(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }

}

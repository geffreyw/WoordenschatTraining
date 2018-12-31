package be.thomasmore.woordenschattraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class TestenKindActivity extends AppCompatActivity {

    private DatabaseHelper db;

    Kind kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testen_kind);

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        kind = db.getKind(bundle.getLong("id"));

        String titel = "Overzicht van " + kind.getNaam();

        TextView overzichtTitelTextView = (TextView)findViewById(R.id.overzichtTitelTextView);
        overzichtTitelTextView.setText(titel);

        final List<Test> testen = db.getTestenFromKind(kind.getId());

        ArrayAdapter<Test> adapter =
                new ArrayAdapter<Test>(this,
                        android.R.layout.simple_list_item_1, testen);

        final ListView listViewTeste =
                (ListView) findViewById(R.id.testenListView);
        listViewTeste.setAdapter(adapter);

        listViewTeste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parentView, View childView, int position, long id) {
                Test testKind = testen.get(position);
                Bundle bundle = new Bundle();
                bundle.putLong("testId", testKind.getId());

                Intent intent = new Intent(TestenKindActivity.this, ResultaatActivity.class);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}

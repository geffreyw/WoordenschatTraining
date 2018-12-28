package be.thomasmore.woordenschattraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper db;

    private List<Kind> kinderen;

    private List<Groep> groepen;

    private Kind kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        readKinderen();
        readGroepen();
        toonKinderen();
        toonGroepen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void readKinderen() {
        kinderen = db.getKinderen();
    }

    private void readGroepen() {
        groepen = db.getGroepen();
    }

    private void toonKinderen() {
        ArrayAdapter<Kind> adapter =
                new ArrayAdapter<Kind>(this,
                        android.R.layout.simple_list_item_1, kinderen);

        final ListView listViewKinderen =
                (ListView) findViewById(R.id.kinderenListView);
        listViewKinderen.setAdapter(adapter);

        listViewKinderen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parentView, View childView, int position, long id) {
                kind = kinderen.get(position);
            }
        });
    }

    private void toonGroepen() {
        ArrayAdapter<Groep> adapter =
                new ArrayAdapter<Groep>(this,
                        android.R.layout.simple_list_item_1, groepen);

        Spinner spinnerAllGroepen =
                (Spinner) findViewById(R.id.allGroepenSpinner);

        spinnerAllGroepen.setAdapter(adapter);

        spinnerAllGroepen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(parentView.getId() == R.id.allGroepenSpinner){
                    filterKinderen(selectedItemView);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void filterKinderen(View V) {
        Spinner spinner = (Spinner) findViewById(R.id.allGroepenSpinner);
        Groep groep = (Groep) spinner.getSelectedItem();
        kinderen = db.getKinderenByGroep(groep.getId());
        toonKinderen();
    }

    public void toonVoormeting(View v) {
        if (kind != null){
            Bundle bundle = new Bundle();
            bundle.putLong("id", kind.getId());

            Intent intent = new Intent(this, ConditieActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            kind = null;
        } else {
            toon("Selecteer een kind!");
        }
    }

    public void toonBeheren(View v) {
        Intent intent = new Intent(this, BeherenActivity.class);
        startActivity(intent);
    }

    private void toon(String tekst)
    {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }
}

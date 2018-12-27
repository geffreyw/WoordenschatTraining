package be.thomasmore.woordenschattraining;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class BeherenActivity extends AppCompatActivity {

    private DatabaseHelper db;

    private List<Kind> kinderen;

    private List<Groep> groepen;

    private Kind kind = new Kind();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beheren);

        db = new DatabaseHelper(this);

        readKinderen();
        readGroepen();
        toonKinderen();
        toonGroepen();
    }

    private int getPosition(long groepId) {
        int found = -1;
        for (int i = 0; i < groepen.size(); i++) {
            if (groepen.get(i).getId() == groepId) {
                found = i;
            }
        }
        return found;
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
                toonKind(kinderen.get(position));
            }
        });
    }

    private void toonKind(Kind kind) {
        this.kind = kind;
        EditText naamEditText = (EditText) findViewById(R.id.naamEditText);
        naamEditText.setText(kind.getNaam());

        Spinner groepSpinner = (Spinner) findViewById(R.id.groepSpinner);
        groepSpinner.setSelection(getPosition(kind.getGroepId()));
    }

    private void toonGroepen() {
        ArrayAdapter<Groep> adapter =
                new ArrayAdapter<Groep>(this,
                        android.R.layout.simple_list_item_1, groepen);

        Spinner spinnerAllGroepen =
                (Spinner) findViewById(R.id.allGroepenSpinner);

        spinnerAllGroepen.setAdapter(adapter);

        Spinner spinnerGroep =
                (Spinner) findViewById(R.id.groepSpinner);

        spinnerGroep.setAdapter(adapter);

        spinnerAllGroepen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (parentView.getId() == R.id.allGroepenSpinner) {
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

    public void buttonUpdate_onClick(View v) {
        Kind editKind = kind;

        EditText naamEditText = (EditText) findViewById(R.id.naamEditText);
        editKind.setNaam(naamEditText.getText() + "");

        Spinner spinner = (Spinner) findViewById(R.id.groepSpinner);
        Groep groep = (Groep) spinner.getSelectedItem();
        editKind.setGroepId(groep.getId());

        if (editKind.getId() == 0) {
            db.insertKind(editKind);
        } else {
            db.updateKind(editKind);
        }

        readKinderen();
        toonKinderen();
        toonGroepen();
        toonKind(new Kind());
        toon(editKind.getNaam() + " aangepast");
    }

    public void buttonNew_onClick(View v) {
        toonKind(new Kind());
    }

    public void buttonDelete_onClick(View v) {
        if (kind.getId() != 0) {
            db.deleteKind(kind.getId());
            readKinderen();
            toonKinderen();
            toonGroepen();
            toon(kind.getNaam() + " verwijderd");
            toonKind(new Kind());
        }
    }

    public void buttonDeleteTesten_onClick(View v) {
        if (db.deleteTesten()){
            toon("Testen verwijderd!");
        }
        else {
            toon("Error!");
        }
    }

    private void toon(String tekst) {
        Toast.makeText(getBaseContext(), tekst, Toast.LENGTH_SHORT).show();
    }
}

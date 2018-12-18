package be.thomasmore.woordenschattraining;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 4;
    // Database Name
    private static final String DATABASE_NAME = "woordTraining";

    // uitgevoerd bij instantiatie van DatabaseHelper
    // -> als database nog niet bestaat, dan creëren (call onCreate)
    // -> als de DATABASE_VERSION hoger is dan de huidige versie,
    //    dan upgraden (call onUpgrade)

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // methode wordt uitgevoerd als de database gecreëerd wordt
    // hierin de tables creëren en opvullen met gegevens
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_GROEP = "CREATE TABLE groep (" +
                "id INTEGER PRIMARY KEY," +
                "naam TEXT)";
        db.execSQL(CREATE_TABLE_GROEP);

        String CREATE_TABLE_KIND = "CREATE TABLE kind (" +
                "id INTEGER PRIMARY KEY," +
                "naam TEXT," +
                "groepId INTEGER," +
                "FOREIGN KEY (groepId) REFERENCES groep(id))";
        db.execSQL(CREATE_TABLE_KIND);

        String CREATE_TABLE_TEST = "CREATE TABLE test (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "kindId INTEGER," +
                "score INTEGER, " +
                "datum TEXT," +
                "FOREIGN KEY (kindId) REFERENCES kind(id))";
        db.execSQL(CREATE_TABLE_TEST);

        insertGroepen(db);
        insertKinderen(db);
    }

    private void insertKinderen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO kind (id, naam, groepId) VALUES (1, 'Kim Moelants', 1);");
        db.execSQL("INSERT INTO kind (id, naam, groepId) VALUES (2, 'Gefke Wuist', 2);");
        db.execSQL("INSERT INTO kind (id, naam, groepId) VALUES (3, 'Joske Dirks', 3);");
    }

    private void insertGroepen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO groep (id, naam) VALUES (1, 'Groep 1');");
        db.execSQL("INSERT INTO groep (id, naam) VALUES (2, 'Groep 2');");
        db.execSQL("INSERT INTO groep (id, naam) VALUES (3, 'Groep 3');");
    }

    // methode wordt uitgevoerd als database geupgrade wordt
    // hierin de vorige tabellen wegdoen en opnieuw creëren
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS kind");
        db.execSQL("DROP TABLE IF EXISTS test");
        db.execSQL("DROP TABLE IF EXISTS groep");

        // Create tables again
        onCreate(db);
    }

    //-------------------------------------------------------------------------------------------------
    //  CRUD Operations
    //-------------------------------------------------------------------------------------------------

    // insert-methode met ContentValues
    public long insertKind(Kind kind) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("naam", kind.getNaam());
        values.put("groepId", kind.getGroepId());

        long id = db.insert("kind", null, values);

        db.close();
        return id;
    }

    // update-methode met ContentValues
    public boolean updateKind(Kind kind) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("naam", kind.getNaam());
        values.put("groepId", kind.getGroepId());

        int numrows = db.update(
                "kind",
                values,
                "id = ?",
                new String[] { String.valueOf(kind.getId()) });

        db.close();
        return numrows > 0;
    }

    // delete-methode
    public boolean deleteKind(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int numrows = db.delete(
                "kind",
                "id = ?",
                new String[] { String.valueOf(id) });

        db.close();
        return numrows > 0;
    }

    // query-methode
    public Kind getKind(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "kind",      // tabelnaam
                new String[] { "id", "naam", "groepId" }, // kolommen
                "id = ?",  // selectie
                new String[] { String.valueOf(id) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??

        Kind kind = new Kind();

        if (cursor.moveToFirst()) {
            kind = new Kind(cursor.getLong(0),
                    cursor.getString(1), cursor.getInt(2));
        }
        cursor.close();
        db.close();
        return kind;
    }

    // rawQuery-methode
    public List<Kind> getKinderen() {
        List<Kind> lijst = new ArrayList<Kind>();

        String selectQuery = "SELECT  * FROM kind ORDER BY naam";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Kind kind = new Kind(cursor.getLong(0),
                        cursor.getString(1), cursor.getInt(2));
                lijst.add(kind);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // rawQuery-methode
    public List<Kind> getKinderenByGroep(long groepId) {
        List<Kind> lijst = new ArrayList<Kind>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "kind",      // tabelnaam
                new String[] { "id", "naam", "groepId" }, // kolommen
                "groepId = ?",  // selectie
                new String[] { String.valueOf(groepId) }, // selectieparameters
                null,           // groupby
                null,           // having
                null,           // sorting
                null);          // ??
        if (cursor.moveToFirst()) {
            do {
                Kind kind = new Kind(cursor.getLong(0),
                        cursor.getString(1), cursor.getInt(2));
                lijst.add(kind);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // rawQuery-methode
    public List<Test> getTesten() {
        List<Test> lijst = new ArrayList<Test>();

        String selectQuery = "SELECT  * FROM test ORDER BY datum";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Test test = new Test(cursor.getLong(0), cursor.getLong(1), cursor.getInt(2), cursor.getString(3));
                lijst.add(test);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

    // rawQuery-methode
    public List<Groep> getGroepen() {
        List<Groep> lijst = new ArrayList<Groep>();

        String selectQuery = "SELECT  * FROM groep ORDER BY naam";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Groep groep = new Groep(cursor.getLong(0), cursor.getString(1));
                lijst.add(groep);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lijst;
    }

}

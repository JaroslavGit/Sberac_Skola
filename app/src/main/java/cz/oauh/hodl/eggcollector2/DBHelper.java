package cz.oauh.hodl.eggcollector2;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import java.sql.Date;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "collect.db";
    //table pocasi
    public static final String POCASI_TABLE_NAME = "pocasi";
    public static final String POCASI_COLUMN_ID = "id";
    public static final String POCASI_COLUMN_NAZEV = "name";
    public static final String POCASI_COLUMN_KOD = "kod";
    //table specialni akce
    public static final String AKCE_TABLE_NAME = "akce";
    public static final String AKCE_COLUMN_ID = "id";
    public static final String AKCE_COLUMN_nazev = "nazev";
    public static final String AKCE_COLUMN_kod = "kod";
    //table kdy nastala spec akce
    public static final String SPECIAL_TABLE_NAME = "special";
    public static final String SPECIAL_COLUMN_ID = "id";
    public static final String SPECIAL_COLUMN_DATUM = "datum";
    public static final String SPECIAL_COLUMN_IDAKCE = "idakce";
    public static final String SPECIAL_COLUMN_POCETKS = "pocetks";
    public static final String SPECIAL_COLUMN_cena = "cena";
    public static final String SPECIAL_COLUMN_POZN = "poznamka";
    //table sber
    public static final String SBER_TABLE_NAME = "sber";
    public static final String SBER_COLUMN_ID = "id";
    public static final String SBER_COLUMN_DATUM = "datum";
    public static final String SBER_COLUMN_POCETVAJEC = "pocetvajec";
    public static final String SBER_COLUMN_POZN = "poznamka";
    public static final String SBER_COLUMN_IDPOCASI = "idpocasi";
    public static final String SBER_COLUMN_STUPNE = "stupne";



    private HashMap hp;

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table pocasi (id integer primary key AUTOINCREMENT, name text, kod integer)"
        );
        //  insertPocasi();
        db.execSQL(
                "create table sber (id integer primary key AUTOINCREMENT, datum Date, pocetvajec integer NOT NULL, poznamka text, idpocasi integer, stupne integer, FOREIGN KEY(idpocasi) REFERENCES pocasi(id) )"
        );
        db.execSQL(
                "create table akce (id integer primary key AUTOINCREMENT, nazev text, kod integer)"
        );
        //   insertAkce();
        db.execSQL(
                "create table special (id integer primary key AUTOINCREMENT, datum Date, pocetks integer, cena integer, nazev text, poznamka text, idakce integer, FOREIGN KEY(idakce) REFERENCES akce(id) )"
        );
    }

   @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertAkce()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  AKCE");
        db.execSQL("INSERT INTO AKCE (nazev,  kod) VALUES('Odebráno slepic', 0)");
        db.execSQL("INSERT INTO AKCE (nazev, kod) VALUES('Nákup slepic', 1)");
        db.execSQL("INSERT INTO AKCE (nazev,  kod) VALUES('Nákup krmiva', 2)");
        db.execSQL("INSERT INTO AKCE (nazev,  kod) VALUES('Prodej vajec', 3)");
        db.execSQL("INSERT INTO AKCE (nazev,  kod) VALUES('Darovány vejce', 4)");
        return true;
    }
    public boolean insertPocasi()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  pocasi");
        db.execSQL("INSERT INTO pocasi (name, kod) VALUES('Déšť', 0)");
        db.execSQL("INSERT INTO pocasi (name, kod) VALUES('Zataženo', 1)");
        db.execSQL("INSERT INTO pocasi (name, kod) VALUES('Slunečno', 2)");
        db.execSQL("INSERT INTO pocasi (name, kod) VALUES('Sníh', 3)");
        return true;
    }

    //zjistí všechny počasí uložené v DB, případně počasí se zadaným ID
    public Cursor getPocasi(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        if(id==0) { //všechny
            return  db.rawQuery("select * from pocasi", null);
        } else { //jen jedno
            return db.rawQuery("select * from pocasi where id=" + id + "", null);
        }
    }
  //ulož nový sběr
    public int insertNew(Date date, int pocetVajec, int idPocasi, String pozn, int stupne)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement("INSERT INTO sber (datum, pocetvajec, idpocasi, poznamka, stupne) VALUES (?,?,?,?,?)");
        stmt.bindString(1, date.toString());
        stmt.bindLong(2, pocetVajec);
        stmt.bindLong(3, idPocasi);
        stmt.bindString(4, pozn);
        stmt.bindLong(5, stupne);
        stmt.executeInsert() ;
        return 0;
    }

    //vrátí všechny sběry
    public Cursor getSber() {
        SQLiteDatabase db = this.getReadableDatabase();
        return  db.rawQuery("select * from sber order by datum", null);
    }

    public int numberOfRowsPocasi(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, POCASI_TABLE_NAME);
        return numRows;
    }

    public int getIDPocasi(String nazev)
    {
       SQLiteDatabase db = this.getReadableDatabase();
       String sql = "SELECT ID FROM POCASI WHERE NAME = '" + nazev + "'";
       System.out.println(sql);
       Cursor c = db.rawQuery(sql, null);
       c.moveToFirst();
       return c.getInt(0);
    }

}
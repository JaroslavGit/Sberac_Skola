package cz.oauh.hodl.eggcollector2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.view.*;
import android.content.*;

public class MainActivity extends AppCompatActivity {
    //komponenty z Layoutu
 private com.applandeo.materialcalendarview.CalendarView kalendar;
 private com.shawnlin.numberpicker.NumberPicker vejce;
 private com.shawnlin.numberpicker.NumberPicker pocasi;
 private com.shawnlin.numberpicker.NumberPicker stupne;
 private DBHelper db; //naše databáze
 //vybrany den z kalendáře
 private Calendar vybranyDen;
 //seznam událostí sběru
 private List<EventDay> events;
    String[] pocasiPole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.db = new DBHelper(this);
        //zpřístupni komponentu z layoutu
        kalendar = this.findViewById(R.id.mycalendar);
        //vytvořím prázdný seznam událost
        events = new ArrayList<>();
        fillEvents(); //do kalendáře načtu záznamy ze sběru
        fillSpinners(); //do spinneru chci nastavit data z tabulky pocasi

        //nastavím kalendáři události
        kalendar.setEvents(events);
        //nastav vybrany den
       kalendar.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                vybranyDen = eventDay.getCalendar();
            }
        });
    }

    private void fillEvents()
    {
      Cursor datumy = db.getSber();
      //vytáhnu si datumy sběru a dám si je do Eventu
      datumy.moveToFirst();
        while(!datumy.isAfterLast())
        {
         java.sql.Date datum = java.sql.Date.valueOf(datumy.getString(1));
         Calendar cal = Calendar.getInstance();
         cal.setTimeInMillis(datum.getTime());
         events.add(new EventDay(cal, R.drawable.ic_egg));
         datumy.moveToNext();
        }
        //nastavím kalendáři události
        kalendar.setEvents(events);
    }

    private void fillSpinners()
    {
     if(db.numberOfRowsPocasi()==0)
     {
     db.insertPocasi();
     }
     Cursor data = db.getPocasi(0); //vrátit všechny texty z tabuzlky pocasi
     data.moveToFirst(); //jdi na první zaznam
     List<String> pocasiList = new ArrayList<String>();
     //projdu data, která vrátil SELECT
     while(!data.isAfterLast())
     {
      pocasiList.add(data.getString(1)); //vytahnu si nazev pocasi
      data.moveToNext();
     }
     //numberpicker
     pocasi = this.findViewById(R.id.weather_picker);

     //nechám si je zobrazit spinnerem, který ukazuje počasí
        pocasiPole = new String[pocasiList.size()];
        pocasiPole = pocasiList.toArray(pocasiPole);
        pocasi.setDisplayedValues(pocasiPole);
        pocasi.setMinValue(1);
        pocasi.setMaxValue(pocasiPole.length);
        pocasi.setValue(2);
    }
    //zmackl jsem tlacitjko ULOZ
    public void saveData(View v)
    {
    vejce = this.findViewById(R.id.egg_count_picker);
    pocasi = this.findViewById(R.id.weather_picker);
    stupne = this.findViewById(R.id.degree_picker);
    java.sql.Date datum = new java.sql.Date(vybranyDen.getTimeInMillis());
    /*System.out.println("Vybral jsem "+stupne.getValue() + " °C");
     System.out.println("Vybrali jste datum: "+vybranyDen.getTime().toString());
    */
     //vytahnu si ID pocasi pomocí textu, který je v numberpickeruString[] pData = pocasi.getDisplayedValues();
     int idP = db.getIDPocasi(pocasiPole[pocasi.getValue()]);
     db.insertNew(datum, vejce.getValue(), idP, "", stupne.getValue());
     Toast.makeText(this, "Data byla uložena", Toast.LENGTH_SHORT).show();
     events.add(new EventDay(vybranyDen, R.drawable.ic_egg));
        //nastavím kalendáři události
        kalendar.setEvents(events);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_zadej:
                Intent akt = new Intent(this, MainActivity.class);
                startActivity(akt);
                return true;//..atd
            //case R.id.menu_ //atd
            case R.id.menu_item_statistic:
                Intent akt2 = new Intent(this, StatisticActivity.class);
                startActivity(akt2);
                return true;
            default: return false;
        }
    }


}
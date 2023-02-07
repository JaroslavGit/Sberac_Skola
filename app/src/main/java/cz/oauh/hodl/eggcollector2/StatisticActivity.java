package cz.oauh.hodl.eggcollector2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {
    private ListView seznam; //seznam řádků se sběrem
    private List<CollectCl> sbery; //data z databaze
    private DBHelper db; //naše databáze
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        seznam = (ListView) this.findViewById(R.id.collectList);
        sbery = new ArrayList<>();
        this.db = new DBHelper(this);
        nactiZDB(); //nacte do Listu data z db
    }

    private void nactiZDB()
    {
      Cursor sberyData = db.getSber();
      sberyData.moveToFirst();
        while(!sberyData.isAfterLast())
        {//z dat si vytvořím CollectCl a uložím ho do Listu sbery
          CollectCl zaznam = new CollectCl(sberyData.getString(1), sberyData.getInt(2), sberyData.getInt(4), sberyData.getInt(5));
          sbery.add(zaznam);
          sberyData.moveToNext();
        }
        CollectAdapter cAdapt = new CollectAdapter(this, sbery);
        seznam.setAdapter(cAdapt);
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
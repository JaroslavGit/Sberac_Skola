package cz.oauh.hodl.eggcollector2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.*;
import java.util.*;

public class CollectAdapter extends BaseAdapter {

    private Context cont;
    private List<CollectCl> values;

    public CollectAdapter(Context cont, List<CollectCl> values) {
        this.cont = cont;
        this.values = values;
    }

    @Override
    public int getCount() {
       return values.size();
    }

    @Override
    public Object getItem(int i) {
        return values.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(cont).inflate(R.layout.statistic_layout, viewGroup, false);
        }
        TextView datum = view.findViewById(R.id.stat_datum);
        TextView den = view.findViewById(R.id.stat_den);
        TextView pocV = view.findViewById(R.id.stat_pocV);
        ImageView obr = view.findViewById(R.id.stat_pocasi);
        TextView stupne = view.findViewById(R.id.stat_stupne);

        CollectCl sber = values.get(i);

        datum.setText(sber.getDate());
        den.setText(sber.getDate());
        pocV.setText(sber.getEggCount() + " vajec");
        obr.setImageResource(R.drawable.ic_egg);
        stupne.setText(sber.getDegreeValue() + " °C");
        return view;
    }
}


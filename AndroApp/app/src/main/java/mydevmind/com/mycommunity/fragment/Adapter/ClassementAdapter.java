package mydevmind.com.mycommunity.fragment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.model.Statistic;

/**
 * Created by Mugiwara on 29/07/2014.
 */
public class ClassementAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Statistic> statisticArrayList;

    public  ClassementAdapter(Activity context, ArrayList<Statistic> statisticArrayList){
        this.context= context;
        this.statisticArrayList= statisticArrayList;
    }

    @Override
    public int getCount() {
        return statisticArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return statisticArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow= inflater.inflate(R.layout.list_classement_item, null);
        Statistic stat= (Statistic) statisticArrayList.get(position);

        TextView fieldPlayer= (TextView) viewRow.findViewById(R.id.textViewClassementItemPlayer);
        fieldPlayer.setText(stat.getPlayer().getName());
        TextView fieldPlay= (TextView) viewRow.findViewById(R.id.textViewClassementItemPlay);
        fieldPlay.setText(stat.getPlay()+"");
        TextView fieldWon= (TextView) viewRow.findViewById(R.id.textViewClassementItemWon);
        fieldWon.setText(stat.getWon()+"");
        TextView fieldDraw= (TextView) viewRow.findViewById(R.id.textViewClassementItemDraw);
        fieldDraw.setText(stat.getDraw()+"");
        TextView fieldLose= (TextView) viewRow.findViewById(R.id.textViewClassementItemLose);
        fieldLose.setText(stat.getLose()+"");
        TextView fieldPoints= (TextView) viewRow.findViewById(R.id.textViewClassementItemPoints);
        fieldPoints.setText(stat.getPoints()+"");

        return viewRow;
    }
}

package mydevmind.com.mycommunity.fragment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.model.Information;
import mydevmind.com.mycommunity.model.Match;

/**
 * Created by Joan on 24/07/2014.
 */
public class InformationAdapter extends BaseAdapter {

    private Activity context;
    private List<Information> informations;

    public InformationAdapter(Activity context, List<Information> informations){
        super();
        this.context= context;
        this.informations= informations;
    }

    @Override
    public int getCount() {
        return informations.size();
    }

    @Override
    public Object getItem(int i) {
        return informations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_informations_item, null);
        TextView txtTitle= (TextView) rowView.findViewById(R.id.textViewInfoTitle);
        txtTitle.setText(informations.get(i).getTitle());
        TextView txtContent= (TextView) rowView.findViewById(R.id.textViewInfoText);
        txtContent.setText(informations.get(i).getText());
        if(informations.get(i) instanceof Match){
            txtContent.setGravity(Gravity.CENTER_HORIZONTAL);
        }
        TextView txtDate= (TextView) rowView.findViewById(R.id.textViewInfoDate);
        txtDate.setText(informations.get(i).getDate().toLocaleString());
        return rowView;
    }
}

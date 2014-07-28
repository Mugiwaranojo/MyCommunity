package mydevmind.com.mycommunity.fragment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Mugiwara on 28/07/2014.
 */
public class PlayerAdapter extends BaseAdapter{

    private Activity context;
    private List<Player> players;

    public  PlayerAdapter(Activity context, ArrayList<Player> playerArrayList){
        this.context= context;
        this.players= playerArrayList;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.list_player_item, null);
        TextView textPlayer= (TextView) rowView.findViewById(R.id.textViewItemPlayer);
        textPlayer.setText(players.get(position).getName());
        return rowView;
    }
}

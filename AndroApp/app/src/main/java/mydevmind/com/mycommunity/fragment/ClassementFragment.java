package mydevmind.com.mycommunity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.fragment.Adapter.ClassementAdapter;
import mydevmind.com.mycommunity.model.Player;
import mydevmind.com.mycommunity.model.Statistic;

/**
 * Created by Mugiwara on 29/07/2014.
 */
public class ClassementFragment extends Fragment {

    private ArrayList<Statistic> statisticList;
    private ClassementAdapter adapter;
    private ListView listViewClassement;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classement, null);
        
        listViewClassement = (ListView) view.findViewById(R.id.listViewClassement);
        updateListView();
        return view;
    }

    public void updateListView(){
        statisticList = getStatistics();
        adapter= new ClassementAdapter(getActivity(), statisticList);
        listViewClassement.setAdapter(adapter);
        listViewClassement.invalidate();
    }

    public ArrayList<Statistic> getStatistics(){
        ArrayList<Statistic> statistics= new ArrayList<Statistic>();
        for(Player player: CommunityFragment.getCurrentCommunity().getPlayers()){
            statistics.add(Statistic.getPlayerStatistic(player, CommunityFragment.getCurrentCommunity()));
        }
        return statistics;
    }
}
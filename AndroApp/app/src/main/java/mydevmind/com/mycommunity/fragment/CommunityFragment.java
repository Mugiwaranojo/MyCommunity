package mydevmind.com.mycommunity.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.API.CommunityAPIManager;
import mydevmind.com.mycommunity.API.IAPIResultListener;
import mydevmind.com.mycommunity.MainActivity;
import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.fragment.Adapter.InformationAdapter;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Information;
import mydevmind.com.mycommunity.model.Match;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 23/07/2014.
 */
public class CommunityFragment extends Fragment{

    private ListView listViewInformations;
    private List<Information> informations;
    private InformationAdapter adapter;

    private CommunityAPIManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, null);
        Intent intentFromLogin= getActivity().getIntent();
        informations = new ArrayList<Information>();
        listViewInformations= (ListView) v.findViewById(R.id.listViewMainInformations);

        manager= CommunityAPIManager.getInstance(getActivity());
        manager.setCurentPlayer((Player) intentFromLogin.getSerializableExtra("player"));
        manager.setCurrentCommunity(manager.getCurentPlayer().getCommunities().get(0));
        MainActivity.setmTitle(manager.getCurrentCommunity().getName());
        updateInfos();
        return v;
    }

    public void updateListView(){
        informations= manager.getCurrentCommunity().getInformations();
        adapter= new InformationAdapter(getActivity(), informations);
        listViewInformations.setAdapter(adapter);
        listViewInformations.invalidate();
    }

    public void updateInfos(){
         NavigationDrawerFragment.getSpinner().show();
         manager.setInformationsListener(new IAPIResultListener<ArrayList<Information>>() {
             @Override
             public void onApiResultListener(ArrayList<Information> obj, ParseException e) {
                 updateListView();
                 NavigationDrawerFragment.getSpinner().dismiss();
                 updatePlayer();
             }
         });
        manager.fetchInformations();
    }

    public void updatePlayer(){
        NavigationDrawerFragment.getSpinner().show();
        manager.setPlayersListListener(new IAPIResultListener<ArrayList<Player>>() {
            @Override
            public void onApiResultListener(ArrayList<Player> obj, ParseException e) {
                NavigationDrawerFragment.getSpinner().dismiss();
            }
        });
        manager.fetchPlayers();
    }
}

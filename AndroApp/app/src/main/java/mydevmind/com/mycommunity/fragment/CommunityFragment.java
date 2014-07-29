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
import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.fragment.Adapter.InformationAdapter;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Information;
import mydevmind.com.mycommunity.model.Match;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 23/07/2014.
 */
public class CommunityFragment extends Fragment implements IAPIResultListener<ArrayList<Community>> {

    private ListView listViewInformations;
    private List<Information> informations;
    private InformationAdapter adapter;

    private CommunityAPIManager manager;

    private static Player currentUser;
    private static Community currentCommunity;

    public static Community getCurrentCommunity() {
        return currentCommunity;
    }

    public static Player getCurrentUser() {
        return currentUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, null);

        Intent intentFromLogin= getActivity().getIntent();
        currentUser = (Player) intentFromLogin.getSerializableExtra("player");

        informations = new ArrayList<Information>();
        listViewInformations= (ListView) v.findViewById(R.id.listViewMainInformations);

        manager= new CommunityAPIManager(getActivity());
        manager.setCommunityListListener(this);
        manager.fetchCommunities(currentUser);
        return v;
    }

    public void updateInfos(){
         NavigationDrawerFragment.getSpinner().show();
         manager.setPlayersListListener(new IAPIResultListener<ArrayList<Player>>() {
                    @Override
                    public void onApiResultListener(ArrayList<Player> obj, ParseException e) {
                        getCurrentCommunity().setPlayers(obj);
                        manager.fetchInformations(currentCommunity);
                    }
                });
         manager.setInformationsListener(new IAPIResultListener<ArrayList<Information>>() {
            @Override
            public void onApiResultListener(ArrayList<Information> obj, ParseException e) {
                fetchPlayersInMatches();
                informations= currentCommunity.getInformations();
                adapter= new InformationAdapter(getActivity(), informations);
                listViewInformations.setAdapter(adapter);
                listViewInformations.invalidate();
                NavigationDrawerFragment.getSpinner().dismiss();
             }
        });
         manager.fetchPlayers(currentCommunity);
    }

    private void fetchPlayersInMatches(){
        for(Match m: currentCommunity.getMatches()){
            for(Player p: currentCommunity.getPlayers()){
                if(m.getPlayerFrom().getObjectId().equals(p.getObjectId())){
                    m.setPlayerFrom(p);
                }else if(m.getPlayerTo().getObjectId().equals(p.getObjectId())){
                    m.setPlayerTo(p);
                }
            }
        }
    }

    @Override
    public void onApiResultListener(ArrayList<Community> obj, ParseException e) {
        currentCommunity= obj.get(0);
        updateInfos();
    }
}

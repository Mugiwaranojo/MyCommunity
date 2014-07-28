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
public class CommunityFragment extends Fragment {

    private ListView listViewInformations;

    private List<Information> informations;
    private InformationAdapter adapter;

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
        currentCommunity = currentUser.getCommunities().get(0);
        informations = new ArrayList<Information>();
        listViewInformations= (ListView) v.findViewById(R.id.listViewMainInformations);
        updateList();
        return v;
    }

    public void updateList(){
        NavigationDrawerFragment.getSpinner().show();
        CommunityAPIManager communityAPIManager = CommunityAPIManager.getInstance(getActivity());
        communityAPIManager.setPlayersListListener(new IAPIResultListener<ArrayList<Player>>() {
            @Override
            public void onApiResultListener(ArrayList<Player> obj, ParseException e) {
                getCurrentCommunity().setPlayers(obj);
                CommunityAPIManager.getInstance(getActivity()).fetchInformations(currentCommunity);
            }
        });
        communityAPIManager.setInformationsListener(new IAPIResultListener<ArrayList<Information>>() {
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
        communityAPIManager.fetchPlayers(currentCommunity);
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
}

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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mydevmind.com.mycommunity.API.DAO.CommunityDAO;
import mydevmind.com.mycommunity.API.DAO.PlayerDAO;
import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Information;
import mydevmind.com.mycommunity.model.Match;
import mydevmind.com.mycommunity.model.Notification;
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
        try {
            currentUser = PlayerDAO.getInstance(getActivity()).find(intentFromLogin.getStringExtra("userId"));
            currentCommunity = currentUser.getCommunities().get(0);
            currentCommunity= CommunityDAO.getInstance(getActivity()).find(currentCommunity.getObjectId());
            informations = new ArrayList<Information>();
            for(Notification n:currentCommunity.getNotifications()){
                informations.add(n);
            }
            for(Match m:currentCommunity.getMatches()){
                informations.add(m);
            }
            Collections.sort(informations);

            listViewInformations= (ListView) v.findViewById(R.id.listViewMainInformations);
            adapter= new InformationAdapter(getActivity(), informations);
            listViewInformations.setAdapter(adapter);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return v;
    }
}

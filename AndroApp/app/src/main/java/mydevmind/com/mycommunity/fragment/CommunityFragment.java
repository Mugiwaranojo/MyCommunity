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

import mydevmind.com.mycommunity.API.DAO.PlayerDAO;
import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.model.Information;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 23/07/2014.
 */
public class CommunityFragment extends Fragment {

    private ListView listViewInformations;

    private ArrayList<Information> informations;

    public static Player currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, null);

        Intent intentFromLogin= getActivity().getIntent();
        try {
            currentUser = PlayerDAO.getInstance(getActivity()).find(intentFromLogin.getStringExtra("userId"));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        return v;
    }
}

package mydevmind.com.mycommunity.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;

import mydevmind.com.mycommunity.API.DAO.CommunityDAO;
import mydevmind.com.mycommunity.API.DAO.PlayerDAO;
import mydevmind.com.mycommunity.LoginActivity;
import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 21/07/2014.
 */
public class InscriptionFragment extends Fragment {

    private Button returnButton;
    private Button inscriptionButton;
    private IFragmentActionListener actionListener;

    private EditText userName, userPassword, userConfirm;
    private EditText communityName, communityPassword, communityConfirm;

    private CommunityDAO communityDAO;

    public void setActionListener(IFragmentActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inscription, null);

        userName = (EditText) v.findViewById(R.id.editTextUserName);
        userPassword = (EditText) v.findViewById(R.id.editTextUserPassword);
        userConfirm = (EditText) v.findViewById(R.id.editTextUserConfirm);
        communityName= (EditText) v.findViewById(R.id.editCommunityName);
        communityPassword= (EditText) v.findViewById(R.id.editTextCommunityPassword);
        communityConfirm= (EditText) v.findViewById(R.id.editTextCommunityConfirm);

        returnButton = (Button) v.findViewById(R.id.buttonReturnLogin);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionListener.onFragmentAction(LoginActivity.LOGIN_ACTION_RETURN, null);
            }
        });

        inscriptionButton = (Button) v.findViewById(R.id.buttonInscription);
        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validForm()){
                    Player player= new Player(userName.getText().toString(), userPassword.getText().toString());
                    Community community= new Community(communityName.getText().toString(), communityPassword.getText().toString());

                    try {
                        CommunityDAO.getInstance(getActivity()).create(community);
                        PlayerDAO.getInstance(getActivity()).create(player);

                        ParseObject retrievePlayer = PlayerDAO.getInstance(getActivity()).find(userName.getText().toString(), userName.getText().toString());
                        ParseObject retrieveCommunity = CommunityDAO.getInstance(getActivity()).find(communityName.getText().toString(), communityPassword.getText().toString());

                        Log.d("player", retrievePlayer.getObjectId());
                        retrievePlayer.put("communities", retrieveCommunity);
                        retrievePlayer.save();
                        retrieveCommunity.put("players", retrievePlayer);
                        retrieveCommunity.save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return v;
    }

    public boolean validForm(){
        boolean isValid= true;
        if(userName.getText().toString().equals("")){
            userName.setTextColor(Color.RED);
            isValid= false;
        }else{
            userName.setTextColor(Color.GREEN);
        }
        if(userPassword.getText().toString().equals("")){
            userPassword.setTextColor(Color.RED);
            isValid= false;
        }else{
            userPassword.setTextColor(Color.GREEN);
        }
        if(communityName.getText().toString().equals("")){
            communityName.setTextColor(Color.RED);
            isValid= false;
        }else{
            communityName.setTextColor(Color.GREEN);
        }
        if(communityPassword.getText().toString().equals("")){
            communityPassword.setTextColor(Color.RED);
            isValid= false;
        }else{
            communityPassword.setTextColor(Color.GREEN);
        }
        if(!userPassword.getText().toString().equals(userConfirm.getText().toString())){
            userPassword.setTextColor(Color.RED);
            userConfirm.setTextColor(Color.RED);
            isValid= false;
        }else{
            userPassword.setTextColor(Color.GREEN);
            userConfirm.setTextColor(Color.GREEN);
        }
        if(!communityPassword.getText().toString().equals(communityConfirm.getText().toString())){
            communityPassword.setTextColor(Color.RED);
            communityConfirm.setTextColor(Color.RED);
            isValid= false;
        }else{
            communityPassword.setTextColor(Color.GREEN);
            communityConfirm.setTextColor(Color.GREEN);
        }
        return isValid;
    }
}

package mydevmind.com.mycommunity.fragment.Login;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
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

import org.json.JSONException;

import mydevmind.com.mycommunity.API.CommunityAPIManager;
import mydevmind.com.mycommunity.API.DAO.CommunityDAO;
import mydevmind.com.mycommunity.API.DAO.InscriptionDAO;
import mydevmind.com.mycommunity.API.DAO.PlayerDAO;
import mydevmind.com.mycommunity.API.IAPIResultListener;
import mydevmind.com.mycommunity.LoginActivity;
import mydevmind.com.mycommunity.MainActivity;
import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.fragment.IFragmentActionListener;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Inscription;
import mydevmind.com.mycommunity.model.Notification;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 21/07/2014.
 */
public class InscriptionFragment extends Fragment {

    private Button returnButton;
    private Button inscriptionButton;
    private IFragmentActionListener actionListener;

    private ProgressDialog spinner;

    private EditText userName, userPassword, userConfirm;
    private EditText communityName, communityPassword, communityConfirm;

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


        spinner = new ProgressDialog(getActivity());
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle("Inscription ...");
        spinner.setMessage("Patientez, ceci peut prendre quelques secondes");
        spinner.setCancelable(false);

        inscriptionButton = (Button) v.findViewById(R.id.buttonInscription);
        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(validForm()){
                    spinner.show();
                    final Player player= new Player(userName.getText().toString(), userPassword.getText().toString());
                     Community community= new Community(communityName.getText().toString(), communityPassword.getText().toString());
                     final CommunityAPIManager apiManager= new CommunityAPIManager(getActivity());
                     apiManager.setPlayerListener(new IAPIResultListener<Player>() {
                        @Override
                        public void onApiResultListener(Player obj, ParseException e) {
                            spinner.dismiss();
                            Intent connectionIntent = new Intent(getActivity(), MainActivity.class);
                            connectionIntent.putExtra("player", obj);
                            startActivity(connectionIntent);
                        }
                    });
                     apiManager.setNotificationListener(new IAPIResultListener<Notification>() {
                        @Override
                        public void onApiResultListener(Notification obj, ParseException e) {
                            apiManager.connection(player.getName(), player.getPassword());
                        }
                    });
                    apiManager.inscriptionPlayerAndCommunity(player, community);
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

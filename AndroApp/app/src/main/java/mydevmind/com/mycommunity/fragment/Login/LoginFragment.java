package mydevmind.com.mycommunity.fragment.Login;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

import mydevmind.com.mycommunity.API.CommunityAPIManager;
import mydevmind.com.mycommunity.API.IAPIResultListener;
import mydevmind.com.mycommunity.LoginActivity;
import mydevmind.com.mycommunity.MainActivity;
import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.fragment.IFragmentActionListener;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 16/07/2014.
 */
public class LoginFragment extends Fragment implements IAPIResultListener<Player> {

    private EditText loginField, passwordField;
    private Button loginButton;
    private Button inscriptionButton;

    private CommunityAPIManager apiManager;
    private IFragmentActionListener actionListener;

    private ProgressDialog spinner;

    public void setActionListener(IFragmentActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_login, null);

        loginField= (EditText) v.findViewById(R.id.editTextLogin);
        passwordField= (EditText) v.findViewById(R.id.editTextPassword);
        loginButton= (Button) v.findViewById(R.id.buttonLogin);
        inscriptionButton= (Button) v.findViewById(R.id.buttonInscription);

        spinner = new ProgressDialog(getActivity());
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle("Connection ...");
        spinner.setMessage("Patientez, ceci peut prendre quelques secondes");
        spinner.setCancelable(false);

        apiManager = new CommunityAPIManager(getActivity());
        apiManager.setPlayerListener(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.show();
                apiManager.connection(loginField.getText().toString(), passwordField.getText().toString());
            }
        });

        inscriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionListener.onFragmentAction(LoginActivity.LOGIN_ACTION_INSCRIPTION, null);
            }
        });
        return v;
    }

    @Override
    public void onApiResultListener(Player player, ParseException e) {
        if(player!=null){
            Toast.makeText(getActivity().getApplicationContext(), "Connection OK!!!", Toast.LENGTH_SHORT).show();
            Intent connectionIntent= new Intent(getActivity(), MainActivity.class);
            connectionIntent.putExtra("player", player);
            startActivity(connectionIntent);
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "Incorrect login/password!!!", Toast.LENGTH_SHORT).show();
        }
        spinner.dismiss();
    }
}

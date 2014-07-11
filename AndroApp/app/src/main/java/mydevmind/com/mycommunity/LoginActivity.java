package mydevmind.com.mycommunity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Joan on 11/07/2014.
 */
public class LoginActivity extends Activity {

    private final String APP_ID="4UNxW53O9e42UjNxLaGma5foAtZQpE22H2IwZ9y3";
    private final String CLIENT_KEY="zqk5C0BKHuWmSaIrSfuWFVyH4MRlAd7g3iY9uUCg";

    private EditText loginField, passwordField;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login);
        loginField= (EditText) findViewById(R.id.editTextLogin);
        passwordField= (EditText) findViewById(R.id.editTextPassword);
        loginButton= (Button) findViewById(R.id.buttonLogin);

        Parse.initialize(this, APP_ID, CLIENT_KEY);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
                query.whereEqualTo("name", loginField.getText().toString());
                query.whereEqualTo("password", passwordField.getText().toString());

                Log.d("connection to parse.com :", "name :"+loginField.getText().toString()+
                                                   " password :"+passwordField.getText().toString());

                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> userList, ParseException e) {
                        if(e==null){
                            Log.d("login", "Retrieved  "+ userList.size()+ " user");
                            Toast.makeText(getApplicationContext(), "Connection OK!!!", Toast.LENGTH_SHORT).show();
                        }else{
                           Log.d("login", "Error: "+e.getMessage());
                        }
                    }
                });
            }
        });

    }
}

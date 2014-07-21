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

import mydevmind.com.mycommunity.fragment.LoginFragment;
import mydevmind.com.mycommunity.model.IFragmentActionListener;

/**
 * Created by Joan on 11/07/2014.
 */
public class LoginActivity extends Activity implements IFragmentActionListener {


    public static final Integer LOGIN_ACTION_INSCRIPTION= 1705;
    private LoginFragment login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = new LoginFragment();
        login.setActionListener(this);
        getFragmentManager().beginTransaction()
                .add(R.id.container, login)
                .commit();
    }

    @Override
    public void onFragmentAction(Integer action) {
        if(action.equals(LOGIN_ACTION_INSCRIPTION)){
            Toast.makeText(getApplicationContext(), "inscription", Toast.LENGTH_SHORT).show();
        }
    }
}

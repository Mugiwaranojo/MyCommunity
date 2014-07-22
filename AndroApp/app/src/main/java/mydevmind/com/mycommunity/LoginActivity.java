package mydevmind.com.mycommunity;

import android.app.Activity;
import android.os.Bundle;

import mydevmind.com.mycommunity.fragment.InscriptionFragment;
import mydevmind.com.mycommunity.fragment.LoginFragment;
import mydevmind.com.mycommunity.fragment.IFragmentActionListener;

/**
 * Created by Joan on 11/07/2014.
 */
public class LoginActivity extends Activity implements IFragmentActionListener {


    public static final Integer LOGIN_ACTION_INSCRIPTION= 1705;
    public static final Integer LOGIN_ACTION_RETURN= 1706;
    public static final Integer LOGIN_ACTION_CONNECT= 1707;

    private LoginFragment login;
    private InscriptionFragment inscription;
    private boolean isPageLogin= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(login==null) {
            login = new LoginFragment();
            login.setActionListener(this);

            inscription = new InscriptionFragment();
            inscription.setActionListener(this);


            getFragmentManager().beginTransaction()
                    .add(R.id.container, login)
                    .add(R.id.container, inscription)
                    .hide(inscription)
                    .commit();
        }
    }

    @Override
    public void onFragmentAction(Integer action, Object obj) {
        if(action.equals(LOGIN_ACTION_INSCRIPTION)){
            isPageLogin=false;
            getFragmentManager().beginTransaction()
                 .hide(login)
                 .show(inscription)
                 .commit();
        }else if(action.equals(LOGIN_ACTION_RETURN)){
            this.onBackPressed();
        }
    }


    @Override
    public void onBackPressed() {
        if(isPageLogin){
            super.onBackPressed();
        }else{
            getFragmentManager().beginTransaction()
                    .hide(inscription)
                    .show(login)
                    .commit();
            isPageLogin=true;
        }
    }
}

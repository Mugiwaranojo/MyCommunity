package mydevmind.com.mycommunity.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import mydevmind.com.mycommunity.LoginActivity;
import mydevmind.com.mycommunity.R;

/**
 * Created by Joan on 21/07/2014.
 */
public class InscriptionFragment extends Fragment {

    private Button returnButton;
    private IFragmentActionListener actionListener;

    public void setActionListener(IFragmentActionListener actionListener) {
        this.actionListener = actionListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inscription, null);

        returnButton = (Button) v.findViewById(R.id.buttonReturnLogin);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionListener.onFragmentAction(LoginActivity.LOGIN_ACTION_RETURN);
            }
        });
        return v;
    }
}

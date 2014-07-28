package mydevmind.com.mycommunity.fragment.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;

import java.util.ArrayList;

import mydevmind.com.mycommunity.API.CommunityAPIManager;
import mydevmind.com.mycommunity.API.IAPIResultListener;
import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.fragment.Adapter.PlayerAdapter;
import mydevmind.com.mycommunity.fragment.CommunityFragment;
import mydevmind.com.mycommunity.fragment.NavigationDrawerFragment;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Match;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Mugiwara on 28/07/2014.
 */
public class MatchDialogFragment extends DialogFragment{

    private Spinner selectPlayerFrom;
    private Spinner selectPlayerTo;
    private PlayerAdapter adapter;
    private EditText editScorePlayerFrom;
    private EditText editScorePlayerTo;
    private EditText editComment;
    private Button buttonSubmitMatch;
    private Button buttonCancelAddMatch;

    private IOnSubmitMatchListener listener;


    public void setListener(IOnSubmitMatchListener listener) {
        this.listener = listener;
    }

    public interface IOnSubmitMatchListener{
        void onSubmitMatchListener(Match match);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setTitle(getString(R.string.field_add_match));
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_add_match);
        dialog.show();

        selectPlayerFrom = (Spinner) dialog.findViewById(R.id.spinnerSelectPlayerFrom);
        selectPlayerTo = (Spinner) dialog.findViewById(R.id.spinnerSelectPlayerTo);
        adapter = new PlayerAdapter(getActivity(), CommunityFragment.getCurrentCommunity().getPlayers());
        selectPlayerFrom.setAdapter(adapter);
        selectPlayerTo.setAdapter(adapter);

        editScorePlayerFrom= (EditText) dialog.findViewById(R.id.editTextPlayerFromScore);
        editScorePlayerTo= (EditText) dialog.findViewById(R.id.editTextPlayerToScore);
        editComment= (EditText) dialog.findViewById(R.id.editTextMatchComment);

        buttonSubmitMatch= (Button) dialog.findViewById(R.id.buttonAddMatch);
        buttonSubmitMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validForm()) {
                    Match match = new Match();
                    match.setPlayerFrom((Player) selectPlayerFrom.getSelectedItem());
                    match.setPlayerTo((Player) selectPlayerTo.getSelectedItem());
                    match.setScoreFrom(Integer.parseInt(editScorePlayerFrom.getText().toString()));
                    match.setScoreTo(Integer.parseInt(editScorePlayerTo.getText().toString()));
                    match.setComment(editComment.getText().toString());
                    match.setCommunity(CommunityFragment.getCurrentCommunity());
                    dismiss();
                    listener.onSubmitMatchListener(match);
                }
            }
        });
        buttonCancelAddMatch= (Button) dialog.findViewById(R.id.buttonCancelMatch);
        buttonCancelAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return dialog;
    }

    private boolean validForm(){
        boolean valid= true;
        Player from = (Player) selectPlayerFrom.getSelectedItem();
        Player to= (Player) selectPlayerTo.getSelectedItem();
        if(from.getObjectId().equals(to.getObjectId())){
            valid=false;
        }
        if(editScorePlayerTo.getText().equals("")||editScorePlayerFrom.getText().equals("")){
            valid=false;
        }
        return valid;
    }
}
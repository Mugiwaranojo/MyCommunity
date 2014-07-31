package mydevmind.com.mycommunity.fragment.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import mydevmind.com.mycommunity.R;
import mydevmind.com.mycommunity.fragment.NavigationDrawerFragment;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 25/07/2014.
 */
public class PlayerDialogFragment extends DialogFragment {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private EditText editTextConfirm;
    private Button  buttonSubmitPlayer;
    private Button  buttonCancelPlayer;
    private  IOnSubmitPlayerListener listener;

    public interface IOnSubmitPlayerListener{
        void onSubmitPlayerListener(Player player);
    }

    public void setListener(IOnSubmitPlayerListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(getString(R.string.field_add_player));
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_add_player);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.background_blue);
        dialog.show();

        editTextLogin = (EditText) dialog.findViewById(R.id.editTextPName);
        editTextPassword = (EditText) dialog.findViewById(R.id.editTextPPassword);
        editTextConfirm = (EditText) dialog.findViewById(R.id.editTextPConfirm);

        buttonSubmitPlayer = (Button) dialog.findViewById(R.id.buttonSavePlayer);
        buttonSubmitPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validForm()){
                    Player player= new Player(editTextLogin.getText().toString(),
                                              editTextPassword.getText().toString());
                    dismiss();
                    listener.onSubmitPlayerListener(player);
                }
            }
        });

        buttonCancelPlayer = (Button) dialog.findViewById(R.id.buttonCancelPlayer);
        buttonCancelPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return dialog;
    }

    private boolean validForm(){
        boolean isValid= true;
        if(editTextLogin.getText().toString().equals("")){
            editTextLogin.setTextColor(Color.RED);
            isValid= false;
        }else{
            editTextLogin.setTextColor(Color.GREEN);
        }
        if(editTextPassword.getText().toString().equals("")){
            editTextPassword.setTextColor(Color.RED);
            isValid= false;
        }else{
            editTextPassword.setTextColor(Color.GREEN);
        }
        if(!editTextPassword.getText().toString().equals(editTextConfirm.getText().toString())){
            editTextPassword.setTextColor(Color.RED);
            editTextConfirm.setTextColor(Color.RED);
            isValid= false;
        }else{
            editTextPassword.setTextColor(Color.GREEN);
            editTextConfirm.setTextColor(Color.GREEN);
        }
        return isValid;
    }

}

package mydevmind.com.mycommunity.API;

import android.content.Context;
import android.text.StaticLayout;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Joan on 21/07/2014.
 */
public class CommunityAPIManager {

    private static final String APP_ID="4UNxW53O9e42UjNxLaGma5foAtZQpE22H2IwZ9y3";
    private static final String CLIENT_KEY="zqk5C0BKHuWmSaIrSfuWFVyH4MRlAd7g3iY9uUCg";

    private OnApiResultListener listener;

    private static CommunityAPIManager instance;

    public static CommunityAPIManager getInstance(Context context){
        if(instance==null){
            Parse.initialize(context, APP_ID, CLIENT_KEY);
            instance = new CommunityAPIManager();
        }
        return instance;
    }

    public void connection(String login, String password){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("name", login);
        query.whereEqualTo("password", password);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> userList, ParseException e) {
                if(e==null){
                   listener.onApiResult(userList, null);
                }else{
                    listener.onApiResult(null, e);
                }
            }
        });
    }

    public void setListener(OnApiResultListener listener) {
        this.listener = listener;
    }
}

package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;

import org.json.JSONException;

import mydevmind.com.mycommunity.API.CommunityAPIManager;

/**
 * Created by Joan on 21/07/2014.
 */
public abstract class DAO<T> {

    public CommunityAPIManager apiManager;


    private static Context context;


    public DAO(Context context)
    {
        this.context= context;
        apiManager= CommunityAPIManager.getInstance(this.context);
    }

    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public  abstract ParseObject create(T obj) throws ParseException;

    public  abstract  boolean delete(T obj) throws ParseException;

    public  abstract  boolean update(T obj) throws ParseException;

    public  abstract  T find(String objId) throws ParseException;
}

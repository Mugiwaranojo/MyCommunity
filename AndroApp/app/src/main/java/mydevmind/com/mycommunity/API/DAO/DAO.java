package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.Parse;

import mydevmind.com.mycommunity.API.IAPIResultListener;
import mydevmind.com.mycommunity.model.Inscription;

/**
 * Created by Joan on 21/07/2014.
 */
public abstract class DAO<T> {

    private static final String APP_ID="4UNxW53O9e42UjNxLaGma5foAtZQpE22H2IwZ9y3";
    private static final String CLIENT_KEY="zqk5C0BKHuWmSaIrSfuWFVyH4MRlAd7g3iY9uUCg";

    private static Context context;

    public DAO(Context context){
        Parse.initialize(context, APP_ID, CLIENT_KEY);
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DAO.context = context;
    }

    public  abstract void create(final T obj, final IAPIResultListener<T> listener);

    public  abstract  void delete(final T obj,final IAPIResultListener<T> listener);

    public  abstract  void update(final T obj,final IAPIResultListener<T> listener);

    public  abstract  void find(String objId,final IAPIResultListener<T> listener);
}

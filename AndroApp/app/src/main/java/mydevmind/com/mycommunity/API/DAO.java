package mydevmind.com.mycommunity.API;

import android.content.Context;

/**
 * Created by Joan on 21/07/2014.
 */
public abstract class DAO<T> {

    public CommunityAPIManager apiManager;

    public DAO(Context context){
        apiManager= CommunityAPIManager.getInstance(context);
    }

    public  abstract  boolean create(T obj);

    public  abstract  boolean delete(T obj);

    public  abstract  boolean update(T obj);

    public  abstract  boolean find(T obj);
}

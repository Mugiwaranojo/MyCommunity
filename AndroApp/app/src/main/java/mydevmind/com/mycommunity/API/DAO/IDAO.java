package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.Parse;

import mydevmind.com.mycommunity.API.IAPIResultListener;
import mydevmind.com.mycommunity.model.Inscription;

/**
 * Created by Joan on 21/07/2014.
 */
public interface IDAO<T> {

    public   void create(final T obj, final IAPIResultListener<T> listener);

    public   void delete(final T obj,final IAPIResultListener<T> listener);

    public   void update(final T obj,final IAPIResultListener<T> listener);

    public   void find(String objId,final IAPIResultListener<T> listener);
}

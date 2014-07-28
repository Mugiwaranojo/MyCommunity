package mydevmind.com.mycommunity.API;

import java.text.ParseException;

/**
 * Created by Mugiwara on 26/07/2014.
 */
public interface  IAPIResultListener<T>{

    public void onApiResultListener(T obj, com.parse.ParseException e);
}

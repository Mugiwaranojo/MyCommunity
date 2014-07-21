package mydevmind.com.mycommunity.API;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Joan on 21/07/2014.
 */
public interface OnApiResultListener {

    public void onApiResult(List<ParseObject> objects, ParseException e);
}

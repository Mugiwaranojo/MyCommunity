package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.API.IAPIResultListener;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Inscription;
import mydevmind.com.mycommunity.model.Match;
import mydevmind.com.mycommunity.model.Notification;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 22/07/2014.
 */
public class CommunityDAO extends DAO<Community> {

    private static CommunityDAO instance;
    private CommunityDAO(Context context) {
        super(context);
    }

    public static CommunityDAO getInstance(Context context) {
        if(instance==null){
              instance = new CommunityDAO(context);
        }
        return  instance;
    }

    @Override
    public void create(Community obj,final IAPIResultListener<Community> listener){
        final ParseObject community=  new ParseObject("Community");
        community.put("name", obj.getName());
        community.put("password", obj.getPassword());
        community.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                listener.onApiResultListener(parseObjectToCommunity(community), e);
            }
        });
    }

    @Override
    public void delete(Community obj,final IAPIResultListener<Community> listener){
       ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    final ParseObject community= parseObjects.get(0);
                    community.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            listener.onApiResultListener(parseObjectToCommunity(community), e);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void update(final Community obj, final IAPIResultListener<Community> listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    final ParseObject community= parseObjects.get(0);
                    community.put("name", obj.getName());
                    community.put("password", obj.getPassword());
                    community.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            listener.onApiResultListener(parseObjectToCommunity(community), e);
                        }
                    });
                }
            }
        });

    }

    @Override
    public void find(String objId, final IAPIResultListener<Community> listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("objectId", objId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    listener.onApiResultListener(parseObjectToCommunity(parseObjects.get(0)), e);
                }else {
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    public static Community parseObjectToCommunity(ParseObject obj){
        Community community= new Community();
        community.setObjectId(obj.getObjectId());
        try {
            community.setName(obj.getString("name"));
            community.setPassword(obj.getString("password"));
            community.setCreatedAt(obj.getCreatedAt());
            community.setUpdatedAt(obj.getUpdatedAt());
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        return community;
    }
}

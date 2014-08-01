package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.API.IAPIResultListener;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Notification;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 23/07/2014.
 */
public class NotificationDAO implements IDAO<Notification> {

    private static NotificationDAO instance;

    private NotificationDAO() {

    }

    public static NotificationDAO getInstance(){
        if(instance==null){
            instance = new NotificationDAO();
        }
        return  instance;
    }

    @Override
    public void create(Notification obj, final IAPIResultListener<Notification> listener){
        ParseObject community= ParseObject.createWithoutData("Community", obj.getCommunity().getObjectId());
        final ParseObject notification= new ParseObject("Notification");
        notification.put("community", community);
        if(obj.getWriter()!=null) {
            ParseObject player = ParseObject.createWithoutData("Player", obj.getWriter().getObjectId());
            notification.put("writer", player);
        }
        notification.put("title", obj.getTitle());
        notification.put("text", obj.getText());
        notification.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                try {
                    listener.onApiResultListener(parseObjectToNotification(notification), e);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void delete(Notification obj, final IAPIResultListener<Notification> listener){
    }

    @Override
    public void update(Notification obj, final IAPIResultListener<Notification> listener){
    }

    @Override
    public void find(String objId, final IAPIResultListener<Notification> listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.whereEqualTo("objectId", objId);
        query.include("Player");
        query.include("Community");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    try {
                        listener.onApiResultListener(parseObjectToNotification(parseObjects.get(0)), e);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }else {
                   listener.onApiResultListener(null, e);
                }
            }
        });

    }

    public void findByCommunity(Community community, final IAPIResultListener<ArrayList<Notification>> listener){
        final ArrayList<Notification> notifications = new ArrayList<Notification>();
        ParseObject fetchedCommunity = ParseObject.createWithoutData("Community", community.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.whereEqualTo("community", fetchedCommunity);
        query.include("Player");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                try {
                    for(ParseObject obj: parseObjects){
                        notifications.add(parseObjectToNotification(obj));
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                listener.onApiResultListener(notifications, e);
            }
        });

    }

    public static Notification parseObjectToNotification(ParseObject obj) throws ParseException {
        Notification notification= new Notification();
        notification.setObjectId(obj.getObjectId());
        notification.setTitle(obj.getString("title"));
        notification.setText(obj.getString("text"));
        notification.setCreatedAt(obj.getCreatedAt());
        notification.setUpdatedAt(obj.getUpdatedAt());
        notification.setCommunity(new Community(obj.getParseObject("community").getObjectId()));
        if(obj.getParseObject("writer")!=null){
            notification.setWriter(PlayerDAO.parseObjectToPlayer(obj.getParseObject("writer").fetchIfNeeded()));
        }
        return notification;
    }
}

package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Inscription;
import mydevmind.com.mycommunity.model.Notification;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 23/07/2014.
 */
public class NotificationDAO extends DAO<Notification> {

    private static NotificationDAO instance;

    private NotificationDAO(Context context) {
        super(context);
    }

    public static NotificationDAO getInstance(Context context){
        if(instance==null){
            instance = new NotificationDAO(context);
        }
        return  instance;
    }

    @Override
    public ParseObject create(Notification obj) throws ParseException {
        ParseObject community= ParseObject.createWithoutData("Community", obj.getCommunity().getObjectId());
        ParseObject notification= new ParseObject("Notification");
        notification.put("community", community);
        if(obj.getWriter()!=null) {
            ParseObject player = ParseObject.createWithoutData("Player", obj.getWriter().getObjectId());
            notification.put("writer", player);
        }
        notification.put("title", obj.getTitle());
        notification.put("text", obj.getText());
        notification.save();
        notification.refresh();
        return notification;
    }

    @Override
    public boolean delete(Notification obj) throws ParseException {
        return false;
    }

    @Override
    public boolean update(Notification obj) throws ParseException {
        return false;
    }

    @Override
    public Notification find(String objId) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.whereEqualTo("objectId", objId);
        query.include("Player");
        query.include("Community");
        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject obj= result.get(0);
            Notification notification= parseObjectToNotification(obj);
            return notification;
        }
        return null;
    }

    public ArrayList<Notification> findByCommunity(Community community) throws ParseException {
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        ParseObject fetchedCommunity = ParseObject.createWithoutData("Community", community.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
        query.whereEqualTo("community", fetchedCommunity);
        query.include("Player");
        query.include("Community");
        List<ParseObject> result= query.find();
        for(ParseObject obj: result){
            notifications.add(parseObjectToNotification(obj));
        }
        return notifications;
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
            notification.setWriter(new Player(obj.getParseObject("writer").getObjectId()));
        }
        return notification;
    }
}

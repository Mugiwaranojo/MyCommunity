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
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 21/07/2014.
 */
public class PlayerDAO extends DAO<Player> {

    public static PlayerDAO instance;

    private PlayerDAO(Context context) {
        super(context);
    }

    public  static PlayerDAO getInstance(Context context){
        if(instance==null){
            instance= new PlayerDAO(context);
        }
        return instance;
    }

    @Override
    public void create(Player obj, final IAPIResultListener<Player> listener){
        final ParseObject player=  new ParseObject("Player");
        player.put("name", obj.getName());
        player.put("password", obj.getPassword());
        player.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                listener.onApiResultListener(parseObjectToPlayer(player), e);
            }
        });
    }

    @Override
    public void delete(Player obj, final  IAPIResultListener<Player> listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    final ParseObject player= parseObjects.get(0);
                    player.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            listener.onApiResultListener(parseObjectToPlayer(player), e);
                        }
                    });
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });

    }

    @Override
    public void update(final Player obj, final IAPIResultListener<Player> listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    final ParseObject player= parseObjects.get(0);
                    player.put("name", obj.getName());
                    player.put("password", obj.getPassword());
                    player.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            listener.onApiResultListener(parseObjectToPlayer(player), e);
                        }
                    });
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    @Override
    public void find(String objId, final IAPIResultListener<Player> listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("objectId", objId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    listener.onApiResultListener(parseObjectToPlayer(parseObjects.get(0)), e);
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    public Player find(String objId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("objectId", objId);
        try {
            List<ParseObject> parseObjects = query.find();
            if(parseObjects.size()==1){
               return  parseObjectToPlayer(parseObjects.get(0));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void findByUserPassword(String login, String password, final IAPIResultListener<Player> listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("name", login);
        query.whereEqualTo("password", password);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> userList, ParseException e) {
                if(e==null){
                    if(userList.size()==1){
                        listener.onApiResultListener(parseObjectToPlayer(userList.get(0)), null);
                    }else {
                        listener.onApiResultListener(null, null);
                    }
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    public static Player parseObjectToPlayer(ParseObject obj){
        Player player= new Player();
        player.setObjectId(obj.getObjectId());
        player.setName(obj.getString("name"));
        player.setPassword(obj.getString("password"));
        player.setCreatedAt(obj.getCreatedAt());
        player.setUpdatedAt(obj.getUpdatedAt());
        return player;
    }
}

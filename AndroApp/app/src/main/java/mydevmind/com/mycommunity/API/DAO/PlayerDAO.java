package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.API.OnApiResultListener;
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
    public ParseObject create(Player obj) throws ParseException {
        ParseObject player=  new ParseObject("Player");
        player.put("name", obj.getName());
        player.put("password", obj.getPassword());
        player.save();
        player.refresh();
        return player;
    }

    @Override
    public boolean delete(Player obj) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("objectId", obj.getObjectId());

        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject player= result.get(0);
            player.delete();
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Player obj) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("objectId", obj.getObjectId());

        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject player= result.get(0);
            player.put("name", obj.getName());
            player.put("password", obj.getPassword());
            return true;
        }
        return false;
    }

    @Override
    public Player find(String objId) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("objectId", objId);
        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject obj= result.get(0);
            Player player= parseObjectToPlayer(obj);
            return player;
        }
        return null;
    }

    public void findByUserPassword(String login, String password, final OnApiResultListener listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("name", login);
        query.whereEqualTo("password", password);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> userList, ParseException e) {
                if(e==null){
                    listener.onApiResult(userList, null);
                }else{
                    listener.onApiResult(null, e);
                }
            }
        });
    }

    public static Player parseObjectToPlayer(ParseObject obj) throws ParseException {
        Player player= new Player();
        player.setObjectId(obj.getObjectId());
        player.setName(obj.getString("name"));
        player.setPassword(obj.getString("password"));
        player.setCreatedAt(obj.getCreatedAt());
        player.setUpdatedAt(obj.getUpdatedAt());
        ArrayList<Inscription> inscriptions= InscriptionDAO.getInstance(getContext()).findByUser(player);
        ArrayList<Community> communities= new ArrayList<Community>();
        for(Inscription inscription: inscriptions){
            communities.add(inscription.getCommunity());
        }
        player.setCommunities(communities);
        return player;
    }
}

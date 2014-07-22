package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 21/07/2014.
 */
public class PlayerDAO extends DAO<Player> {

    public static PlayerDAO instance;
    private Context context;

    private PlayerDAO(Context context) {
        super(context);
        this.context = context;
    }

    public  static PlayerDAO getInstance(Context context){
        if(instance==null){
            instance= new PlayerDAO(context);
        }
        return instance;
    }

    @Override
    public boolean create(Player obj) throws ParseException {
        ParseObject player=  new ParseObject("Player");
        player.put("name", obj.getName());
        player.put("password", obj.getPassword());
        player.save();
        return true;
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
    public Player find(String objId) throws ParseException, JSONException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("objectId", objId);

        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject obj= result.get(0);
            Player player= new Player();
            player.setObjectId(objId);
            player.setName(obj.getString("name"));
            player.setPassword(obj.getString("password"));
            player.setCreatedAt(obj.getCreatedAt());
            player.setUpdatedAt(obj.getUpdatedAt());
            JSONArray communitiesIds= obj.getJSONArray("communities");
            ArrayList<Community> communities= new ArrayList<Community>();
            for(int i=0; i<communitiesIds.length(); i++){
                String id= communitiesIds.getString(i);
                Community community= CommunityDAO.getInstance(context).find(id);
                communities.add(community);
            }
            player.setCommunities(communities);
            return player;
        }
        return null;
    }

    public ParseObject find(String name, String password) throws ParseException {
        ParseQuery query = ParseQuery.getQuery("Player");
        query.include("communities");
        query.whereEqualTo("name", name);
        query.whereEqualTo("password", password);
        List<ParseObject> result= query.find();
        if(result.size()==1){
            return result.get(0);
        }
        return  null;
    }
}

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
 * Created by Joan on 22/07/2014.
 */
public class CommunityDAO extends DAO<Community> {

    public static CommunityDAO instance;
    private Context context;

    private CommunityDAO(Context context) {
        super(context);
        this.context = context;
    }

    public static CommunityDAO getInstance(Context context) {
        if(instance==null){
              instance = new CommunityDAO(context);
        }
        return  instance;
    }

    @Override
    public boolean create(Community obj) throws ParseException {
        ParseObject community=  new ParseObject("Community");
        community.put("name", obj.getName());
        community.put("password", obj.getPassword());
        community.save();
        return true;
    }

    @Override
    public boolean delete(Community obj) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("objectId", obj.getObjectId());

        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject community= result.get(0);
            community.delete();
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Community obj) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("objectId", obj.getObjectId());

        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject community= result.get(0);
            community.put("name", obj.getName());
            community.put("password", obj.getPassword());
            return true;
        }
        return false;
    }

    @Override
    public Community find(String objId) throws ParseException, JSONException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("objectId", objId);

        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject obj= result.get(0);
            Community community= new Community();
            community.setObjectId(objId);
            community.setName(obj.getString("name"));
            community.setPassword(obj.getString("password"));
            community.setCreatedAt(obj.getCreatedAt());
            community.setUpdatedAt(obj.getUpdatedAt());
            JSONArray playersIds= obj.getJSONArray("players");
            ArrayList<Player> players= new ArrayList<Player>();
            for(int i=0; i<playersIds.length(); i++){
                String id= playersIds.getString(i);
                Player player= PlayerDAO.getInstance(context).find(id);
                players.add(player);
            }
            return community;
        }
        return null;
    }

    public ParseObject find(String name, String password) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("name", name);
        query.whereEqualTo("password", password);
        List<ParseObject> result= query.find();
        if(result.size()==1){
            return result.get(0);
        }
        return  null;
    }
}

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
    public ParseObject create(Community obj) throws ParseException {
        ParseObject community=  new ParseObject("Community");
        community.put("name", obj.getName());
        community.put("password", obj.getPassword());
        community.save();
        community.refresh();
        return community;
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
    public Community find(String objId) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Community");
        query.whereEqualTo("objectId", objId);

        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject obj= result.get(0);
            Community community= parseObjectToCommunity(obj);
            return community;

        }
        return null;
    }

    public static Community parseObjectToCommunity(ParseObject obj) throws ParseException {
        Community community= new Community();
        community.setObjectId(obj.getObjectId());
        community.setName(obj.getString("name"));
        community.setPassword(obj.getString("password"));
        community.setCreatedAt(obj.getCreatedAt());
        community.setUpdatedAt(obj.getUpdatedAt());

        ArrayList<Inscription> inscriptions= InscriptionDAO.getInstance(getContext()).findByCommunity(community);
        ArrayList<Player> players= new ArrayList<Player>();
        for(Inscription inscription: inscriptions){
            players.add(inscription.getUser());
        }
        community.setPlayers(players);

        ArrayList<Notification> notifications= NotificationDAO.getInstance(getContext()).findByCommunity(community);
        community.setNotifications(notifications);

        ArrayList<Match> matches= MatchDAO.getInstance(getContext()).findByCommunity(community);
        community.setMatches(matches);
        return community;
    }
}

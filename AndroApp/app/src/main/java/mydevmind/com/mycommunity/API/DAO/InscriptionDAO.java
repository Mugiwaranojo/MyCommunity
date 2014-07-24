package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Inscription;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 23/07/2014.
 */
public class InscriptionDAO extends DAO<Inscription> {

    private static InscriptionDAO instance;

    private InscriptionDAO(Context context) {
        super(context);
    }

    public static InscriptionDAO getInstance(Context context) {
        if(instance==null){
            instance = new InscriptionDAO(context);
        }
        return instance;
    }

    @Override
    public ParseObject create(Inscription obj) throws ParseException {
        ParseObject player= ParseObject.createWithoutData("Player", obj.getUser().getObjectId());
        ParseObject community= ParseObject.createWithoutData("Community", obj.getCommunity().getObjectId());
        ParseObject inscription=  new ParseObject("Inscription");
        inscription.put("user", player);
        inscription.put("community", community);
        inscription.save();
        inscription.refresh();
        return inscription;
    }

    @Override
    public boolean delete(Inscription obj) throws ParseException {
        return false;
    }

    @Override
    public boolean update(Inscription obj) throws ParseException {
        return false;
    }

    @Override
    public Inscription find(String objId) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Inscription");
        query.whereEqualTo("objectId", objId);
        query.include("Player");
        query.include("Community");
        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject obj= result.get(0);
            Inscription inscription= parseObjectToInscription(obj);
            return inscription;
        }
        return null;
    }

    public ArrayList<Inscription> findByUser(Player player) throws ParseException {
        ArrayList<Inscription> resultList= new ArrayList<Inscription>();
        ParseObject fetchedPlayer= ParseObject.createWithoutData("Player", player.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Inscription");
        query.whereEqualTo("user", fetchedPlayer);
        query.include("Player");
        query.include("Community");
        List<ParseObject> result= query.find();
        for(ParseObject obj: result){
            resultList.add(parseObjectToInscription(obj));
        }
        return resultList;
    }

    public ArrayList<Inscription> findByCommunity(Community community) throws ParseException {
        ArrayList<Inscription> resultList= new ArrayList<Inscription>();
        ParseObject fetchedCommunity = ParseObject.createWithoutData("Community", community.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Inscription");
        query.whereEqualTo("community", fetchedCommunity);
        query.include("Player");
        query.include("Community");
        List<ParseObject> result= query.find();
        for(ParseObject obj: result){
            resultList.add(parseObjectToInscription(obj));
        }
        return resultList;
    }

    public static Inscription parseObjectToInscription(ParseObject obj) throws ParseException {
        Inscription inscription= new Inscription();
        inscription.setObjectId(obj.getObjectId());
        inscription.setCreatedAt(obj.getCreatedAt());
        inscription.setUpdatedAt(obj.getUpdatedAt());
        inscription.setUser(new Player(obj.getParseObject("user").getObjectId()));
        inscription.setCommunity(new Community(obj.getParseObject("community").getObjectId()));
        return inscription;
    }
}

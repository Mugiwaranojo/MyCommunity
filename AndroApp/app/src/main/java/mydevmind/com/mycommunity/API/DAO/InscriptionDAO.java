package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.mycommunity.API.IAPIResultListener;
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
    public void create(final Inscription obj, final IAPIResultListener<Inscription> listener) {
        ParseObject parsePlayer= ParseObject.createWithoutData("Player", obj.getUser().getObjectId());
        ParseObject parseCommunity= ParseObject.createWithoutData("Community", obj.getCommunity().getObjectId());
        final ParseObject inscription=  new ParseObject("Inscription");
        inscription.put("user", parsePlayer);
        inscription.put("community", parseCommunity);
        inscription.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                listener.onApiResultListener(parseObjectToInscription(inscription), e);
            }
        });
    }

    @Override
    public void delete(Inscription obj, final IAPIResultListener<Inscription> listener){

    }

    @Override
    public void update(Inscription obj, final IAPIResultListener<Inscription> listener){

    }

    @Override
    public void find(String objId, final IAPIResultListener<Inscription> listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Inscription");
        query.whereEqualTo("objectId", objId);
        query.include("Player");
        query.include("Community");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    listener.onApiResultListener(parseObjectToInscription(parseObjects.get(0)), e);
                }else {
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    public ArrayList<Inscription> findByUser(Player player){
        ArrayList<Inscription> resultList= new ArrayList<Inscription>();
        ParseObject fetchedPlayer= ParseObject.createWithoutData("Player", player.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Inscription");
        query.whereEqualTo("user", fetchedPlayer);
        query.include("Player");
        query.include("Community");
        List<ParseObject> result= null;
        try {
            result = query.find();
            for(ParseObject obj: result){
                resultList.add(parseObjectToInscription(obj));
            }
            return resultList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void findByCommunity(Community community, final IAPIResultListener<ArrayList<Inscription
            >> listener){
        final ArrayList<Inscription> resultList= new ArrayList<Inscription>();
        ParseObject fetchedCommunity = ParseObject.createWithoutData("Community", community.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Inscription");
        query.whereEqualTo("community", fetchedCommunity);
        query.include("Player");
        query.include("Community");
        query.findInBackground(new FindCallback<ParseObject>() {
           @Override
           public void done(List<ParseObject> parseObjects, ParseException e) {
               for(ParseObject obj: parseObjects){
                   resultList.add(parseObjectToInscription(obj));
               }
               listener.onApiResultListener(resultList, e);
           }
        });
    }

    public static Inscription parseObjectToInscription(ParseObject obj){
        Inscription inscription= new Inscription();
        inscription.setObjectId(obj.getObjectId());
        inscription.setCreatedAt(obj.getCreatedAt());
        inscription.setUpdatedAt(obj.getUpdatedAt());
        inscription.setUser(new Player(obj.getParseObject("user").getObjectId()));
        inscription.setCommunity(new Community(obj.getParseObject("community").getObjectId()));
        return inscription;
    }
}

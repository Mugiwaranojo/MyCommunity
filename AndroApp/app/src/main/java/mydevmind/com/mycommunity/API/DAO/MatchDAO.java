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
import mydevmind.com.mycommunity.model.Match;

/**
 * Created by Joan on 23/07/2014.
 */
public class MatchDAO extends DAO<Match> {

    private static MatchDAO instance;

    private MatchDAO(Context context) {
        super(context);
    }

    public static MatchDAO getInstance(Context context){
        if(instance==null){
            instance= new MatchDAO(context);
        }
        return instance;
    }

    @Override
    public void create(Match obj,final IAPIResultListener<Match> listener){
        ParseObject community= ParseObject.createWithoutData("Community", obj.getCommunity().getObjectId());
        ParseObject playerFrom= ParseObject.createWithoutData("Player", obj.getPlayerFrom().getObjectId());
        ParseObject playerTo= ParseObject.createWithoutData("Player", obj.getPlayerTo().getObjectId());
        final ParseObject match= new ParseObject("Match");
        match.put("community", community);
        match.put("playerFrom", playerFrom);
        match.put("playerTo", playerTo);
        match.put("scoreFrom", obj.getScoreFrom());
        match.put("scoreTo", obj.getScoreTo());
        match.put("comment", obj.getComment());
        match.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                listener.onApiResultListener(parseObjectToMatch(match), e);
            }
        });
    }

    @Override
    public void delete(Match obj,final IAPIResultListener<Match> listener){
    }

    @Override
    public void update(Match obj, IAPIResultListener<Match> listener){
    }

    @Override
    public void find(String objId, final IAPIResultListener<Match> listener){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Match");
        query.whereEqualTo("objectId", objId);
        query.include("Player");
        query.include("Community");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    ParseObject obj= parseObjects.get(0);
                    Match match= parseObjectToMatch(obj);
                    listener.onApiResultListener(match, e);
                }else {
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    public void findByCommunity(Community community, final IAPIResultListener<ArrayList<Match>> listener){
        final ArrayList<Match> matches= new ArrayList<Match>();
        ParseObject fetchedCommunity = ParseObject.createWithoutData("Community", community.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Match");
        query.whereEqualTo("community", fetchedCommunity);
        query.include("Player");
        query.include("Community");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                for(ParseObject obj: parseObjects){
                    matches.add(parseObjectToMatch(obj));
                }
                listener.onApiResultListener(matches, e);
            }
        });
    }

    private Match parseObjectToMatch(ParseObject obj){
        Match match= new Match();
        match.setObjectId(obj.getObjectId());
        match.setCreatedAt(obj.getCreatedAt());
        match.setUpdatedAt(obj.getUpdatedAt());
        match.setCommunity(CommunityDAO.parseObjectToCommunity(obj.getParseObject("community")));
        match.setPlayerFrom(PlayerDAO.parseObjectToPlayer(obj.getParseObject("playerFrom")));
        match.setPlayerTo(PlayerDAO.parseObjectToPlayer(obj.getParseObject("playerTo")));
        match.setScoreFrom(obj.getInt("scoreFrom"));
        match.setScoreTo(obj.getInt("scoreTo"));
        match.setComment(obj.getString("comment"));
        return match;
    }
}

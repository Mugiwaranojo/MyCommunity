package mydevmind.com.mycommunity.API.DAO;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

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
    public ParseObject create(Match obj) throws ParseException {
        ParseObject community= ParseObject.createWithoutData("Community", obj.getCommunity().getObjectId());
        ParseObject playerFrom= ParseObject.createWithoutData("Player", obj.getPlayerFrom().getObjectId());
        ParseObject playerTo= ParseObject.createWithoutData("Player", obj.getPlayerTo().getObjectId());
        ParseObject match= new ParseObject("Match");
        match.put("community", community);
        match.put("playerFrom", playerFrom);
        match.put("playerTo", playerTo);
        match.put("scoreFrom", obj.getScoreFrom());
        match.put("scoreTo", obj.getScoreTo());
        match.put("comment", obj.getComment());
        match.save();
        match.refresh();
        return match;
    }

    @Override
    public boolean delete(Match obj) throws ParseException {
        return false;
    }

    @Override
    public boolean update(Match obj) throws ParseException {
        return false;
    }

    @Override
    public Match find(String objId) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Match");
        query.whereEqualTo("objectId", objId);
        query.include("Player");
        query.include("Community");
        List<ParseObject> result= query.find();
        if(result.size()==1){
            ParseObject obj= result.get(0);
            Match match= parseObjectToMatch(obj);
            return match;
        }
        return null;
    }

    public ArrayList<Match> findByCommunity(Community community) throws ParseException {
        ArrayList<Match> matches= new ArrayList<Match>();
        ParseObject fetchedCommunity = ParseObject.createWithoutData("Community", community.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Match");
        query.whereEqualTo("community", fetchedCommunity);
        query.include("Player");
        query.include("Community");
        List<ParseObject> result= query.find();
        for(ParseObject obj: result){
            matches.add(parseObjectToMatch(obj));
        }
        return matches;
    }

    private Match parseObjectToMatch(ParseObject obj) throws ParseException {
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

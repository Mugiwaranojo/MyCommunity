package mydevmind.com.mycommunity.API;

import android.content.Context;
import android.text.StaticLayout;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import mydevmind.com.mycommunity.API.DAO.CommunityDAO;
import mydevmind.com.mycommunity.API.DAO.InscriptionDAO;
import mydevmind.com.mycommunity.API.DAO.NotificationDAO;
import mydevmind.com.mycommunity.API.DAO.PlayerDAO;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Inscription;
import mydevmind.com.mycommunity.model.Notification;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 21/07/2014.
 */
public class CommunityAPIManager {

    private static final String APP_ID="4UNxW53O9e42UjNxLaGma5foAtZQpE22H2IwZ9y3";
    private static final String CLIENT_KEY="zqk5C0BKHuWmSaIrSfuWFVyH4MRlAd7g3iY9uUCg";

    private OnApiResultListener listener;

    private static CommunityAPIManager instance;
    private static Context myContext;

    public static CommunityAPIManager getInstance(Context context){
        if(instance==null){
            Parse.initialize(context, APP_ID, CLIENT_KEY);
            instance = new CommunityAPIManager();
            myContext= context;
        }
        return instance;
    }

    public void setListener(OnApiResultListener listener) {
        this.listener = listener;
    }

    public void connection(String login, String password){
        PlayerDAO.getInstance(myContext).findByUserPassword(login, password, listener);
    }

    public void inscriptionPlayerWithoutCommunity(Player player, Community community) throws ParseException{
        ParseObject parseCommunity= CommunityDAO.getInstance(myContext).create(community);
        ParseObject parsePlayer= PlayerDAO.getInstance(myContext).create(player);

        player =  PlayerDAO.getInstance(myContext).find(parsePlayer.getObjectId());
        community= CommunityDAO.getInstance(myContext).find(parseCommunity.getObjectId());

        Inscription inscription= new Inscription();
        inscription.setUser(player);
        inscription.setCommunity(community);
        InscriptionDAO.getInstance(myContext).create(inscription);

        Notification notification= new Notification();
        notification.setCommunity(community);
        notification.setTitle("Bienvenue dans MyCommunity");
        notification.setText("La communauté permetra à vous et vos amis d'enregistrer vos score FIFA, pour qu'il n'y ait plus de discussion sur les mémoires selective!\n" +
                "Pour commencer veuillez ajouter les différents membres de votre communauté et tranmettez leurs, leurs accès");

        NotificationDAO.getInstance(myContext).create(notification);
    }

    public void addPlayerInCommunity(Player player, Community community) throws ParseException {
        ParseObject parsePlayer= PlayerDAO.getInstance(myContext).create(player);
        player =  PlayerDAO.getInstance(myContext).find(parsePlayer.getObjectId());
        community= CommunityDAO.getInstance(myContext).find(community.getObjectId());

        Inscription inscription= new Inscription();
        inscription.setUser(player);
        inscription.setCommunity(community);
        InscriptionDAO.getInstance(myContext).create(inscription);

        Notification notification= new Notification();
        notification.setCommunity(community);
        notification.setTitle(player.getName()+" a rejoint vôtre communauté");
        notification.setText(player.getName()+" est un nouveau concourant. Mettez le à l'épreuve dès maintenant en lui proposant un match.");

        NotificationDAO.getInstance(myContext).create(notification);
    }
}

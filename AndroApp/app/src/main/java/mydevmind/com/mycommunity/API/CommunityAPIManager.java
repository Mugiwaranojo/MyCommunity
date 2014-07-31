package mydevmind.com.mycommunity.API;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.Collections;

import mydevmind.com.mycommunity.API.DAO.CommunityDAO;
import mydevmind.com.mycommunity.API.DAO.InscriptionDAO;
import mydevmind.com.mycommunity.API.DAO.MatchDAO;
import mydevmind.com.mycommunity.API.DAO.NotificationDAO;
import mydevmind.com.mycommunity.API.DAO.PlayerDAO;
import mydevmind.com.mycommunity.fragment.CommunityFragment;
import mydevmind.com.mycommunity.model.Community;
import mydevmind.com.mycommunity.model.Information;
import mydevmind.com.mycommunity.model.Inscription;
import mydevmind.com.mycommunity.model.Match;
import mydevmind.com.mycommunity.model.Notification;
import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 21/07/2014.
 */
public class CommunityAPIManager {

    private static final String APP_ID="4UNxW53O9e42UjNxLaGma5foAtZQpE22H2IwZ9y3";
    private static final String CLIENT_KEY="zqk5C0BKHuWmSaIrSfuWFVyH4MRlAd7g3iY9uUCg";

    private static IAPIResultListener<Player> playerListener;
    private static IAPIResultListener<ArrayList<Community>> communityListListener;
    private static IAPIResultListener<Inscription> inscriptionListener;
    private static IAPIResultListener<Notification> notificationListener;
    private static IAPIResultListener<ArrayList<Information>> informationsListener;
    private static IAPIResultListener<Match> matchListener;
    private static IAPIResultListener<ArrayList<Player>> playersListListener;

    private Player curentPlayer;
    private Community currentCommunity;

    private static CommunityAPIManager instance;

    private CommunityAPIManager(Context context) {
        Parse.initialize(context, APP_ID, CLIENT_KEY);
    }

    public static CommunityAPIManager getInstance(Context context){
        if(instance==null){
            instance= new CommunityAPIManager(context);
        }
        return instance;
    }

    public Player getCurentPlayer() {
        return curentPlayer;
    }

    public void setCurentPlayer(Player curentPlayer) {
        this.curentPlayer = curentPlayer;
    }

    public Community getCurrentCommunity() {
        return currentCommunity;
    }

    public void setCurrentCommunity(Community currentCommunity) {
        this.currentCommunity = currentCommunity;
    }

    public void setPlayerListener(IAPIResultListener<Player> playerListener) {
        CommunityAPIManager.playerListener = playerListener;
    }

    public  void setCommunityListListener(IAPIResultListener<ArrayList<Community>> communityListListener) {
        CommunityAPIManager.communityListListener = communityListListener;
    }

    public  void setInscriptionListener(IAPIResultListener<Inscription> inscriptionListener) {
        CommunityAPIManager.inscriptionListener = inscriptionListener;
    }

    public  void setNotificationListener(IAPIResultListener<Notification> notificationListener) {
        CommunityAPIManager.notificationListener = notificationListener;
    }

    public  void setInformationsListener(IAPIResultListener<ArrayList<Information>> informationListener) {
        CommunityAPIManager.informationsListener = informationListener;
    }

    public  void setMatchListener(IAPIResultListener<Match> matchListener){
        CommunityAPIManager.matchListener= matchListener;
    }

    public  void setPlayersListListener(IAPIResultListener<ArrayList<Player>> playersListListener) {
        CommunityAPIManager.playersListListener = playersListListener;
    }

    public void connection(String login, String password) {
        PlayerDAO.getInstance().findByUserPassword(login, password, new IAPIResultListener<Player>() {
            @Override
            public void onApiResultListener(final Player player, ParseException e) {
                final ArrayList<Community> userCommunities= new ArrayList<Community>();
                InscriptionDAO.getInstance().findByUser(player, new IAPIResultListener<ArrayList<Inscription>>() {
                    @Override
                    public void onApiResultListener(ArrayList<Inscription> inscriptionArrayList, ParseException e) {
                        for(Inscription inscription: inscriptionArrayList){
                            userCommunities.add(inscription.getCommunity());
                        }
                        player.setCommunities(userCommunities);
                        setCurentPlayer(player);
                        setCurrentCommunity(userCommunities.get(0));
                        playerListener.onApiResultListener(player, e);
                    }
                });
            }
        });
    }

    public void inscriptionPlayerAndCommunity(Player player, final Community community) {
        PlayerDAO.getInstance().create(player, new IAPIResultListener<Player>() {
            @Override
            public void onApiResultListener(final Player resultPlayer, ParseException e) {
                if (resultPlayer != null) {
                    CommunityDAO.getInstance().create(community, new IAPIResultListener<Community>() {
                        @Override
                        public void onApiResultListener(Community resultCommunity, ParseException e) {
                            if (resultCommunity != null) {
                                Inscription inscription = new Inscription();
                                inscription.setUser(resultPlayer);
                                inscription.setCommunity(resultCommunity);
                                InscriptionDAO.getInstance().create(inscription, new IAPIResultListener<Inscription>() {
                                    @Override
                                    public void onApiResultListener(Inscription obj, ParseException e) {
                                        Notification notification= new Notification();
                                        notification.setCommunity(obj.getCommunity());
                                        notification.setTitle("Bienvenue dans MyCommunity");
                                        notification.setText("La communauté permetra à vous et vos amis d'enregistrer vos score FIFA, pour qu'il n'y ait plus de discussion sur les mémoires selective!\n" +
                                                "Pour commencer veuillez ajouter les différents membres de votre communauté et tranmettez leurs, leurs accès");
                                        community.getNotifications().add(notification);
                                        NotificationDAO.getInstance().create(notification, notificationListener);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void addPlayerInCommunity(Player player){
        PlayerDAO.getInstance().create(player, new IAPIResultListener<Player>() {
            @Override
            public void onApiResultListener(final Player obj, ParseException e) {
                Inscription inscription = new Inscription();

                inscription.setUser(obj);
                inscription.setCommunity(getCurrentCommunity());
                InscriptionDAO.getInstance().create(inscription, new IAPIResultListener<Inscription>() {
                    @Override
                    public void onApiResultListener(Inscription objInscription, ParseException e) {
                        if (objInscription != null) {
                            getCurrentCommunity().getPlayers().add(obj);
                            Notification notification= new Notification();
                            notification.setCommunity(objInscription.getCommunity());
                            notification.setTitle(obj.getName()+" a rejoint vôtre communauté");
                            notification.setText(obj.getName()+" est un nouveau concourant. Mettez le à l'épreuve dès maintenant en lui proposant un match.");
                            NotificationDAO.getInstance().create(notification, notificationListener);
                        }
                    }
                });
            }
        });
    }

    public void fetchInformations(){
        final ArrayList<Information> informationsList = new ArrayList<Information>();
        NotificationDAO.getInstance().findByCommunity(getCurrentCommunity(), new IAPIResultListener<ArrayList<Notification>>() {
            @Override
            public void onApiResultListener(ArrayList<Notification> notificationsList, ParseException e) {
                getCurrentCommunity().setNotifications(notificationsList);
                for(Notification n: notificationsList){
                    informationsList.add(n);
                }
                MatchDAO.getInstance().findByCommunity(getCurrentCommunity(), new IAPIResultListener<ArrayList<Match>>() {
                    @Override
                    public void onApiResultListener(ArrayList<Match> matchesList, ParseException e) {
                        getCurrentCommunity().setMatches(matchesList);
                        for (Match m: matchesList){
                            informationsList.add(m);
                        }
                        Collections.sort(informationsList, Collections.reverseOrder());
                        informationsListener.onApiResultListener(informationsList, e);
                    }
                });
            }
        });
    }

    public void fetchPlayers(){
        final ArrayList<Player> playersList= new ArrayList<Player>();
        InscriptionDAO.getInstance().findByCommunity(getCurrentCommunity(), new IAPIResultListener<ArrayList<Inscription>>() {
            @Override
            public void onApiResultListener(ArrayList<Inscription> inscriptionArrayList, ParseException e) {
                for (Inscription inscription: inscriptionArrayList){
                    playersList.add(inscription.getUser());
                }
                getCurrentCommunity().setPlayers(playersList);
                playersListListener.onApiResultListener(playersList, e);
            }
        });
    }

    public void addMatch(Match match){
        MatchDAO.getInstance().create(match, new IAPIResultListener<Match>() {
            @Override
            public void onApiResultListener(Match obj, ParseException e) {
                matchListener.onApiResultListener(obj, e);
            }
        });
    }
}

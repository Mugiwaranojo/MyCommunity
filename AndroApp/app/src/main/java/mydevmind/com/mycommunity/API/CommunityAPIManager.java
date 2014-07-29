package mydevmind.com.mycommunity.API;

import android.content.Context;

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

    private static IAPIResultListener<Player> playerListener;
    private static IAPIResultListener<ArrayList<Community>> communityListListener;
    private static IAPIResultListener<Inscription> inscriptionListener;
    private static IAPIResultListener<Notification> notificationListener;
    private static IAPIResultListener<ArrayList<Information>> informationsListener;
    private static IAPIResultListener<Match> matchListener;
    private static IAPIResultListener<ArrayList<Player>> playersListListener;

    private Context myContext;

    public CommunityAPIManager(Context context) {
        myContext = context;
    }

    public static void setPlayerListener(IAPIResultListener<Player> playerListener) {
        CommunityAPIManager.playerListener = playerListener;
    }

    public static void setCommunityListListener(IAPIResultListener<ArrayList<Community>> communityListListener) {
        CommunityAPIManager.communityListListener = communityListListener;
    }

    public static void setInscriptionListener(IAPIResultListener<Inscription> inscriptionListener) {
        CommunityAPIManager.inscriptionListener = inscriptionListener;
    }

    public static void setNotificationListener(IAPIResultListener<Notification> notificationListener) {
        CommunityAPIManager.notificationListener = notificationListener;
    }

    public static void setInformationsListener(IAPIResultListener<ArrayList<Information>> informationListener) {
        CommunityAPIManager.informationsListener = informationListener;
    }

    public static void setMatchListener(IAPIResultListener<Match> matchListener){
        CommunityAPIManager.matchListener= matchListener;
    }

    public static void setPlayersListListener(IAPIResultListener<ArrayList<Player>> playersListListener) {
        CommunityAPIManager.playersListListener = playersListListener;
    }

    public void connection(String login, String password) {
        PlayerDAO.getInstance(myContext).findByUserPassword(login, password, playerListener);
    }

    public void inscriptionPlayerAndCommunity(Player player, final Community community) {
        PlayerDAO.getInstance(myContext).create(player, new IAPIResultListener<Player>() {
            @Override
            public void onApiResultListener(final Player resultPlayer, ParseException e) {
                if (resultPlayer != null) {
                    CommunityDAO.getInstance(myContext).create(community, new IAPIResultListener<Community>() {
                        @Override
                        public void onApiResultListener(Community resultCommunity, ParseException e) {
                            if (resultCommunity != null) {
                                Inscription inscription = new Inscription();
                                inscription.setUser(resultPlayer);
                                inscription.setCommunity(resultCommunity);
                                InscriptionDAO.getInstance(myContext).create(inscription, new IAPIResultListener<Inscription>() {
                                    @Override
                                    public void onApiResultListener(Inscription obj, ParseException e) {
                                        Notification notification= new Notification();
                                        notification.setCommunity(obj.getCommunity());
                                        notification.setTitle("Bienvenue dans MyCommunity");
                                        notification.setText("La communauté permetra à vous et vos amis d'enregistrer vos score FIFA, pour qu'il n'y ait plus de discussion sur les mémoires selective!\n" +
                                                "Pour commencer veuillez ajouter les différents membres de votre communauté et tranmettez leurs, leurs accès");
                                        NotificationDAO.getInstance(myContext).create(notification, notificationListener);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void addPlayerInCommunity(Player player, Community community){
        PlayerDAO.getInstance(myContext).create(player, new IAPIResultListener<Player>() {
            @Override
            public void onApiResultListener(final Player obj, ParseException e) {
                Inscription inscription = new Inscription();
                inscription.setUser(obj);
                inscription.setCommunity(CommunityFragment.getCurrentCommunity());
                InscriptionDAO.getInstance(myContext).create(inscription, new IAPIResultListener<Inscription>() {
                    @Override
                    public void onApiResultListener(Inscription objInscription, ParseException e) {
                        if (objInscription != null) {
                            Notification notification= new Notification();
                            notification.setCommunity(objInscription.getCommunity());
                            notification.setTitle(obj.getName()+" a rejoint vôtre communauté");
                            notification.setText(obj.getName()+" est un nouveau concourant. Mettez le à l'épreuve dès maintenant en lui proposant un match.");
                            NotificationDAO.getInstance(myContext).create(notification, notificationListener);
                        }
                    }
                });
            }
        });
    }

    public void fetchCommunities(final Player player){
        final ArrayList<Community> communitiesList= new ArrayList<Community>();
        InscriptionDAO.getInstance(myContext).findByUser(player, new IAPIResultListener<ArrayList<Inscription>>() {
            @Override
            public void onApiResultListener(ArrayList<Inscription> inscriptionArrayList, ParseException e) {
               Community tempCommunity= inscriptionArrayList.get(0).getCommunity();
               CommunityDAO.getInstance(myContext).find(tempCommunity.getObjectId(), new IAPIResultListener<Community>() {
                   @Override
                   public void onApiResultListener(Community obj, ParseException e) {
                       communitiesList.add(obj);
                       communityListListener.onApiResultListener(communitiesList, e);
                   }
               });
            }
        });

    }

    public void fetchInformations(final Community community){
        final ArrayList<Information> informationsList = new ArrayList<Information>();
        NotificationDAO.getInstance(myContext).findByCommunity(community, new IAPIResultListener<ArrayList<Notification>>() {
            @Override
            public void onApiResultListener(ArrayList<Notification> notificationsList, ParseException e) {
                community.setNotifications(notificationsList);
                for(Notification n: notificationsList){
                    informationsList.add(n);
                }
                MatchDAO.getInstance(myContext).findByCommunity(community, new IAPIResultListener<ArrayList<Match>>() {
                    @Override
                    public void onApiResultListener(ArrayList<Match> matchesList, ParseException e) {
                        community.setMatches(matchesList);
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

    public void fetchPlayers(final Community community){
        final ArrayList<Player> playersList= new ArrayList<Player>();
        InscriptionDAO.getInstance(myContext).findByCommunity(community, new IAPIResultListener<ArrayList<Inscription>>() {
            @Override
            public void onApiResultListener(ArrayList<Inscription> inscriptionArrayList, ParseException e) {
                for (Inscription inscription: inscriptionArrayList){
                    playersList.add(PlayerDAO.getInstance(myContext).find(inscription.getUser().getObjectId()));
                }
                community.setPlayers(playersList);
                playersListListener.onApiResultListener(playersList, e);
            }
        });
    }

    public void addMatch(Match match){
        MatchDAO.getInstance(myContext).create(match, new IAPIResultListener<Match>() {
            @Override
            public void onApiResultListener(Match obj, ParseException e) {
                matchListener.onApiResultListener(obj, e);
            }
        });
    }
}

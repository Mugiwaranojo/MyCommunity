package mydevmind.com.mycommunity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Joan on 21/07/2014.
 */
public class Community implements Serializable {

    private String objectId;
    private String name;
    private String password;
    private Date createdAt;
    private Date updatedAt;

    private ArrayList<Player> players;
    private ArrayList<Notification> notifications;
    private ArrayList<Match> matches;

    public Community(){

    }

    public Community(String objectId){
        this.objectId= objectId;
    }


    public Community(String name, String password){
        this.name = name;
        this.password = password;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void remove(Player player){
        this.players.remove(player);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }

    public void remove(Notification notification){
        this.notifications.remove(notification);
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public void addMatch(Match match){
        this.matches.add(match);
    }

    public void remove(Match match){
        this.matches.remove(match);
    }

    public ArrayList<Information> getInformations(){
        ArrayList<Information> list= new ArrayList<Information>();
        for(Notification n: getNotifications()){
            list.add(n);
        }
        for (Match m: getMatches()){
            list.add(m);
        }
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }
}

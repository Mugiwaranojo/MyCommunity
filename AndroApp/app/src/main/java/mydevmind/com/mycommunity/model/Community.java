package mydevmind.com.mycommunity.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Joan on 21/07/2014.
 */
public class Community {

    private String objectId;
    private String name;
    private String password;
    private Date createdAt;
    private Date updatedAt;

    private Set<Player> players= new HashSet<Player>();

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

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void remove(Player player){
        this.players.remove(player);
    }
}

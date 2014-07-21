package mydevmind.com.mycommunity.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Joan on 21/07/2014.
 */
public class Player {

    private String objectId;
    private String name;
    private String password;
    private Date createdAt;
    private Date updatedAt;

    private Set<Community> communities= new HashSet<Community>();

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

    public Set<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public void addCommunity(Community community){
        this.communities.add(community);
    }

    public void removeCommunity(Community community){
        this.communities.remove(community);
    }
}

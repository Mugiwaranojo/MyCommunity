package mydevmind.com.mycommunity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Joan on 23/07/2014.
 */
public class Notification implements Information, Serializable{

    private String objectId;
    private Community community;
    private Player writer;
    private String title;
    private String text;
    private Date createdAt;
    private Date updatedAt;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Player getWriter() {
        return writer;
    }

    public void setWriter(Player writer) {
        this.writer = writer;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public Date getDate(){
        return createdAt;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Information){
            Information information=(Information) o;
            return getCreatedAt().compareTo(information.getDate());
        }
        return 0;
    }
}

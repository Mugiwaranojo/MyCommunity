package mydevmind.com.mycommunity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Joan on 23/07/2014.
 */
public class Match implements Information, Serializable {

    private String objectId;
    private Community community;
    private Player playerFrom;
    private Player playerTo;
    private Integer scoreFrom;
    private Integer scoreTo;
    private String comment;
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

    public Player getPlayerFrom() {
        return playerFrom;
    }

    public void setPlayerFrom(Player playerFrom) {
        this.playerFrom = playerFrom;
    }

    public Player getPlayerTo() {
        return playerTo;
    }

    public void setPlayerTo(Player playerTo) {
        this.playerTo = playerTo;
    }

    public Integer getScoreFrom() {
        return scoreFrom;
    }

    public void setScoreFrom(Integer scoreFrom) {
        this.scoreFrom = scoreFrom;
    }

    public Integer getScoreTo() {
        return scoreTo;
    }

    public void setScoreTo(Integer scoreTo) {
        this.scoreTo = scoreTo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return this.createdAt;
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
    public  Date getDate(){
        return createdAt;
    }

    @Override
    public String getTitle() {
        return this.playerFrom.getName()+ " VS "+ this.playerTo.getName();
    }

    @Override
    public String getText() {
        return this.scoreFrom+ " - " + this.scoreTo+"\n"+"\n"+
                this.getComment();
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Information){
            Information information=(Information) o;
            return getCreatedAt().compareTo(information.getDate());
        }
        return 0;
    }

    public Player getWinner(){
        if(scoreFrom>scoreTo){
            return playerFrom;
        }else if(scoreTo>scoreFrom){
            return playerTo;
        }else {
            return null;
        }
    }

    public Player getLoser(){
        if(scoreFrom<scoreTo){
            return playerFrom;
        }else if(scoreTo<scoreFrom){
            return playerTo;
        }else {
            return null;
        }
    }
}

package mydevmind.com.mycommunity.model;

/**
 * Created by Mugiwara on 29/07/2014.
 */
public class Statistic implements Comparable {

    private Player player;
    private Integer play=0, won=0, lose=0,draw=0, goalDone=0, goalReceive=0;


    public Statistic(Player player){
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Integer getPlay() {
        return play;
    }

    public void setPlay(Integer play) {
        this.play = play;
    }

    public Integer getWon() {
        return won;
    }

    public void setWon(Integer won) {
        this.won = won;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getGoalDone() {
        return goalDone;
    }

    public void setGoalDone(Integer goalDone) {
        this.goalDone = goalDone;
    }

    public Integer getGoalReceive() {
        return goalReceive;
    }

    public void setGoalReceive(Integer goalReceive) {
        this.goalReceive = goalReceive;
    }

    public Integer getPoints(){
        return getWon()*3 + getDraw();
    }
    public static Statistic getPlayerStatistic(Player p, Community community){
        Statistic statistic= new Statistic(p);
        Integer play=0, won=0, lose=0,draw=0, goalDone=0, goalReceive=0;
        for(Match match:community.getMatches()){
            if(match.getPlayerFrom().getObjectId().equals(p.getObjectId())||
                    match.getPlayerTo().getObjectId().equals(p.getObjectId())){

                play++;
                if(match.getPlayerFrom().getObjectId().equals(p.getObjectId())){
                    goalDone+=match.getScoreFrom();
                    goalReceive+=match.getScoreTo();
                }else{
                    goalDone+=match.getScoreTo();
                    goalReceive+=match.getScoreFrom();
                }
                Player winner= match.getWinner();
                if(winner==null){
                    draw++;
                }else if(winner.getObjectId().equals(p.getObjectId())){
                    won++;
                }else{
                    lose++;
                }
            }
        }
        statistic.setPlay(play);
        statistic.setWon(won);
        statistic.setDraw(draw);
        statistic.setLose(lose);
        statistic.setGoalDone(goalDone);
        statistic.setGoalReceive(goalReceive);
        return statistic;
    }

    @Override
    public int compareTo(Object another) {
        return this.getPoints().compareTo(((Statistic)another).getPoints());
    }
}

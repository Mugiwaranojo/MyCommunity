package mydevmind.com.mycommunity.model;

import java.util.Date;

/**
 * Created by Joan on 23/07/2014.
 */
public interface Information extends Comparable {

    public Date getDate();
    public String getTitle();
    public String getText();

}

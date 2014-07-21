package mydevmind.com.mycommunity.API;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseObject;

import mydevmind.com.mycommunity.model.Player;

/**
 * Created by Joan on 21/07/2014.
 */
public class PlayerDAO extends DAO<Player> {

    public PlayerDAO(Context context) {
        super(context);
    }

    @Override
    public boolean create(Player obj) {
        ParseObject player=  new ParseObject("Player");
        player.put("name", obj.getName());
        player.put("password", obj.getPassword());
        player.saveInBackground();
        return false;
    }

    @Override
    public boolean delete(Player obj) {
        return false;
    }

    @Override
    public boolean update(Player obj) {
        return false;
    }

    @Override
    public boolean find(Player obj) {
        return false;
    }
}

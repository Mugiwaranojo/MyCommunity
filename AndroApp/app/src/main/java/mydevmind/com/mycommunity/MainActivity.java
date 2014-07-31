package mydevmind.com.mycommunity;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.parse.ParseException;

import mydevmind.com.mycommunity.API.CommunityAPIManager;
import mydevmind.com.mycommunity.fragment.ClassementFragment;
import mydevmind.com.mycommunity.fragment.CommunityFragment;
import mydevmind.com.mycommunity.fragment.IFragmentActionListener;
import mydevmind.com.mycommunity.fragment.NavigationDrawerFragment;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, IFragmentActionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CommunityFragment communityFragment;
    private ClassementFragment classementFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private static CharSequence mTitle;

    public static CharSequence getmTitle() {
        return mTitle;
    }

    public static void setmTitle(CharSequence sequence) {
        mTitle = sequence;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setActionLister(this);

        communityFragment= new CommunityFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.container, communityFragment)
                .commit();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if (findViewById(R.id.listViewMainInformations) != null){
            if(position==0) {
               if(classementFragment==null){
                   fragmentManager.beginTransaction()
                           .show(communityFragment)
                           .commit();
               }else {
                   fragmentManager.beginTransaction()
                           .show(communityFragment)
                           .hide(classementFragment)
                           .commit();
               }
            }else if(position==1){
                if(classementFragment==null){
                    classementFragment= new ClassementFragment();
                    fragmentManager.beginTransaction()
                            .add(R.id.container, classementFragment)
                            .hide(communityFragment)
                            .commit();
                }else{
                    fragmentManager.beginTransaction()
                            .show(classementFragment)
                            .hide(communityFragment)
                            .commit();
                }
            }
        }
    }


    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.global, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentAction(Integer action, Object obj) {
        if(action.equals(NavigationDrawerFragment.ACTION_ADD_MATCH)&& classementFragment!=null){
            classementFragment.updateListView();
        }
        communityFragment.updateListView();

    }
}

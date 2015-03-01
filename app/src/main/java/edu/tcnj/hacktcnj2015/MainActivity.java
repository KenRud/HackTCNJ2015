package edu.tcnj.hacktcnj2015;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity implements ItemFragment.OnFragmentInteractionListener,
        UsernameFragment.OnFragmentInteractionListener {

    public static String user = null;

    private static final String PREFIX = "edu.tcnj.hacktcnj2015.";

    public static final String TEST_MESSAGE = PREFIX + "TESTMESSAGE";

    private Fragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userFragment = new UsernameFragment();

        if (user == null) {
            setContentView(R.layout.activity_main);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, userFragment)
                        .commit();
            }
        } else {
            setContentView(R.layout.activity_main);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new ItemFragment(user))
                        .commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String id) {
        // start playback activity
        Intent intent = new Intent(this, PlaybackActivity.class);
        intent.putExtra(TEST_MESSAGE, "something");
        intent.putExtra(PREFIX + "User", user);
        intent.putExtra(PREFIX + "Opponent", GameList.ITEM_MAP.get(id).content);
        startActivity(intent);
    }

    public void submit() {
        ItemFragment itemFragment = new ItemFragment(user);
        getSupportFragmentManager().beginTransaction()
                .hide(userFragment)
                .add(R.id.container, itemFragment)
                .show(itemFragment)
                .commit();
    }

    @Override
    public void onUsernameFragmentInteraction(String username, EditText editText) {
        System.out.println(username);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        user = username.trim();
        submit();
    }
}

package edu.tcnj.hacktcnj2015;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class PlaybackActivity extends ActionBarActivity {
    private final PlaybackFragment playbackFragment = new PlaybackFragment();
    private final ChallengeFragment challengeFragment = new ChallengeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.TEST_MESSAGE);
        System.out.println(message);

        setContentView(R.layout.activity_playback);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, playbackFragment)
                    .add(R.id.container, challengeFragment)
                    .hide(challengeFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playback, menu);
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

    public void switchToChallengeScreen(View view) {
        playbackFragment.stopVideo();
        challengeFragment.loadAudio();
        getFragmentManager().beginTransaction()
                .hide(playbackFragment)
                .show(challengeFragment)
                .commit();
    }
}

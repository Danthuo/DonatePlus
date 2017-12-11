package bbitb.com.donateplus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomesList.OnFragmentInteractionListener
                    {


    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            //Login Activity here if user is not logged in
            finish();
            startActivity(new Intent(this, Login.class));

        }else {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String displayEmail = user.getEmail();

            // NavigationView
            NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
            View mHeaderView =  mNavigationView.inflateHeaderView(R.layout.nav_header_main);

            //View
            textViewUserEmail = (TextView) mHeaderView.findViewById(R.id.textViewUserEmail);

            //Set Email
            textViewUserEmail.setText(displayEmail);


        }


        //buttonLogout = (Button) findViewById(R.id.buttonLogout);
        //buttonLogout.setOnClickListener(this);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Fragment myfragment = null;
        Class fragmentClass = HomesList.class;

        try {
            myfragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fLcontent,myfragment).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment myfragment = null;
        Class fragmentClass = HomesList.class;


        Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT);

        Log.wtf("This", "Has been clicked");
        int id = item.getItemId();

        if (id == R.id.nav_notifications) {
            fragmentClass = Notifications.class;

        } else if (id == R.id.nav_my_donations) {
            fragmentClass = MyDonations.class;

        } else if (id == R.id.nav_homes) {
            fragmentClass = HomesList.class;

        } else if (id == R.id.nav_donate) {
            fragmentClass = Donate.class;

        } else if (id == R.id.nav_rewards) {
            fragmentClass = Rewards.class;

        } else if (id == R.id.nav_manage) {
            fragmentClass = Settings.class;

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_about) {
            fragmentClass = AboutUs.class;
        }

        try {
            myfragment = (Fragment) fragmentClass.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fLcontent,myfragment).commit();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onNavigationItemSelected(item);
                return true;
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}

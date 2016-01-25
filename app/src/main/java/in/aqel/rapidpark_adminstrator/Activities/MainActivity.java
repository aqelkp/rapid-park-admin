package in.aqel.rapidpark_adminstrator.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;

import in.aqel.quickparksdk.Objects.Parking;
import in.aqel.quickparksdk.Utils.PrefUtils;
import in.aqel.rapidpark_adminstrator.Fragments.CountFragment;
import in.aqel.rapidpark_adminstrator.Fragments.ScanQr;
import in.aqel.rapidpark_adminstrator.Fragments.UpdateDetailsFragment;
import in.aqel.rapidpark_adminstrator.R;
import in.aqel.quickparksdk.Utils.AppConstants;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static String LOG_TAG = "MainActivity";
    Context context = MainActivity.this;
    Firebase ref;
    public Parking parking;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(LOG_TAG, "Parking object saved" + PrefUtils.getParking(context));



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new CountFragment());
        fragmentTransaction.commit();

        Firebase.setAndroidContext(this);

        ref = new Firebase(AppConstants.SERVER);

        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {

                    // user is logged in
                    Log.d(LOG_TAG, " logged in");
                    Log.d(LOG_TAG, "id:" + authData.getUid());

                    Query queryRef = ref.child("parkings").orderByChild("user").equalTo(authData.getUid());

                    queryRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            Log.d(LOG_TAG, "onChildAdded");
                            Log.d(LOG_TAG, "Length of snapshot" + snapshot.getChildrenCount());
                            if (!snapshot.exists()){
                                Intent intent = new Intent(context, NewParkingActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                            for (DataSnapshot parkingSnap: snapshot.getChildren()) {
                                parking = parkingSnap.getValue(Parking.class);
                                PrefUtils.setParking(context, parkingSnap.getValue().toString());
                                PrefUtils.setParkingId(context, parkingSnap.getKey());
                                System.out.println(parking.getName() + " - " + snapshot.getKey());
                            }
                            try{
                                CountFragment fragment =
                                        (CountFragment) getSupportFragmentManager()
                                                .findFragmentById(R.id.fragment_container);
                                fragment.updateParking(parking);
                            } catch (Exception e ){
                                e.printStackTrace();
                            }
                            Log.d(LOG_TAG, snapshot.toString());
                            Log.d(LOG_TAG, snapshot.getValue().toString());
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                } else {
                    // user is not logged in
                    Log.d(LOG_TAG, "Not logged in");
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    public Parking getParking(){
        Log.d(LOG_TAG, "Parking in MainAct" + new Gson().toJson(parking) );
        return parking;
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
        if (id == R.id.action_logout) {
            ref.unauth();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_qr_booking) {
            // Handle the camera action

        } else if (id == R.id.nav_counter) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, new CountFragment());
            fragmentTransaction.commit();
        }
        else if (id==R.id.nav_qr_booking) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, new ScanQr());
            fragmentTransaction.commit();
        }
            else if (id == R.id.nav_logout) {
            ref.unauth();
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_update_details) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new UpdateDetailsFragment());
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

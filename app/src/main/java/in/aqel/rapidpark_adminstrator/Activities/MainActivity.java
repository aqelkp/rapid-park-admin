package in.aqel.rapidpark_adminstrator.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import in.aqel.quickparksdk.Objects.Parking;
import in.aqel.rapidpark_adminstrator.R;
import in.aqel.rapidpark_adminstrator.Utils.AppConstants;

public class MainActivity extends AppCompatActivity {

    private static String LOG_TAG = "MainActivity";
    Context context = MainActivity.this;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);

        ref = new Firebase(AppConstants.SERVER);

        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    // user is logged in
                    Log.d(LOG_TAG, " logged in");
                    Log.d(LOG_TAG, "id:" + authData.getUid());

                    ref.child("parkings").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                Log.d(LOG_TAG, postSnapshot.getValue().toString());
                            }
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }
                    });

                    Query queryRef = ref.child("parkings").orderByChild("totalCars");
                    queryRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                            Log.d(LOG_TAG, "onChildAdded for parking");
                            Log.d(LOG_TAG, "Total number of parkings " + snapshot.getChildrenCount());
                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                Parking parking = postSnapshot.getValue(Parking.class);
                                System.out.println(parking.getName() + " - " + parking.getUser());
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            Log.d(LOG_TAG, firebaseError.getMessage());
                        }
                    });


//                    Query query = ref.child("parkings").child("user").equalTo(authData.getUid());
//                    Log.d(LOG_TAG, "User id for query is:" + authData.getUid());
//                    query.addChildEventListener(new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot snapshot, String s) {
//                            Log.d(LOG_TAG, "onChildAdded");
//                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
//                                Parking parking = postSnapshot.getValue(Parking.class);
//                                System.out.println(parking.getName() + " - " + parking.getUser());
//                            }
//
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                            Log.d(LOG_TAG, "child changed");
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(FirebaseError firebaseError) {
//                            Log.d(LOG_TAG, firebaseError.getMessage());
//
//                        }
//                    });

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
}

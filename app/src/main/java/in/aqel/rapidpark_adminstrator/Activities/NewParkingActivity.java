package in.aqel.rapidpark_adminstrator.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import in.aqel.quickparksdk.Objects.Parking;
import in.aqel.quickparksdk.Utils.PrefUtils;
import in.aqel.rapidpark_adminstrator.R;
import in.aqel.rapidpark_adminstrator.Utils.AppConstants;

public class NewParkingActivity extends AppCompatActivity {

    Switch sBooking;
    boolean isBooking = false;
    EditText etName,  etLat, etLon, etTotalCars, etTotalBikes, etCurrentCars, etCurrentBikes;
    EditText etParkingCharge, etBookingCharge, etEmail;
    String name, email;
    int totalCars = 0, totalBikes =0, parkingCharge = 0, bookingCharge =0;
    Double lat, lon;
    Context context = NewParkingActivity.this;
    ProgressDialog pDialog;
    Firebase ref;
    private static String LOG_TAG = "NewParkingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_parking);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText) findViewById(R.id.etName);
        etLat = (EditText) findViewById(R.id.etLat);
        etLon = (EditText) findViewById(R.id.etLon);
        etTotalCars = (EditText) findViewById(R.id.etTotalCars);
        etTotalBikes = (EditText) findViewById(R.id.etTotalBikes);
        etParkingCharge = (EditText) findViewById(R.id.etParkingCharge);
        etBookingCharge = (EditText) findViewById(R.id.etBookingCharge);

        sBooking = (Switch) findViewById(R.id.sBooking);
        sBooking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isBooking = isChecked;
                etBookingCharge.setEnabled(isChecked);
            }
        });

        etEmail.setText(PrefUtils.getEmail(context));

        Firebase.setAndroidContext(this);

        ref = new Firebase(AppConstants.SERVER);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            email = etEmail.getText().toString();
            name = etName.getText().toString();

            

            if ( !isValidInput()){
                Snackbar
                        .make(etBookingCharge, "Please enter all the details to continue", Snackbar.LENGTH_SHORT)
                        .show();
                return true;
            }

            lon = Double.parseDouble(etLon.getText().toString());
            lat = Double.parseDouble(etLat.getText().toString());
            totalCars = Integer.parseInt(etTotalCars.getText().toString());
            totalBikes = Integer.parseInt(etTotalBikes.getText().toString());
            parkingCharge = Integer.parseInt(etParkingCharge.getText().toString());

            Parking parking = new Parking();
            parking.setName(name);
            parking.setLat(lat);
            parking.setLon(lon);
            parking.setTotalBikes(totalBikes);
            parking.setTotalCars(totalCars);
            parking.setParkingCharge(parkingCharge);
            parking.setBooking(isBooking);
            parking.setUser(ref.getAuth().getUid());
            if (isBooking) {
                bookingCharge = Integer.parseInt(etBookingCharge.getText().toString());
                parking.setBookingCharge(bookingCharge);
            }
            showProgressDialog();
            ref.child("parkings").push().setValue(parking, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null){
                        Snackbar
                                .make(etBookingCharge, firebaseError.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                        return;
                    }
                    Log.d(LOG_TAG, "Completed");
                    Log.d(LOG_TAG, firebase.getKey());
                    if (pDialog != null)
                        pDialog.cancel();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showProgressDialog() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading ...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private boolean isValidInput() {
        if (name.isEmpty() || email.isEmpty() || etLon.getText().toString().isEmpty() ||
                etLat.getText().toString().isEmpty() || etTotalCars.getText().toString().isEmpty()
                || etTotalBikes.getText().toString().isEmpty() ||
                etParkingCharge.getText().toString().isEmpty()) return false;
        else if (isBooking){
            if (etBookingCharge.getText().toString().isEmpty()) return false;
        }
        return true;
    }
    
}

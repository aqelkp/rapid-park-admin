package in.aqel.rapidpark_adminstrator.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import in.aqel.quickparksdk.Objects.Parking;
import in.aqel.quickparksdk.Utils.PrefUtils;
import in.aqel.rapidpark_adminstrator.R;

public class NewParkingActivity extends AppCompatActivity {

    Switch sBooking;
    boolean isBooking = false;
    EditText etName,  etLat, etLon, etTotalCars, etTotalBikes, etCurrentCars, etCurrentBikes;
    EditText etParkingCharge, etBookingCharge, etEmail;
    String name;
    int totalCars, totalBikes, pakringCharge;
    Double lat, lon;
    Context context = NewParkingActivity.this;

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
                etBookingCharge.setEnabled(isChecked);
            }
        });

        etEmail.setText(PrefUtils.getEmail(context));
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



            Parking parking = new Parking();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
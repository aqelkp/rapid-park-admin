package in.aqel.rapidpark_adminstrator.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.firebase.client.Firebase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import in.aqel.quickparksdk.Objects.Parking;
import in.aqel.quickparksdk.Utils.AppConstants;
import in.aqel.quickparksdk.Utils.PrefUtils;
import in.aqel.rapidpark_adminstrator.Activities.MainActivity;
import in.aqel.rapidpark_adminstrator.R;


public class UpdateDetailsFragment extends Fragment {

    Switch sBooking;
    boolean isBooking;
    EditText etName,  etLat, etLon, etTotalCars, etTotalBikes, etCurrentCars, etCurrentBikes;
    EditText etParkingCharge, etBookingCharge;
    String name;
    int totalCars, totalBikes, parkingCharge, bookingCharge;
    Double lat, lon;
    Context context ;
    ProgressDialog pDialog;
    private static String LOG_TAG = "UpdateFragmentDetails";
    Parking parking;
    Button bSave;
    Firebase ref;
    int currentBikes, currentCars;

    public UpdateDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_details, container, false);

        parking = ((MainActivity) getActivity()).getParking();
        Log.d(LOG_TAG, "Parking in Frag" + new Gson().toJson(parking) );

        name = parking.getName();
        currentBikes = parking.getCurrentBikes();
        currentCars = parking.getCurrentCars();
        totalBikes = parking.getTotalBikes();
        totalCars = parking.getTotalCars();
        parkingCharge = parking.getParkingCharge();
        bookingCharge = parking.getBookingCharge();
        ref = new Firebase(AppConstants.SERVER);
        context = getActivity();

        isBooking = parking.getBookingCharge() > 0;

        etName = (EditText) view.findViewById(R.id.etName);
        etTotalCars = (EditText) view.findViewById(R.id.etTotalCars);
        etTotalBikes = (EditText) view.findViewById(R.id.etTotalBikes);
        etParkingCharge = (EditText) view.findViewById(R.id.etParkingCharge);
        etBookingCharge = (EditText) view.findViewById(R.id.etBookingCharge);
        etCurrentBikes = (EditText) view.findViewById(R.id.etCurrentBikes);
        etCurrentCars = (EditText) view.findViewById(R.id.etCurrentCars);

        sBooking = (Switch) view.findViewById(R.id.sBooking);
        sBooking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isBooking = isChecked;
                etBookingCharge.setEnabled(isChecked);
            }
        });


        etName.setText(parking.getName());
        etCurrentCars.setText(parking.getCurrentCars() + "");
        etCurrentBikes.setText(parking.getCurrentBikes() + "");
        if (isBooking){
            etBookingCharge.setEnabled(true);
            etBookingCharge.setText(parking.getBookingCharge() + "");
        } else etBookingCharge.setEnabled(false);
        etTotalBikes.setText(parking.getTotalBikes() + "");
        etTotalCars.setText(parking.getTotalCars() + "");
        sBooking.setChecked(parking.isBooking());
        bSave = (Button) view.findViewById(R.id.bSave);
        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase parkingRef = ref.child("parkings").child(PrefUtils.getParkingId(context));
                Map<String, Object> park = new HashMap<String, Object>();
                if (!etName.getText().toString().equals(name))
                park.put("name", etName.getText().toString());
                park.put("booking", isBooking);
                if (isBooking){
                    park.put("bookingCharge", Integer.parseInt(etBookingCharge.getText().toString()));
                } else{
                    park.put("bookingCharge", 0);
                }
                if (currentBikes != Integer.parseInt(etCurrentBikes.getText().toString()))
                    park.put("currentBikes", Integer.parseInt(etCurrentBikes.getText().toString()) );
                if (currentCars != Integer.parseInt(etCurrentCars.getText().toString()))
                    park.put("currentCars", Integer.parseInt(etCurrentCars.getText().toString()) );
                park.put("parkingCharge", Integer.parseInt(etParkingCharge.getText().toString()));

                if (totalBikes != Integer.parseInt(etTotalBikes.getText().toString()))
                    park.put("totalBikes", Integer.parseInt(etTotalBikes.getText().toString()) );
                if (totalCars != Integer.parseInt(etTotalCars.getText().toString()))
                    park.put("totalCars", Integer.parseInt(etTotalCars.getText().toString()) );

                parkingRef.updateChildren(park);
            }
        });

        return view;
    }


}

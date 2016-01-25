package in.aqel.rapidpark_adminstrator.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import com.firebase.client.Firebase;

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
    Firebase ref;
    private static String LOG_TAG = "UpdateFragmentDetails";

    public UpdateDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_details, container, false);

        return view;
    }
}

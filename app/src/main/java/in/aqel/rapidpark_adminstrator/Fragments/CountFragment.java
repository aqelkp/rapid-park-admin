package in.aqel.rapidpark_adminstrator.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;

import in.aqel.quickparksdk.Objects.Parking;
import in.aqel.quickparksdk.Utils.AppConstants;
import in.aqel.quickparksdk.Utils.PrefUtils;
import in.aqel.rapidpark_adminstrator.Activities.MainActivity;
import in.aqel.rapidpark_adminstrator.R;

public class CountFragment extends Fragment implements View.OnClickListener{

    Context context;
    Parking parking;
    TextView tvTotalBike, tvOccupiedBike, tvEmptyBike;
    TextView tvTotalCar, tvOccupiedCar, tvEmptyCar;
    Button bPlusBike, bPlusCar;
    Button bMinusBike, bMinusCar;
    private static String LOG_TAG = "CountFragment";
    Firebase ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_count, container, false);
        context = getActivity();
        parking = ((MainActivity) getActivity()).getParking();
        ref = new Firebase(AppConstants.SERVER);

        setUpViews(view);
        setUpCounts(parking);

        return view;
    }

    public void updateParking(Parking parking){
        this.parking = parking;
        setUpCounts(parking);
        Log.d(LOG_TAG, "Update Parking method called");
    }

    private void setUpCounts(Parking parking) {
        tvTotalBike.setText("Total : " + parking.getTotalBikes());
        tvOccupiedBike.setText("Occupied : " + parking.getCurrentBikes());
        tvEmptyBike.setText("Free : " + (parking.getTotalBikes() - parking.getCurrentBikes()));

        tvTotalCar.setText("Total : " + parking.getTotalCars());
        tvOccupiedCar.setText("Occupied : " + parking.getCurrentCars());
        tvEmptyCar.setText("Free : " + (parking.getTotalCars() - parking.getCurrentCars()));

    }

    private void setUpViews(View view) {
        tvTotalBike = (TextView) view.findViewById(R.id.tvTotalBike);
        tvOccupiedBike = (TextView) view.findViewById(R.id.tvOccupiedBike);
        tvEmptyBike = (TextView) view.findViewById(R.id.tvEmptyBike);
        tvTotalCar = (TextView) view.findViewById(R.id.tvTotalCar);
        tvOccupiedCar = (TextView) view.findViewById(R.id.tvOccupiedCar);
        tvEmptyCar = (TextView) view.findViewById(R.id.tvEmptyCar);
        bPlusBike = (Button) view.findViewById(R.id.bPlusBike);
        bMinusBike = (Button) view.findViewById(R.id.bMinusBike);
        bMinusCar = (Button) view.findViewById(R.id.bMinusCar);
        bPlusCar = (Button) view.findViewById(R.id.bPlusCar);

        bPlusBike.setOnClickListener(this);
        bMinusBike.setOnClickListener(this);
        bMinusCar.setOnClickListener(this);
        bPlusCar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bPlusBike:
                changeCounts("currentBikes", 1);
                break;
            case R.id.bMinusBike:
                changeCounts("currentBikes", -1);
                break;
            case R.id.bMinusCar:
                changeCounts("currentCars", -1);
                break;
            case R.id.bPlusCar:
                changeCounts("currentCars", 1);
                break;

        }
    }

    private void changeCounts(String mode, final int value) {
        Firebase countRef = ref.child("parkings").child(PrefUtils.getParkingId(getActivity()))
                .child(mode);
        countRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + value);
                }

                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                //This method will be called once with the results of the transaction.
                Log.d(LOG_TAG, "Current data is:" + currentData.getValue().toString());
                if (firebaseError != null){
                    Snackbar
                            .make(tvEmptyBike, firebaseError.getMessage(), Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });

    }

}

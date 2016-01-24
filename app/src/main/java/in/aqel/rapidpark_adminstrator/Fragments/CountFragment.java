package in.aqel.rapidpark_adminstrator.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import in.aqel.quickparksdk.Objects.Parking;
import in.aqel.rapidpark_adminstrator.Activities.MainActivity;
import in.aqel.rapidpark_adminstrator.R;

public class CountFragment extends Fragment {

    Context context;
    Parking parking;
    TextView tvTotalBike, tvOccupiedBike, tvEmptyBike;
    TextView tvTotalCar, tvOccupiedCar, tvEmptyCar;
    Button bPlusBike, bPlusCar;

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
        tvTotalBike = (TextView) view.findViewById(R.id.tvTotalBike);
        tvOccupiedBike = (TextView) view.findViewById(R.id.tvOccupiedBike);
        tvEmptyBike = (TextView) view.findViewById(R.id.tvEmptyBike);
        tvTotalCar = (TextView) view.findViewById(R.id.tvTotalCar);
        tvOccupiedCar = (TextView) view.findViewById(R.id.tvOccupiedCar);
        tvEmptyCar = (TextView) view.findViewById(R.id.tvEmptyCar);
        bPlusBike = (Button) view.findViewById(R.id.bPlusBike);
        bPlusCar = (Button) view.findViewById(R.id.bPlusCar);
        return view;
    }

}

package com.example.sportgo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class StopSportFragment extends Fragment {
    private fragment_stop_sport listener;
    private Button buttonStoptSport;

    public interface fragment_stop_sport{
        void onInputASent(CharSequence input);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stop_sport, container, false);
        Button btnStopSportFragment = (Button)view.findViewById(R.id.button_stop_sport);

        btnStopSportFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction frTwo= getFragmentManager().beginTransaction();
                frTwo.replace(R.id.fragment_container,new SportFragment());
                frTwo.commit();
            }
        });

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}

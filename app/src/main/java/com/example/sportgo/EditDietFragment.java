package com.example.sportgo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class EditDietFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet_edit, container, false);
        Button btnSaveDietFragment = (Button)view.findViewById(R.id.button_save_profile);

        btnSaveDietFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction drTwo= getFragmentManager().beginTransaction();
                drTwo.replace(R.id.fragment_container,new DietFragment());
                drTwo.commit();
            }
        });
        return view;
    }
}

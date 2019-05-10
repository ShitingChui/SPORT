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

public class DietFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diet, container, false);

        Button btnEditDietFragment = (Button)view.findViewById(R.id.button_edit_diet);

        btnEditDietFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction dr= getFragmentManager().beginTransaction();
                dr.replace(R.id.fragment_container,new EditProfileFragment());
                dr.commit();
            }
        });

        return view;
    }

    public void btn_edit_diet(View view) {

    }
}
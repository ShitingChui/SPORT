package com.example.sportgo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SportFragment extends Fragment {


//    private SportFragmentListener sportFragmentListener;
    private TextView viewExerciseTime;

//    public interface SportFragmentListener {
//        void onInputBSent(CharSequence input, TextView textView);
//    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("onStart", "onStart: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("onCreateView", "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_sport, container, false);

        Button btnStartSportFragment = (Button)view.findViewById(R.id.button_start_sport);
        viewExerciseTime = (TextView)view.findViewById(R.id.viewExerciseTime);

//        Bundle args = getArguments();
//        if (args != null) {
//            String ExerciseTime = args.getString("ExerciseTime");
//            viewExerciseTime.setText(ExerciseTime);
//        }

        btnStartSportFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentTransaction fr= getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new StopSportFragment());
                fr.commit();

//                CharSequence input = "";
//                sportFragmentListener.onInputBSent(input, viewExerciseTime);
            }
        });

        return view;
    }

    public void updateExerciseTime(CharSequence newText) {
        viewExerciseTime.setText("運動時間：" + newText);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof SportFragmentListener) {
//            sportFragmentListener = (SportFragmentListener) context;
//        } else {
//            throw new RuntimeException(context.toString() + "must implement SportFragmentListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        sportFragmentListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void btn_start_sport(View view) {

    }
}

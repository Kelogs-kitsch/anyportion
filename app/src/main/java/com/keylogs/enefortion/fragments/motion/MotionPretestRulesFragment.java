package com.keylogs.enefortion.fragments.motion;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.keylogs.enefortion.R;
import com.keylogs.enefortion.activity.investigatingmotion.LessonActivity1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MotionPretestRulesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotionPretestRulesFragment extends Fragment {

    private Button startButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MotionPretestRulesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MotionPretestRulesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MotionPretestRulesFragment newInstance(String param1, String param2) {
        MotionPretestRulesFragment fragment = new MotionPretestRulesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_motion_pretest_rules, container, false);
        startButton = view.findViewById(R.id.startButton);

//        // Add a callback for the back button press
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                // Send a broadcast to handle the back press
//                Intent intent = new Intent("com.example.ACTION_BACK_PRESS");
//                requireActivity().sendBroadcast(intent);
//            }
//        });

        startButton.setOnClickListener(v -> {
            showPretestRuleFragment();
        });
        return view;
    }
    private void showPretestRuleFragment() {
        MotionPretestFragment pretestFragment = MotionPretestFragment.newInstance("param1Value", "param2Value");
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.pretestMotionContainer, pretestFragment)  // Ensure that fragment_container is correct
                .commit();
    }

}
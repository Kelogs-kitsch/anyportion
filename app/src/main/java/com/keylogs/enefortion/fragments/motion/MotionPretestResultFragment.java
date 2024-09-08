package com.keylogs.enefortion.fragments.motion;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.keylogs.enefortion.R;
import com.keylogs.enefortion.activity.investigatingmotion.LessonActivity1;
import com.keylogs.enefortion.fragments.motion.distance.DistancePage1Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MotionPretestResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotionPretestResultFragment extends Fragment {

    private Button startTopicButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MotionPretestResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment MotionPretestResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MotionPretestResultFragment newInstance(int score) {
        MotionPretestResultFragment fragment = new MotionPretestResultFragment();
        Bundle args = new Bundle();
        args.putInt("score", score);  // Use a key for the score
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
        View view = inflater.inflate(R.layout.fragment_motion_pretest_result, container, false);

        if (getArguments() != null) {
            int score = getArguments().getInt("score");  // Retrieve the score using the same key
            TextView scoreTextView = view.findViewById(R.id.scoreTextView);
            scoreTextView.setText("Your score: " + score);
        }

        startTopicButton = view.findViewById(R.id.startSubtopicButton);
        startTopicButton.setOnClickListener(v -> {
            showLesson1();
        });


        return view;
    }
    private void showLesson1() {
        Intent intent = new Intent(getActivity(), LessonActivity1.class);
        intent.putExtra("START_TOPIC_CLICKED", true);
        startActivity(intent);
        getActivity().finish();
    }

}
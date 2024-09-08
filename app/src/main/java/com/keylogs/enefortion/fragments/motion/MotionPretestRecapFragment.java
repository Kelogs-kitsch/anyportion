package com.keylogs.enefortion.fragments.motion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.keylogs.enefortion.R;
import com.keylogs.enefortion.model.EnefortionDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MotionPretestRecapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotionPretestRecapFragment extends Fragment {

    private static final String ARG_SCORE = "score";
    private int score;
    private EnefortionDatabase dbHelper;

    public MotionPretestRecapFragment() {
        // Required empty public constructor
    }

    public static MotionPretestRecapFragment newInstance(int score) {
        MotionPretestRecapFragment fragment = new MotionPretestRecapFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCORE, score);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getInt(ARG_SCORE);
        }
        dbHelper = new EnefortionDatabase(getActivity());
    }

    private TextView recapTextView;
    private Button submitButton, editAnswersButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion_pretest_recap, container, false);

        recapTextView = view.findViewById(R.id.recapTextView);
        submitButton = view.findViewById(R.id.submitButton);
        editAnswersButton = view.findViewById(R.id.editAnswersButton);

//        if (getArguments() != null) {
//            recapTextView.setText("Your score: " + score);
//        }

        submitButton.setOnClickListener(v -> submitScore());
        editAnswersButton.setOnClickListener(v -> editAnswers());

        return view;
    }

    private void submitScore() {
        String username = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                .getString("username", "");

        if (username.isEmpty()) {
            Toast.makeText(getContext(), "No user logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert score into database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO UserScore (username, score) VALUES (?, ?)", new Object[]{username, score});

        Toast.makeText(getContext(), "Score submitted!", Toast.LENGTH_SHORT).show();

        MotionPretestResultFragment resultFragment = MotionPretestResultFragment.newInstance(score);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.pretestMotionContainer, resultFragment)
                .addToBackStack(null)
                .commit();
    }

    private void editAnswers() {
        // Go back to the QuizFragment to allow answer editing
        getActivity().getSupportFragmentManager().popBackStack();
    }

    // Uncomment if you have a LeaderboardFragment and want to display it after submitting
    // private void showLeaderboardFragment() {
    //     LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
    //     getActivity().getSupportFragmentManager().beginTransaction()
    //             .replace(R.id.fragment_container, leaderboardFragment)
    //             .commit();
    // }
}

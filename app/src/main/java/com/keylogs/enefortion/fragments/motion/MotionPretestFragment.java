package com.keylogs.enefortion.fragments.motion;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.keylogs.enefortion.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MotionPretestFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView questionTextView;
    private RadioGroup answersRadioGroup;
    private Button nextButton;
    private int currentQuestionIndex = 0;
    private int score = 0;
    // Add the newInstance method
    public static MotionPretestFragment newInstance(String param1, String param2) {
        MotionPretestFragment fragment = new MotionPretestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    // Define the questions array with 15 questions
    private String[] questions = {
            "Question 1 text...",
            "Question 2 text...",
            "Question 3 text...",
            "Question 4 text...",
            "Question 5 text...",
            "Question 6 text...",
            "Question 7 text...",
            "Question 8 text...",
            "Question 9 text...",
            "Question 10 text...",
            "Question 11 text...",
            "Question 12 text...",
            "Question 13 text...",
            "Question 14 text...",
            "Question 15 text..."
    };

    // Define the answers array with corresponding answers for each question
    private String[][] answers = {
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"},
            {"Answer 1", "Answer 2", "Answer 3"}
    };

    // Define the correctAnswers array with indices for correct answers (0-based index)
    private int[] correctAnswers = {0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motion_pretest, container, false);

        questionTextView = view.findViewById(R.id.questionTextView);
        answersRadioGroup = view.findViewById(R.id.answersRadioGroup);
        nextButton = view.findViewById(R.id.nextButton);

        shuffleQuestions();
        loadQuestion();

        nextButton.setOnClickListener(v -> {
            int selectedId = answersRadioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                int selectedAnswerIndex = answersRadioGroup.indexOfChild(view.findViewById(selectedId));
                if (selectedAnswerIndex == correctAnswers[currentQuestionIndex]) {
                    score++;
                }

                // Check if it's the last question
                if (currentQuestionIndex < questions.length - 1) {
                    currentQuestionIndex++;
                    loadQuestion();
                } else {
                    // End of quiz - show the recap fragment
                    showRecapFragment();
                }
            } else {
                Toast.makeText(getContext(), "Please select an answer.", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void loadQuestion() {
        questionTextView.setText(questions[currentQuestionIndex]);
        answersRadioGroup.clearCheck();

        int childCount = answersRadioGroup.getChildCount();
        int answerCount = answers[currentQuestionIndex].length;

        for (int i = 0; i < answerCount && i < childCount; i++) {
            RadioButton radioButton = (RadioButton) answersRadioGroup.getChildAt(i);
            if (radioButton != null) {
                radioButton.setText(answers[currentQuestionIndex][i]);
            } else {
                Log.e("MotionQuizFragment", "RadioButton at index " + i + " is null.");
            }
        }
    }
    private void shuffleQuestions() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        String[] shuffledQuestions = new String[questions.length];
        String[][] shuffledAnswers = new String[questions.length][];
        int[] shuffledCorrectAnswers = new int[questions.length];

        for (int i = 0; i < indices.size(); i++) {
            int index = indices.get(i);
            shuffledQuestions[i] = questions[index];
            shuffledAnswers[i] = answers[index];
            shuffledCorrectAnswers[i] = correctAnswers[index];
        }

        questions = shuffledQuestions;
        answers = shuffledAnswers;
        correctAnswers = shuffledCorrectAnswers;
    }

    private void showRecapFragment() {
        MotionPretestRecapFragment recapFragment = MotionPretestRecapFragment.newInstance(score);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.pretestMotionContainer, recapFragment)  // Ensure that fragment_container is correct
                .commit();
    }
}
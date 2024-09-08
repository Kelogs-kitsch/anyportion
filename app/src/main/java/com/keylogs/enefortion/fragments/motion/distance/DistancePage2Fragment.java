package com.keylogs.enefortion.fragments.motion.distance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.keylogs.enefortion.R;
import com.keylogs.enefortion.activity.investigatingmotion.DistanceActivity;
import com.keylogs.enefortion.model.EnefortionDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DistancePage2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DistancePage2Fragment extends Fragment {

    private EnefortionDatabase dbHelper;
    private SQLiteDatabase database;
    private String loggedInUsername;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DistancePage2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Page2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DistancePage2Fragment newInstance(String param1, String param2) {
        DistancePage2Fragment fragment = new DistancePage2Fragment();
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
        View view = inflater.inflate(R.layout.fragment_distance_page2, container, false);

        Button nextButton = view.findViewById(R.id.nextButton2);
        Button previousButton = view.findViewById(R.id.previousButton2);

        dbHelper = new EnefortionDatabase(requireContext());
        database = dbHelper.getWritableDatabase();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof DistanceActivity) {
                    DistanceActivity activity = (DistanceActivity) getActivity();
                    activity.showFragment(new DistancePage3Fragment());
                }

                if (loggedInUsername != null) {
                    addProgressForUser(loggedInUsername);
                }
            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof DistanceActivity) {
                    DistanceActivity activity = (DistanceActivity) getActivity();
                    activity.showFragment(new DistancePage1Fragment());
                }
            }
        });
        return view;
    }
    private void addProgressForUser(String username) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = null;
                try {
                    cursor = database.query(
                            "UserProgress",
                            new String[] { "progress" },
                            "username = ?",
                            new String[] { username },
                            null,
                            null,
                            null
                    );

                    int progress = 1; // Default to 1 if no record is found
                    int progressColumnIndex = cursor.getColumnIndex("progress");

                    if (progressColumnIndex != -1 && cursor.moveToFirst()) {
                        progress = cursor.getInt(progressColumnIndex) + 1;
                    }

                    ContentValues values = new ContentValues();
                    values.put("username", username);
                    values.put("progress", progress);
                    database.replace("UserProgress", null, values);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(requireContext(), "Progress updated!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
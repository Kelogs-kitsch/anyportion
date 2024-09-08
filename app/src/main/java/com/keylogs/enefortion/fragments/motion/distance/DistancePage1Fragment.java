package com.keylogs.enefortion.fragments.motion.distance;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.keylogs.enefortion.R;
import com.keylogs.enefortion.activity.investigatingmotion.DistanceActivity;
import com.keylogs.enefortion.model.EnefortionDatabase;

public class DistancePage1Fragment extends Fragment {

    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private EnefortionDatabase dbHelper;
    private SQLiteDatabase database;
    private String loggedInUsername;
    private VideoView videoView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DistancePage1Fragment() {
        // Required empty public constructor
    }

    public static DistancePage1Fragment newInstance(String param1, String param2) {
        DistancePage1Fragment fragment = new DistancePage1Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_distance_page1, container, false);

        Button nextButton = view.findViewById(R.id.nextButton);
        VideoView videoView = view.findViewById(R.id.playerView);

        // Set up the video URI and play the video
        Uri videoUri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.whatisphysics);
        videoView.setVideoURI(videoUri);
        videoView.start();
//        playerView = view.findViewById(R.id.playerView);
//        exoPlayer = new ExoPlayer.Builder(getContext()).build();
//        playerView.setPlayer(exoPlayer);
//
//        Uri videoUri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.whatisphysics);
//        MediaItem mediaItem = MediaItem.fromUri(videoUri);
//        exoPlayer.setMediaItem(mediaItem);
//        exoPlayer.prepare();
//        exoPlayer.play();

        dbHelper = new EnefortionDatabase(requireContext());
        database = dbHelper.getWritableDatabase();

        // Get the logged-in user's username (assuming it's stored in shared preferences)
        SharedPreferences prefs = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        loggedInUsername = prefs.getString("username", null);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof DistanceActivity) {
                    DistanceActivity activity = (DistanceActivity) getActivity();

                    // Create a new fragment instance
                    DistancePage2Fragment newFragment = new DistancePage2Fragment();

                    // Begin the fragment transaction
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Replace the current fragment with the new fragment
                    fragmentTransaction.replace(R.id.distanceMain, newFragment);

                    // Optionally, add the transaction to the back stack
                    // fragmentTransaction.addToBackStack(null);

                    // Commit the transaction
                    fragmentTransaction.commit();
                }

                // Add progress for the logged-in user
                if (loggedInUsername != null) {
                    addProgressForUser(loggedInUsername);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
        }
        if (database != null) {
            database.close();
        }
    }
}

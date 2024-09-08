package com.keylogs.enefortion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.keylogs.enefortion.activity.investigatingmotion.LessonActivity1;

public class VideoPlayerViewActivity extends AppCompatActivity {
    private ImageView forwardbtn, replaybtn, playpuasebtn, fullscreenbtn, backbtn, speedbtn;
    private TextView currenttime, totaltime, exo_title;
    private PlayerView videoplayerView;
    private ExoPlayer videoexoplayer;
    private boolean isFullScreen = false;
    private Handler handler;
    private boolean isSeeking = false;
    private String[] speedOption = {"0.5x", "0.75x", "1.0x", "1.5x", "2.0x"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video_player_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        videoplayerView = findViewById(R.id.main);
        videoexoplayer = new ExoPlayer.Builder(this).build();
        videoplayerView.setPlayer(videoexoplayer);

        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.whatisphysics);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
        videoexoplayer.setMediaItem(mediaItem);
        videoexoplayer.prepare();
        videoexoplayer.setPlayWhenReady(true);
        videoexoplayer.play();

        forwardbtn = videoplayerView.findViewById(R.id.fastForwardButton);
        replaybtn = videoplayerView.findViewById(R.id.rewindButton);
        playpuasebtn = videoplayerView.findViewById(R.id.playPauseButton);
        fullscreenbtn = videoplayerView.findViewById(R.id.fullScreenButton);
        speedbtn = videoplayerView.findViewById(R.id.slowDownButton);
        currenttime = videoplayerView.findViewById(R.id.exo_position);
        totaltime = videoplayerView.findViewById(R.id.exo_duration2);
//        exo_title = videoplayerView.findViewById(R.id.t);
        backbtn = videoplayerView.findViewById(R.id.backButton);

        String Title = "Distance Tutorial";
        exo_title.setText(Title);

        playpuasebtn.setOnClickListener( v -> {
            videoexoplayer.setPlayWhenReady(!videoexoplayer.getPlayWhenReady());
            playpuasebtn.setImageResource(Boolean.TRUE.equals(videoexoplayer.getPlayWhenReady()) ? R.drawable.baseline_pause_24 : R.drawable.baseline_play_arrow_24);
        });
        speedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoPlayerViewActivity.this);
                builder.setTitle("Playback Speed");
                builder.setItems(speedOption, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            PlaybackParameters param = new PlaybackParameters(0.5f);
                            videoexoplayer.setPlaybackParameters(param);
                        }   if (which == 1 ){
                            PlaybackParameters param = new PlaybackParameters(0.75f);
                            videoexoplayer.setPlaybackParameters(param);
                        }   if (which == 2 ){
                            PlaybackParameters param = new PlaybackParameters(1f);
                            videoexoplayer.setPlaybackParameters(param);
                        }   if (which == 3 ){
                            PlaybackParameters param = new PlaybackParameters(1.5f);
                            videoexoplayer.setPlaybackParameters(param);
                        }   if (which == 4 ){
                            PlaybackParameters param = new PlaybackParameters(2f);
                            videoexoplayer.setPlaybackParameters(param);
                        }
                    }
                });
                builder.show();
            }
        });
        forwardbtn.setOnClickListener( v ->{
            videoexoplayer.seekTo(videoexoplayer.getCurrentPosition() +10000);
        });

        replaybtn.setOnClickListener( v ->{
            long num = videoexoplayer.getCurrentPosition() - 10000;
            if (num <0) {
                videoexoplayer.seekTo(0);
            } else
                videoexoplayer.seekTo(videoexoplayer.getCurrentPosition() -10000);
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoPlayerViewActivity.this, LessonActivity1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        fullscreenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isFullScreen) {
                    exo_title.setVisibility(View.INVISIBLE);
                    backbtn.setVisibility(View.VISIBLE);
                    fullscreenbtn.setImageDrawable(ContextCompat.getDrawable(VideoPlayerViewActivity.this, R.drawable.baseline_fullscreen_24));
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().show();
                    }
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) videoplayerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = (int) (215 * getApplicationContext().getResources().getDisplayMetrics().density);
                    videoplayerView.setLayoutParams(params);
                    isFullScreen = false;

                } else {
                    exo_title.setVisibility(View.VISIBLE);
                    backbtn.setVisibility(View.INVISIBLE);
                    fullscreenbtn.setImageDrawable(ContextCompat.getDrawable(VideoPlayerViewActivity.this, R.drawable.baseline_fullscreen_exit_24));
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().hide();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) videoplayerView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                    videoplayerView.setLayoutParams(params);
                    isFullScreen = true;
                }
            }
        });
        videoplayerView.setControllerVisibilityListener(new PlayerView.ControllerVisibilityListener() {
            @Override
            public void onVisibilityChanged(int visibility) {
                if (isFullScreen){
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    isFullScreen = true;
                }
            }
        });
    }
    @Override
    protected void onResume(){
        videoexoplayer.play();
        super.onResume();
    }

    @Override
    protected void onPause(){
        videoexoplayer.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        videoexoplayer.release();
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed(); // Calls the default back navigation (not needed if you're handling it yourself)
        Intent intent = new Intent(VideoPlayerViewActivity.this, LessonActivity1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
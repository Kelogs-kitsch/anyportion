package com.keylogs.enefortion.activity.investigatingmotion;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.keylogs.enefortion.R;
import com.keylogs.enefortion.fragments.motion.MotionPretestRecapFragment;

public class MotionPretestRecapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_motion_pretest_recap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pretestMotionContainer), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pretestMotionContainer, new MotionPretestRecapFragment())
                    .commit();
        }
    }
}
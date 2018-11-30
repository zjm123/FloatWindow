package com.loveupj.floatwindow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShow = findViewById(R.id.btn_show);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFloatingWindow();
            }
        });

        if (FloatingService.isStarted) {
            stopService(new Intent(MainActivity.this, FloatingService.class));
            FloatingService.isStarted = false;
        }

    }

    private void openFloatingWindow() {
        if (FloatingService.isStarted) {
            return;
        }

        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 3002);
        } else {
            startService(new Intent(MainActivity.this, FloatingService.class));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3002) {
            if (Settings.canDrawOverlays(this)) {
                startService(new Intent(MainActivity.this, FloatingService.class));
            }
        }
    }
}

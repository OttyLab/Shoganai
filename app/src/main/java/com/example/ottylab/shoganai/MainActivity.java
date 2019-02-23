package com.example.ottylab.shoganai;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SHOGANAI";
    private static final String LABEL = "しょうがない";
    private static final int RESIZE_COUNT = 10;
    private static final double RESIZE_RETIO = 1.5;

    private int counter = 0;
    private Button buttonShoganai;
    private Random random = new Random();
    private MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonShoganai = (Button) findViewById(R.id.buttonShoganai);
        buttonShoganai.setText(getLabel(counter));
        buttonShoganai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = getExternalFilesDir(null).getPath();
                File[] files = new File(path).listFiles();
                File file = files[random.nextInt(files.length)];
                try {
                    synchronized (mp) {
                        if (mp.isPlaying()) {
                            mp.stop();
                            mp.release();
                        }

                        mp = new MediaPlayer();
                        mp.setDataSource(file.getPath());
                        mp.prepare();
                        mp.start();

                        updateButton(++counter);
                    }
                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }
        });
    }

    private String getLabel(int counter) {
        return String.valueOf(counter) + LABEL;
    }

    private void updateButton(int counter) {
        buttonShoganai.setText(getLabel(counter));
        if (counter % RESIZE_COUNT == 0) {
            float size = buttonShoganai.getTextSize();
            buttonShoganai.setTextSize((float)(size * RESIZE_RETIO));
        }
    }
}

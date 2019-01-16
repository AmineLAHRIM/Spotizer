package com.materialviewinc.playband.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.materialviewinc.playband.R;

public class HomeActivity extends AppCompatActivity {
    Button btn_getStarted;
    Button btn_signin;
    Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        listener();
    }

    private void init() {
        btn_getStarted=findViewById(R.id.btn_getStarted);
        btn_signin=findViewById(R.id.btn_signin);
        btn_signup=findViewById(R.id.btn_signup);
    }

    private void listener() {
        btn_getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent AlbumsIntent = new Intent(HomeActivity.this, AlbumListActivity.class);
                startActivity(AlbumsIntent);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}

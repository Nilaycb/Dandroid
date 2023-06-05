package xyz.nilay.dandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView showName;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        showName = findViewById(R.id.showName);

        username = getIntent().getStringExtra("username").toString();
        showName.setText("Welcome "+username);
    }
}
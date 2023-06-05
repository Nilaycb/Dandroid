package xyz.nilay.dandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText inputName;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName=findViewById(R.id.inputName);
        submitButton=findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputName.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "No Input Given!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    intent.putExtra("username", inputName.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
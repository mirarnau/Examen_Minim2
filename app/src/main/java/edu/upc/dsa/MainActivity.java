package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView userName;
    Button sendButton;
    String usernameSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPrefer = getSharedPreferences("user", Context.MODE_PRIVATE);
        this.usernameSaved = sharedPrefer.getString("USER", null);
        this.userName = findViewById(R.id.nameTextID);
        this.userName.setText(this.usernameSaved);

        this.sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER", user);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), InfoUser.class);
                startActivity(intent);
            }
        });

    }






}
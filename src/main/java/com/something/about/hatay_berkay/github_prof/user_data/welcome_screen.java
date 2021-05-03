package com.something.about.hatay_berkay.github_prof.user_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.something.about.hatay_berkay.github_prof.R;

public class welcome_screen extends AppCompatActivity {
Button next_page_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        next_page_button = (Button) findViewById(R.id.next_page_button);


        next_page_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user_data_getting_intent = new Intent(getApplicationContext(), user_data_getting.class);
                startActivity(user_data_getting_intent);
                welcome_screen.this.finish();

            }
        });


    }
}
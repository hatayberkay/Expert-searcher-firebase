package com.something.about.hatay_berkay.github_prof.being_expert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.something.about.hatay_berkay.github_prof.R;
import com.something.about.hatay_berkay.github_prof.bottom_bar;

import java.util.HashMap;

public class become_expert extends AppCompatActivity {
private Spinner talent_first_spinner , talent_second_spinner , talent_third_spinner;
private Button save_button;

    private static final String[] items={"" ,"Web Software","Mobile Application","E-Commerce","Domain & Hosting "
            ,"Technological support","Mechatronic Engineering","Mechanical Engineering"
            ,"Eye diseases","Neurology","Mental health",
            "Basic Health knowledge" , "Fashion design" , "Industrial design","Logo design" };

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_expert);

        definations();



        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,  items);
        talent_first_spinner.setAdapter(adapter);
        talent_second_spinner.setAdapter(adapter);
        talent_third_spinner.setAdapter(adapter);


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String talent_first_spinner_string = talent_first_spinner.getSelectedItem().toString();


                if (!talent_first_spinner_string.isEmpty() && !talent_first_spinner_string.equals("")) {

                    String talent_second_spinner_string = talent_second_spinner.getSelectedItem().toString();
                    String talent_third_spinner_string = talent_third_spinner.getSelectedItem().toString();



                    String[] separated = firebaseUser.getEmail().toString().split("@");
                    final String firebase_usernames = separated[0] ;

                    databaseReference = firebaseDatabase.getReference("users/" + firebase_usernames + "/infos/state");
                    HashMap kimlik_ekle = new HashMap();

                    kimlik_ekle.put("state","expert");


                    databaseReference.updateChildren(kimlik_ekle);

                    databaseReference = firebaseDatabase.getReference("users/" + firebase_usernames + "/infos/" );
                    HashMap yetenek_ekle = new HashMap();

                    yetenek_ekle.put("talent_first", talent_first_spinner_string);
                    yetenek_ekle.put("talent_second", talent_second_spinner_string);
                    yetenek_ekle.put("talent_third", talent_third_spinner_string);



                    databaseReference.updateChildren(yetenek_ekle);

                    Intent intent = new Intent(getApplicationContext(), bottom_bar.class);
                    startActivity(intent);
                    become_expert.this.finish();


                }
                }
        });

    }

    private void definations() {

        save_button = (Button)findViewById(R.id.save_button);

        talent_first_spinner = (Spinner)    findViewById(R.id.talent_first_spinner);
        talent_second_spinner = (Spinner)   findViewById(R.id.talent_second_spinner);
        talent_third_spinner = (Spinner)    findViewById(R.id.talent_third_spinner);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}
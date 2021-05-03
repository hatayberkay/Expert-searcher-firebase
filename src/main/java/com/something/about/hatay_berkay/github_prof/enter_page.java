package com.something.about.hatay_berkay.github_prof;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.something.about.hatay_berkay.github_prof.Auth.registration;
import com.something.about.hatay_berkay.github_prof.Auth.reset_password;
import com.something.about.hatay_berkay.github_prof.user_data.welcome_screen;

public class enter_page extends AppCompatActivity {

    private FirebaseAuth mAuth;

 private   EditText e_mail_edittext , password_enter_edittext ;
    private Button get_inside;
    private TextView registration_textview , reset_textview;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
 private static final String TAG = "activity_konusu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        definations();

        //hatayberkayis@gmail.com
        //hatayberkaykedi@gmail.com







        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        if (firebaseAuth.getCurrentUser() != null){


            String[] separated = e_mail_edittext.getText().toString().split("@");
            final String firebase_usernames = separated[0];

            // TODO: 29.04.2021 Check Users info are filled up.

            databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "name/");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name_string = (String) snapshot.getValue();


                    if (name_string != null && !name_string.equals("")){
                        Intent bottom_bar_intent = new Intent(getApplicationContext(),bottom_bar.class);
                        startActivity(bottom_bar_intent);
                        enter_page.this.finish();

                    }
                    else if (name_string != null && name_string.equals("")){

                        Intent welcome_screen_intent = new Intent(getApplicationContext(), welcome_screen.class);
                        startActivity(welcome_screen_intent);
                        enter_page.this.finish();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Intent uye_ol_intent = new Intent(getApplicationContext(),bottom_bar.class);
            startActivity(uye_ol_intent);
            enter_page.this.finish();

        }

        // hatayberkay1234@gmail.com
        // hatayberkayis@gmail.com
        // hatayberkaykedi@gmail.com

        get_inside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inside_to_bottom();
            }

            private void inside_to_bottom() {
                String e_mail_edittext_string = e_mail_edittext.getText().toString();
                String password_enter_edittext_string = password_enter_edittext.getText().toString();


                if (!e_mail_edittext_string.isEmpty() && !password_enter_edittext_string.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(e_mail_edittext_string,password_enter_edittext_string).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                String[] separated = e_mail_edittext.getText().toString().split("@");
                                final String firebase_usernames = separated[0];

                                // TODO: 29.04.2021 Check Users info are filled up.

                                databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "name/");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String name_string = (String) snapshot.getValue();


                                        if (name_string != null && !name_string.equals("")){
                                            Intent bottom_bar_intent = new Intent(getApplicationContext(),bottom_bar.class);
                                            startActivity(bottom_bar_intent);
                                            enter_page.this.finish();

                                        }
                                        else {

                                            Intent welcome_screen_intent = new Intent(getApplicationContext(), welcome_screen.class);
                                            startActivity(welcome_screen_intent);
                                            enter_page.this.finish();

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                                Intent bottom_bar_intent = new Intent(getApplicationContext(),bottom_bar.class);
                                startActivity(bottom_bar_intent);
                                enter_page.this.finish();



                            }


                        }
                    });



                }






            }
        });



        registration_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_to_app();

            }

            private void register_to_app() {

                Intent registration_intent = new Intent(getApplicationContext(), registration.class);
                startActivity(registration_intent);
                enter_page.this.finish();
            }
        });


        reset_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_to_your_password();

            }

            private void reset_to_your_password() {
                Intent intent = new Intent(getApplicationContext(), reset_password.class);
                startActivity(intent);
                enter_page.this.finish();
            }
        });

    }

    private void definations() {


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // Edittexts
        e_mail_edittext = (EditText)findViewById(R.id.e_mail_edittext);
        password_enter_edittext = (EditText)findViewById(R.id.password_enter_edittext);

        // Button
         get_inside = (Button) findViewById(R.id.get_inside);

        // Textviews
        registration_textview = (TextView) findViewById(R.id.registration_textview);
        reset_textview = (TextView) findViewById(R.id.reset_textview);

    }
}
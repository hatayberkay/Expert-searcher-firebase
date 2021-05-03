package com.something.about.hatay_berkay.github_prof.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.something.about.hatay_berkay.github_prof.R;
import com.something.about.hatay_berkay.github_prof.enter_page;

import java.util.HashMap;

public class registration extends AppCompatActivity {
    private EditText password_edittext , e_mail_address_edittext, password_again_edittext ;
    private Button save_button;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        definitions();

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String e_mail_address_edittext_string = e_mail_address_edittext.getText().toString();
                final String password_edittext_string = password_edittext.getText().toString();
                final String password_again_edittext_string = password_again_edittext.getText().toString();


                registations_jobs(e_mail_address_edittext_string , password_edittext_string , password_again_edittext_string);




            }

            private void registations_jobs(String e_mail_address_edittext_string, String password_edittext_string, String password_again_edittext_string) {


                if (password_edittext_string.equals(password_again_edittext_string)){
                    firebaseAuth.createUserWithEmailAndPassword(e_mail_address_edittext_string,password_edittext_string).addOnCompleteListener(registration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()){






                                String[] separated = e_mail_address_edittext_string.split("@");
                                final String firebase_usernames = separated[0] ;


                                databaseReference = firebaseDatabase.getReference("users/" + firebase_usernames + "/infos");


                                HashMap kullanıcı_kaydı_hashmap = new HashMap();

                                kullanıcı_kaydı_hashmap.put("E-mail", firebase_usernames);
                                kullanıcı_kaydı_hashmap.put("name", "");
                                kullanıcı_kaydı_hashmap.put("surname", "");
                                kullanıcı_kaydı_hashmap.put("user_name", "");
                                kullanıcı_kaydı_hashmap.put("image", "");
                                kullanıcı_kaydı_hashmap.put("job", "");
                                kullanıcı_kaydı_hashmap.put("city", "");
                                kullanıcı_kaydı_hashmap.put("education", "");
                                kullanıcı_kaydı_hashmap.put("about", "");

                                databaseReference.updateChildren(kullanıcı_kaydı_hashmap);

                                Toast.makeText(registration.this, "Succesfully You get it.", Toast.LENGTH_SHORT).show();


                                Intent i = new Intent(getApplicationContext(), enter_page.class);
                                startActivity(i);
                                registration.this.finish();


                            }else {
                                Toast.makeText(registration.this, "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
                }else {

                    Toast.makeText(registration.this, "Passwords are not equal.", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    private void definitions() {
        password_edittext = (EditText)findViewById(R.id.password_edittext);
        e_mail_address_edittext = (EditText)findViewById(R.id.e_mail_address_edittext);
        password_again_edittext = (EditText)findViewById(R.id.password_again_edittext);
        save_button = (Button)findViewById(R.id.save_button);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), enter_page.class);
        startActivity(intent);
        registration.this.finish();
    }
}


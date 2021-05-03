package com.something.about.hatay_berkay.github_prof;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.something.about.hatay_berkay.github_prof.model.CustomAdapter;
import com.something.about.hatay_berkay.github_prof.model.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class message_square extends AppCompatActivity {
private ListView mesajlaşma_listview;
private EditText editText_mesaj_yaz;
private Button gonder_buttons;

    private ArrayList<Message> chatLists = new ArrayList<>();
        DatabaseReference databaseReference;
        DatabaseReference databaseReferences;
         FirebaseDatabase firebaseDatabase;
          private FirebaseAuth firebaseAuth;
              private FirebaseUser firebaseUser;
    private CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_square);

        definations();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("");
        databaseReferences = firebaseDatabase.getReference("");

        Bundle extras = getIntent().getExtras();

        // Who has clicked by user.
        String value = extras.getString("send_string");

        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];

        listview_konusmaları_koy(email_string_result, value);


        gonder_buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String push_key_string = firebaseDatabase.getReference().push().getKey();
                databaseReference = firebaseDatabase.getReference("users/" + email_string_result + "/mesajlar/" + email_string_result + "|" + value +  "/").child(push_key_string);
                databaseReferences = firebaseDatabase.getReference("users/" + value + "/mesajlar/" + value + "|" + email_string_result  + "/").child(push_key_string);


                String input_chat_string = editText_mesaj_yaz.getText().toString();
                long msTime = System.currentTimeMillis();
                Date curDateTime = new Date(msTime);
                SimpleDateFormat formatter = new SimpleDateFormat("dd'/'MM'/'y hh:mm");
                String dateTime = formatter.format(curDateTime);

                Message message = new Message(input_chat_string, email_string_result, dateTime);

                databaseReference.setValue(message);
                databaseReferences.setValue(message);

                editText_mesaj_yaz.setText("");


            }
        });


    }

    private void listview_konusmaları_koy(String email_string_result, String kisi) {

        databaseReference = firebaseDatabase.getReference("users/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi +  "/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatLists.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    chatLists.add(message);

                }
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        customAdapter = new CustomAdapter(getApplicationContext(), chatLists, firebaseUser);


        mesajlaşma_listview.setAdapter(customAdapter);


    }


    private void definations() {

        mesajlaşma_listview = (ListView)findViewById(R.id.mesajlaşma_listview);
        editText_mesaj_yaz = (EditText) findViewById(R.id.editText_mesaj_yaz);
        gonder_buttons = (Button) findViewById(R.id.gonder_buttons);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), bottom_bar.class);
        startActivity(intent);
        message_square.this.finish();
    }
}
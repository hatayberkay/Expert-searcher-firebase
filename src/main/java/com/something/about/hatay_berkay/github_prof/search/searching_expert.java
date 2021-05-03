package com.something.about.hatay_berkay.github_prof.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.something.about.hatay_berkay.github_prof.R;
import com.something.about.hatay_berkay.github_prof.bottom_bar;
import com.something.about.hatay_berkay.github_prof.message_square;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class searching_expert extends AppCompatActivity {
ListView expert_listview;

     DatabaseReference databaseReference;
             FirebaseDatabase firebaseDatabase;
             private FirebaseAuth firebaseAuth;
                 private FirebaseUser firebaseUser;

 ArrayList<String> talents_array = new ArrayList<>();
 ArrayList<String> aranan_position = new ArrayList<>();


 ArrayList<String> users_array = new ArrayList<>();
 ArrayList<String> next_users_array = new ArrayList<>();

 ArrayList<String> name_array = new ArrayList<>();
 ArrayList<String> next_name_array = new ArrayList<>();

    ArrayList<String> image_array = new ArrayList<>();
    ArrayList<String> next_image_array = new ArrayList<>();

    custom_searching_expert custom_searching_expert = new custom_searching_expert();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_expert2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("");
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("send_string");

        // hatayberkay1234@gmail.com

        definations();

        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];


        // TODO: 3.05.2021 Add Searching at all by talent...
        databaseReference =  firebaseDatabase.getReference("users/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    //kullanıcı listesi
                    String key = child.getKey().toString();
                    users_array.add(key);

                    String isim_string = child.child("infos/"+"user_name").getValue().toString();
                    name_array.add(isim_string);

                    String image_url_string = child.child("infos/"+"image").getValue().toString();
                    image_array.add(image_url_string);






                    String yetenek_bir_string = child.child("infos/talent_first").getValue(String.class);
                    String yetenek_iki_string = child.child("infos/talent_second").getValue(String.class);
                    String yetenek_uc_string = child.child("infos/talent_third").getValue(String.class);



                    talents_array.add(yetenek_bir_string + yetenek_iki_string  + yetenek_uc_string);


                }

                for (int i = 0; i < talents_array.size(); i++) {
                    if (talents_array.get(i).toString().contains(value)){

                        aranan_position.add(String.valueOf(i));



                    }

                }


                for (String aa: aranan_position
                ) {

                    next_users_array.add(users_array.get(Integer.parseInt(aa)).toString());
                    next_name_array.add(name_array.get(Integer.parseInt(aa)).toString());
                    next_image_array.add(image_array.get(Integer.parseInt(aa)).toString());


                    expert_listview.setAdapter(custom_searching_expert);







                }



                if (next_users_array.size() == 0){
                    Toast.makeText(getApplicationContext(), "Üzgünüz. Bu alanda hiç uzmanımız yok." , Toast.LENGTH_SHORT).show();

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // TODO: 3.05.2021 End of code. ( Searching query job )

        // TODO: 3.05.2021 İtem click.

        expert_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(getApplicationContext(), message_square.class);
                i.putExtra("send_string",users_array.get(position));
                startActivity(i);
                searching_expert.this.finish();

            }
        });


    }

    private void definations() {

        expert_listview = (ListView)findViewById(R.id.expert_listview);
    }

    class custom_searching_expert extends BaseAdapter {
        @Override
        public int getCount() {
            return next_name_array.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_searching_expert, null);
             TextView expert_user_name = view.findViewById(R.id.expert_user_name);

            ImageView expert_image = view.findViewById(R.id.expert_image);




            expert_user_name.setText(next_name_array.get(i).toString());
            Picasso.get().load(next_image_array.get(i)).into(expert_image);



            return view;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), bottom_bar.class);
        startActivity(intent);
        searching_expert.this.finish();
    }
}
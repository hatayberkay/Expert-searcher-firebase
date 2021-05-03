package com.something.about.hatay_berkay.github_prof.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.something.about.hatay_berkay.github_prof.R;
import com.something.about.hatay_berkay.github_prof.being_expert.become_expert;
import com.something.about.hatay_berkay.github_prof.bottom_bar;
import com.something.about.hatay_berkay.github_prof.enter_page;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends Fragment {
   private EditText city_edittext, job_edittext, user_name_edittext, name_and_surname_edittext, education_edittext, about_edittext, expert_situtions_edittext;
   private Button log_out_button, id_button;
   private CircleImageView profile_image;
   private ImageView verfy_image , settings_image;
   private TextView education_textview , about_textvieww;
    String user_name_and_surname_string = "";

    private FirebaseAuth firebaseAuth;
        private FirebaseUser firebaseUser;

        DatabaseReference databaseReference;
                FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        defination();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("");

        user_name_edittext.setEnabled(false);
        education_edittext.setEnabled(false);
        about_edittext.setEnabled(false);
        city_edittext.setEnabled(false);
        job_edittext.setEnabled(false);
        name_and_surname_edittext.setEnabled(false);
        expert_situtions_edittext.setEnabled(false);

        verfy_image.setVisibility(View.INVISIBLE);

        id_button.setEnabled(false);


        id_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), become_expert.class);
                startActivity(intent);

                


            }
        });


        settings_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                education_edittext.setEnabled(true);
                about_edittext.setEnabled(true);


                settings_image.setVisibility(View.INVISIBLE);
                verfy_image.setVisibility(View.VISIBLE);



                education_textview.setText("Education -> Changable.");
                about_textvieww.setText("About -> Changable.");

            }
        });

        verfy_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] separated = firebaseAuth.getCurrentUser().getEmail().toString().split("@");
                final String firebase_usernames = separated[0];

    String education_new_text = education_edittext.getText().toString();
    String about_new_text = about_edittext.getText().toString();

                databaseReference = FirebaseDatabase.getInstance().getReference("users/" + firebase_usernames + "/infos");
                HashMap kullanıcı_kaydı_hashmap = new HashMap();

                kullanıcı_kaydı_hashmap.put("education", education_new_text);
                kullanıcı_kaydı_hashmap.put("about", about_new_text);

                databaseReference.updateChildren(kullanıcı_kaydı_hashmap);

                education_edittext.setEnabled(false);
                about_edittext.setEnabled(false);


                settings_image.setVisibility(View.VISIBLE);
                verfy_image.setVisibility(View.INVISIBLE);
                education_textview.setText("Education");
                about_textvieww.setText("About");
            }
        });

          String[] separated = firebaseAuth.getCurrentUser().getEmail().toString().split("@");
          final String firebase_usernames = separated[0];


          set_all_values(firebase_usernames);


          log_out_button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  firebaseAuth.signOut();
                  Intent enter_page_intent = new Intent(getContext(), enter_page.class);
                  startActivity(enter_page_intent);
                  try {
                      profile.this.finalize();
                  } catch (Throwable throwable) {
                      throwable.printStackTrace();
                  }
              }
          });


    }

    private void set_all_values(String firebase_usernames) {

        databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "image/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_string = (String) snapshot.getValue();

                Picasso.get().load(name_string).into(profile_image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//https://github-prof-cdf6c-default-rtdb.firebaseio.com/users/+"+ firebase_usernames +"/infos/state/

        databaseReference = firebaseDatabase.getReferenceFromUrl("https://github-prof-cdf6c-default-rtdb.firebaseio.com/users/" + firebase_usernames + "/infos/state/state");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_string = (String) snapshot.getValue();


                if (  name_string != null && name_string.equals("expert")) {

                    id_button.setText("Expert");

                }else  {
                    id_button.setText("Asker");
                    id_button.setEnabled(true);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "name/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_string = (String) snapshot.getValue();

                user_name_and_surname_string = name_string ;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "surname/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_string = (String) snapshot.getValue();


                user_name_and_surname_string = user_name_and_surname_string + " " +name_string ;

                name_and_surname_edittext.setText(user_name_and_surname_string);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "user_name/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_string = (String) snapshot.getValue();

                user_name_edittext.setText(name_string);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "job/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_string = (String) snapshot.getValue();


                job_edittext.setText(name_string);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "city/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_string = (String) snapshot.getValue();

                city_edittext.setText(name_string);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "education/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_string = (String) snapshot.getValue();


                education_edittext.setText(name_string);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference = firebaseDatabase.getReference("users/"+ firebase_usernames +"/" +"infos/" + "about/");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name_string = (String) snapshot.getValue();

                about_edittext.setText(name_string);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void defination() {
        name_and_surname_edittext = (EditText) getView().findViewById(R.id.name_and_surname_edittext);
        user_name_edittext = (EditText) getView().findViewById(R.id.user_name_edittext);
        job_edittext = (EditText) getView().findViewById(R.id.job_edittext);
        city_edittext = (EditText) getView().findViewById(R.id.city_edittext);
         education_edittext = (EditText) getView().findViewById(R.id.education_edittext);
        about_edittext = (EditText) getView().findViewById(R.id.about_edittext);
         expert_situtions_edittext = (EditText) getView().findViewById(R.id.expert_situtions_edittext);


        log_out_button = (Button) getView().findViewById(R.id.log_out_button);
        id_button = (Button) getView().findViewById(R.id.id_button);

        profile_image = (CircleImageView) getView().findViewById(R.id.profile_image);



        settings_image = (ImageView) getView().findViewById(R.id.settings_image);
        verfy_image = (ImageView) getView().findViewById(R.id.verfy_image);

        education_textview = (TextView) getView().findViewById(R.id.education_textview);
        about_textvieww = (TextView) getView().findViewById(R.id.about_textvieww);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


    }
}

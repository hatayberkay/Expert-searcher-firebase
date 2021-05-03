package com.something.about.hatay_berkay.github_prof;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.something.about.hatay_berkay.github_prof.Fragment.ask;
import com.something.about.hatay_berkay.github_prof.Fragment.main;
import com.something.about.hatay_berkay.github_prof.Fragment.messanger;
import com.something.about.hatay_berkay.github_prof.Fragment.profile;
import com.something.about.hatay_berkay.github_prof.user_data.welcome_screen;

public class bottom_bar extends AppCompatActivity {
    FragmentManager fragmentManager;

     DatabaseReference databaseReference;
             FirebaseDatabase firebaseDatabase;

              private FirebaseAuth firebaseAuth;
                  private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);


                BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar) ;


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

          String[] separated = firebaseAuth.getCurrentUser().getEmail().toString().split("@");
                                          final String firebase_usernames = separated[0];







        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        main main_fragment = new main();
        transaction.replace(R.id.container , main_fragment ,"main");
        transaction.addToBackStack(null);
        transaction.commit();


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {

                bottom_bar_fragmentler(tabId);



            }
            private void bottom_bar_fragmentler(int tabId) {


                switch (tabId){
                    case R.id.main :
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        main main_fragment = new main();
                        transaction.replace(R.id.container , main_fragment ,"main");

                        transaction.addToBackStack(null);


                        transaction.commit();

                        break;

                    case R.id.ask:


                        FragmentTransaction transaction_soru_sor = fragmentManager.beginTransaction();
                        ask ask_fragment = new ask();
                        transaction_soru_sor.replace(R.id.container , ask_fragment ,"ask");
                        transaction_soru_sor.addToBackStack(null);
                        transaction_soru_sor.commit();

                        break;
                  case R.id.message :

                        FragmentTransaction transaction_gelen_cevaplar = fragmentManager.beginTransaction();
                        messanger messanger_fragment = new messanger();
                        transaction_gelen_cevaplar.replace(R.id.container , messanger_fragment ,"messanger");
                        transaction_gelen_cevaplar.addToBackStack(null);
                        transaction_gelen_cevaplar.commit();

                     /*   Bundle bundle = new Bundle();
                        bundle.putString("user_name", firebase_user_string_email);
                        gelen_cevaplar_fragment.setArguments(bundle);*/

                        break;

                    case R.id.profil :

                        FragmentTransaction transaction_ben = fragmentManager.beginTransaction();
                        profile profile_fragment = new profile();
                        transaction_ben.replace(R.id.container , profile_fragment ,"profile");
                        transaction_ben.addToBackStack(null);


                        transaction_ben.commit();

                        break;



                }



            }
        });



    }
}
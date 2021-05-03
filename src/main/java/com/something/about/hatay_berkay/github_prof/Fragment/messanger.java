package com.something.about.hatay_berkay.github_prof.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
import com.something.about.hatay_berkay.github_prof.message_square;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class messanger extends Fragment {
private ListView listview;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    custom_messanger custom_messanger = new custom_messanger();

     ArrayList<String> image_array = new ArrayList<>();
     ArrayList<String> users_key_array = new ArrayList<>();
     ArrayList<String> users_name_array = new ArrayList<>();
     ArrayList<String> users_image_array = new ArrayList<>();
    ArrayList<String> aranan_position = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messenger_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        definations();

        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];


        // hatayberkaykedi@gmail.com


        // TODO: 30.04.2021 Get all message senders...
        databaseReference = firebaseDatabase.getReference("users/" + email_string_result + "/mesajlar");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users_key_array.clear();
                for (DataSnapshot child : snapshot.getChildren()){

                  String key = child.getKey().toString();

                    String[] separated = key.toString().split("\\|");
                    final String agaist_user_string = separated[1];

                  users_key_array.add(agaist_user_string);

                   // String gonderen_string = child.child("gonderici").getValue(String.class);



                   //  Toast.makeText(getContext(), "" + users_key_array, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference = firebaseDatabase.getReference("users/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users_name_array.clear();
                image_array.clear();

                for (DataSnapshot child : snapshot.getChildren()){

                    String e_mail_string = child.child("infos/E-mail").getValue(String.class);
                    String user_name_string = child.child("infos/user_name").getValue(String.class);
                    String image_string = child.child("infos/image").getValue(String.class);




                    for (int i = 0; i < users_key_array.size(); i++) {
                        if (users_key_array.get(i).toString().equals(e_mail_string)){

                            aranan_position.add(String.valueOf(i));

                            users_name_array.add(user_name_string);
                            image_array.add(image_string);






                        }

                    }




                }

                listview.setAdapter(custom_messanger);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent i = new Intent(getContext(), message_square.class);
        i.putExtra("send_string",users_key_array.get(position));
        startActivity(i);
        try {
            messanger.this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
});


    }


    class  custom_messanger extends BaseAdapter {


        @Override
        public int getCount() {
            return users_name_array.size();
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
            view = getLayoutInflater().inflate(R.layout.custom_messanger, null);
            CircleImageView messanger_image = (CircleImageView) view.findViewById(R.id.messanger_image);
            TextView user_name_textview = (TextView) view.findViewById(R.id.user_name_textview);

            user_name_textview.setText(users_name_array.get(i));


          Picasso.get().load(image_array.get(i)).into(messanger_image);












            return view;

        }

    }

    private void definations() {
        listview = (ListView)getView().findViewById(R.id.listview);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }



    }



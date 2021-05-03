package com.something.about.hatay_berkay.github_prof.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class main extends Fragment {
    GridView ad_gridview;

     DatabaseReference databaseReference;
             FirebaseDatabase firebaseDatabase;

     custom_main_gridview custom_main_gridview = new custom_main_gridview();
     ArrayList<String> e_mail_array = new ArrayList<>();
     ArrayList<String> user_name_array = new ArrayList<>();
     ArrayList<String> image_array = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        defination();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("users/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                e_mail_array.clear();
                user_name_array.clear();
                image_array.clear();

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {

                    String state_string = snapshot.child("infos/"+"state/state").getValue(String.class);
                    String e_mail_string = snapshot.child("infos/"+"E-mail").getValue(String.class);
                    String user_name_string = snapshot.child("infos/"+"user_name").getValue(String.class);
                    String image_string = snapshot.child("infos/"+"image").getValue(String.class);
                    if (state_string != null){
                        e_mail_array.add(e_mail_string);
                        user_name_array.add(user_name_string);
                        image_array.add(image_string);




                    }
                    ad_gridview.setAdapter(custom_main_gridview);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ad_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), message_square.class);
                i.putExtra("send_string",e_mail_array.get(position));
                startActivity(i);

            }
        });



    }

    private void defination() {

        ad_gridview = (GridView) getView().findViewById(R.id.ad_gridview);
    }

    class  custom_main_gridview extends BaseAdapter {


        @Override
        public int getCount() {
            return user_name_array.size();
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
            view = getLayoutInflater().inflate(R.layout.custom_main_gridview, null);
            CircleImageView main_image = (CircleImageView) view.findViewById(R.id.main_image);
            TextView name_textview = (TextView) view.findViewById(R.id.name_textview);

            Picasso.get().load(image_array.get(i)).into(main_image);

            name_textview.setText(user_name_array.get(i));










            return view;

        }
    }

}

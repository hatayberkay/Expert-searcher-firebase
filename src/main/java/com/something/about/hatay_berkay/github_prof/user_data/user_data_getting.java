package com.something.about.hatay_berkay.github_prof.user_data;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.something.about.hatay_berkay.github_prof.R;
import com.something.about.hatay_berkay.github_prof.bottom_bar;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_data_getting extends AppCompatActivity {
CircleImageView profile_image;
TextView galeri_textview;
EditText user_name_edittext , name_edittext , surname_edittext , education_edittext , about_edittext , job_edittext;
Button end_register_button;
Spinner city_spinner;

public static final int GALLERY_REQUEST_REQUEST = 1;
 DatabaseReference databaseReference;
         FirebaseDatabase firebaseDatabase;
         private FirebaseAuth firebaseAuth;
             private FirebaseUser firebaseUser;



    private Uri ImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_getting);



        definiton();

        String[] cities ;

        cities = getResources().getStringArray(R.array.cities );

        firebaseDatabase = FirebaseDatabase.getInstance();


        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,  cities);
        city_spinner.setAdapter(adapter);




        galeri_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,GALLERY_REQUEST_REQUEST);
            }
        });


        end_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user_name_edittext.getText().toString().isEmpty() || !name_edittext.getText().toString().isEmpty() || !surname_edittext.getText().toString().isEmpty() ||
                        !education_edittext.getText().toString().isEmpty() || !about_edittext.getText().toString().isEmpty() || !job_edittext.getText().toString().isEmpty()) {

                    uploads_file();

                }else {

                    Toast.makeText(user_data_getting.this, "Your boxes may be emtpy. Check them all.", Toast.LENGTH_SHORT).show();
                }




            }
        });


    }

    private void definiton() {


        profile_image = (CircleImageView) findViewById(R.id.profile_image);


        galeri_textview = (TextView) findViewById(R.id.galeri_textview);


        end_register_button = (Button) findViewById(R.id.end_register_button);


        user_name_edittext = (EditText) findViewById(R.id.user_name_edittext);
        name_edittext = (EditText) findViewById(R.id.name_edittext);
        surname_edittext = (EditText) findViewById(R.id.surname_edittext);
        education_edittext = (EditText) findViewById(R.id.education_edittext);
        about_edittext = (EditText) findViewById(R.id.about_edittext);
        job_edittext = (EditText) findViewById(R.id.job_edittext);


        city_spinner = (Spinner) findViewById(R.id.city_spinner);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }


    private void uploads_file() {
        // 8  .27
        if (ImageUri != null) {


              String[] separated = firebaseAuth.getCurrentUser().getEmail().split("@");
              final String firebase_usernames = separated[0];


            StorageReference storageReference= FirebaseStorage.getInstance().getReference("profile/" + firebase_usernames + "." + getFileExtension(ImageUri));

            storageReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            databaseReference = FirebaseDatabase.getInstance().getReference("users/" + firebase_usernames + "/infos");

                            String user_name_string = user_name_edittext.getText().toString();
                            String name_string = name_edittext.getText().toString();
                            String surname_string = surname_edittext.getText().toString();
                            String education_string = education_edittext.getText().toString();
                            String about_string = about_edittext.getText().toString();
                            String city_string = city_spinner.getSelectedItem().toString();
                            String job_string = job_edittext.getText().toString();



                            HashMap kullanıcı_kaydı_hashmap = new HashMap();

                            kullanıcı_kaydı_hashmap.put("E-mail", firebase_usernames);
                            kullanıcı_kaydı_hashmap.put("name", name_string);
                            kullanıcı_kaydı_hashmap.put("surname", surname_string);
                            kullanıcı_kaydı_hashmap.put("user_name", user_name_string);
                            kullanıcı_kaydı_hashmap.put("image", uri.toString());
                            kullanıcı_kaydı_hashmap.put("job", job_string);
                            kullanıcı_kaydı_hashmap.put("city", city_string);
                            kullanıcı_kaydı_hashmap.put("education", education_string);
                            kullanıcı_kaydı_hashmap.put("about", about_string);
                            kullanıcı_kaydı_hashmap.put("talent_first", "");
                            kullanıcı_kaydı_hashmap.put("talent_second", "");
                            kullanıcı_kaydı_hashmap.put("talent_third", "");

                            databaseReference.updateChildren(kullanıcı_kaydı_hashmap);

                          Intent bottom_bar_intent = new Intent(getApplicationContext(), bottom_bar.class);
                          startActivity(bottom_bar_intent);
                          user_data_getting.this.finish();


                        }
                    })     ;
                }
            });



        }else {
            Toast.makeText(this, "No file selected.", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            //  Uri path = data.getData();
            ImageUri = data.getData();
            Picasso.get().load(ImageUri).into(profile_image);

            // image_gallery.setImageURI(path);
        }

    }

}
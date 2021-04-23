package something.about.hatay.profossor.mesajlasma_ve_konu_acma;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mindorks.paracamera.Camera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import something.about.hatay.profossor.R;
import something.about.hatay.profossor.ana_sayfa_bottom_bar;

public class konu_acma extends AppCompatActivity {
    public static final int GALLERY_REQUEST = 12;
    public static final int camera_request = 1;
    public static final String TAG = "soru_sorma_ve";


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceters;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

ArrayList<String> fotograflar_array = new ArrayList<>();
    TextView sorulan_kisi_textview;
    EditText konu_edittext, soru_sorma_alanı_edittext ;
    ImageView first_ımage,second_ımage,third_ımage,forth_ımage;
    ImageView gallery_ımageview,camera_ımageview , geri_icon;
    Button gonders_button;
    Camera camera;
    Bitmap gallery_photo_bitmap;
    String kullanıcı_adı;
    String kullanıcı_adı_karsı;
    LinearLayout sorun_liener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konu_acma);
        tanımla();

        Bundle extras = getIntent().getExtras();
        final String ara_string = extras.getString("isim");

//sorulan_kisi_textview.setText(ara_string + " ' e sorunuzu bekliyor.");

//final String konu_edittext_string = konu_edittext.getText().toString();
//final String  soru_sorma_alanı_edittext_string = soru_sorma_alanı_edittext.getText().toString();


        String[] separated = firebaseUser.getEmail().toString().split("@");
        final String firebase_usernames = separated[0] ;



        sorun_liener = (LinearLayout)findViewById(R.id.sorun_liener);

        Display display = getWindowManager().getDefaultDisplay();
        double height = display.getHeight();

        sorun_liener.setMinimumHeight((int) (height/2));
        soru_sorma_alanı_edittext.setHeight((int) (height/2));




        databaseReferenceters = firebaseDatabase.getReference("kullanıcılar/" +  firebase_usernames +  "/bilgiler/Kullanıcı_adı" );

        databaseReferenceters.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
         kullanıcı_adı = (String) dataSnapshot.getValue();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});

        databaseReferenceters = firebaseDatabase.getReference("kullanıcılar/" +  ara_string +  "/bilgiler/Kullanıcı_adı" );

        databaseReferenceters.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kullanıcı_adı_karsı = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




// FIXME: 7.04.2019  Mesajlaşma kısmında sadece konu açma ayrı bölüm olsun. Diğer mesajlar normal yazı şeklinde olsun.
camera_ımageview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        PermissionCamera();
    }
});

gallery_ımageview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        Intent ıntent = new Intent();
        ıntent.setType("image/*");
        ıntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(ıntent,GALLERY_REQUEST);

    }
});
    gonders_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String push_key_string = firebaseDatabase.getReference().push().getKey();

            databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/mesajlar").child(firebase_usernames + "|"+ara_string+ "|" +konu_edittext.getText().toString()  +"/baslangıc");
            databaseReferenceters = firebaseDatabase.getReference("kullanıcılar/" +  ara_string +  "/mesajlar" ).child(ara_string + "|"+firebase_usernames+ "|" +konu_edittext.getText().toString()+"/baslangıc");;
            HashMap giris_icerik_hasmap = new HashMap();
            switch (fotograflar_array.size()) {
// FIXME: 22.09.2018  Komple tek tek parçalarına ayrırak gönder.
                case 0:

                    giris_icerik_hasmap.put("konu", konu_edittext.getText().toString());
                    giris_icerik_hasmap.put("sorun", soru_sorma_alanı_edittext.getText().toString());
                    giris_icerik_hasmap.put("from", firebase_usernames);
                    giris_icerik_hasmap.put("to", ara_string);
                    giris_icerik_hasmap.put("gonderenin", kullanıcı_adı);
                    giris_icerik_hasmap.put("karsı", kullanıcı_adı_karsı);

                    databaseReference.updateChildren(giris_icerik_hasmap);
                    databaseReferenceters.updateChildren(giris_icerik_hasmap);

                    break;


                case 1:


                    giris_icerik_hasmap.put("konu", konu_edittext.getText().toString());
                    giris_icerik_hasmap.put("sorun", soru_sorma_alanı_edittext.getText().toString());
                    giris_icerik_hasmap.put("from", firebase_usernames);
                    giris_icerik_hasmap.put("to", ara_string);
                    giris_icerik_hasmap.put("fotograf_1", fotograflar_array.get(0).toString());
                    giris_icerik_hasmap.put("gonderenin", kullanıcı_adı);
                    giris_icerik_hasmap.put("karsı", kullanıcı_adı_karsı);
                    databaseReference.updateChildren(giris_icerik_hasmap);
                    databaseReferenceters.updateChildren(giris_icerik_hasmap);

                    break;


                case 2:

                    giris_icerik_hasmap.put("konu", konu_edittext.getText().toString());
                    giris_icerik_hasmap.put("sorun", soru_sorma_alanı_edittext.getText().toString());
                    giris_icerik_hasmap.put("from", firebase_usernames);
                    giris_icerik_hasmap.put("to", ara_string);
                    giris_icerik_hasmap.put("fotograf_1", fotograflar_array.get(0).toString());
                    giris_icerik_hasmap.put("fotograf_2", fotograflar_array.get(1).toString());
                    giris_icerik_hasmap.put("gonderenin", kullanıcı_adı);
                    giris_icerik_hasmap.put("karsı", kullanıcı_adı_karsı);
                    databaseReference.updateChildren(giris_icerik_hasmap);
                    databaseReferenceters.updateChildren(giris_icerik_hasmap);

                    break;

                case 3:

                    giris_icerik_hasmap.put("konu", konu_edittext.getText().toString());
                    giris_icerik_hasmap.put("sorun", soru_sorma_alanı_edittext.getText().toString());
                    giris_icerik_hasmap.put("from", firebase_usernames);
                    giris_icerik_hasmap.put("to", ara_string);
                    giris_icerik_hasmap.put("fotograf_1", fotograflar_array.get(0).toString());
                    giris_icerik_hasmap.put("fotograf_2", fotograflar_array.get(1).toString());
                    giris_icerik_hasmap.put("fotograf_3", fotograflar_array.get(2).toString());
                    giris_icerik_hasmap.put("gonderenin", kullanıcı_adı);
                    giris_icerik_hasmap.put("karsı", kullanıcı_adı_karsı);
                    databaseReference.updateChildren(giris_icerik_hasmap);
                    databaseReferenceters.updateChildren(giris_icerik_hasmap);

                    break;

                case 4:

                    giris_icerik_hasmap.put("konu", konu_edittext.getText().toString());
                    giris_icerik_hasmap.put("sorun", soru_sorma_alanı_edittext.getText().toString());
                    giris_icerik_hasmap.put("from", firebase_usernames);
                    giris_icerik_hasmap.put("to", ara_string);
                    giris_icerik_hasmap.put("fotograf_1", fotograflar_array.get(0).toString());
                    giris_icerik_hasmap.put("fotograf_2", fotograflar_array.get(1).toString());
                    giris_icerik_hasmap.put("fotograf_3", fotograflar_array.get(2).toString());
                    giris_icerik_hasmap.put("fotograf_4", fotograflar_array.get(3).toString());
                    giris_icerik_hasmap.put("gonderenin", kullanıcı_adı);
                    giris_icerik_hasmap.put("karsı", kullanıcı_adı_karsı);
                    databaseReference.updateChildren(giris_icerik_hasmap);
                    databaseReferenceters.updateChildren(giris_icerik_hasmap);

                    break;
          
          
          
          
            }


            Intent isd = new Intent(getApplicationContext(),ana_sayfa_bottom_bar.class);
            startActivity(isd);
            konu_acma.this.finish();
        }







            });
    }
    
    

    private void tanımla() {
        sorulan_kisi_textview = (TextView)findViewById(R.id.sorulan_kisi_textview);

        soru_sorma_alanı_edittext = (EditText)findViewById(R.id.soru_sorma_alanı_edittext);
        konu_edittext = (EditText)findViewById(R.id.konu_edittext);
//Imageview

        first_ımage = (ImageView)findViewById(R.id.first_ımage);
        second_ımage = (ImageView)findViewById(R.id.second_ımage);
        third_ımage = (ImageView)findViewById(R.id.third_ımage);
        forth_ımage = (ImageView)findViewById(R.id.forth_ımage);


        gallery_ımageview = (ImageView)findViewById(R.id.gallery_ımageview);
        camera_ımageview = (ImageView)findViewById(R.id.camera_ımageview);


        gonders_button = (Button)findViewById(R.id.gonders_button);

firebaseDatabase = FirebaseDatabase.getInstance();

        
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    private void PermissionCamera() {
        try {


            if (Build.VERSION.SDK_INT >= 21  ) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Check the SDK version and whether the permission is already granted or not.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, camera_request);
                //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            } else {

                openCamera();


            }
        } catch (Exception e) {
            Log.e(TAG, "PermissionCamera: " + e.toString() );
        } finally {

        }

    }



    private void openCamera() {
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(this);

        try {
            camera.takePicture();


        }catch (Exception e){
            e.printStackTrace();
        }

    }




    public String imagetostring(Bitmap bitmap) {
        ByteArrayOutputStream ByteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG , 100, ByteArrayOutputStream);
        byte[] byt = ByteArrayOutputStream.toByteArray();
        String imagetoString =    Base64.encodeToString(byt,Base64.DEFAULT);

        return imagetoString ;}


    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {


            case  GALLERY_REQUEST :

                if(resultCode == RESULT_OK && data != null){   //gallery için 1
                    Uri path = data.getData();
                    try {
                        gallery_photo_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);


                        if (fotograflar_array.size() < 4) {
                            fotograflar_array.add(imagetostring(gallery_photo_bitmap));
                        }else {
                            Toast.makeText(this, "Maksimum tek seferde fotograf koyma oranı.", Toast.LENGTH_SHORT).show();

                        }

                        if (fotograflar_array.size() == 1 ){
                            first_ımage.setImageBitmap(StringToBitMap(fotograflar_array.get(0).toString()));

                        }

                        if (fotograflar_array.size() == 2 ){
                            second_ımage.setImageBitmap(StringToBitMap(fotograflar_array.get(1).toString()));

                        }

                        if (fotograflar_array.size() == 3 ){
                            third_ımage.setImageBitmap(StringToBitMap(fotograflar_array.get(2).toString()));

                        }

                        if (fotograflar_array.size() == 4 ){
                            forth_ımage.setImageBitmap(StringToBitMap(fotograflar_array.get(3).toString()));

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case  camera_request :

// TODO: 17.09.2018 Fotograf almayı en baştan arşive geçir. 
                if(resultCode == RESULT_OK && data != null){

                    Uri selectedImage = data.getData();

                    Bitmap bitmap = camera.getCameraBitmap();
                    if(bitmap != null) {

                        if (fotograflar_array.size()  < 4 ) {
                            fotograflar_array.add(imagetostring(bitmap));
                        }else {
                            Toast.makeText(this, "Maksimum tek seferde fotograf koyma oranı.", Toast.LENGTH_SHORT).show();

                        }

                        if (fotograflar_array.size() == 1 ){
                            first_ımage.setImageBitmap(StringToBitMap(fotograflar_array.get(0).toString()));

                        }

                        if (fotograflar_array.size() == 2 ){
                            second_ımage.setImageBitmap(StringToBitMap(fotograflar_array.get(1).toString()));

                        }

                        if (fotograflar_array.size() == 3 ){
                            third_ımage.setImageBitmap(StringToBitMap(fotograflar_array.get(2).toString()));

                        }

                        if (fotograflar_array.size() == 4 ){
                            forth_ımage.setImageBitmap(StringToBitMap(fotograflar_array.get(3).toString()));

                        }


                    }else{
                        Toast.makeText(this.getApplicationContext(),"Fotoğraf bilgisi alınamadı.!", Toast.LENGTH_SHORT).show();
                    }



                }//gallery için 1


                break;






        }
}}

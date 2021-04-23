package something.about.hatay.profossor.bilgialimi;

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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mindorks.paracamera.Camera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import something.about.hatay.profossor.R;
import something.about.hatay.profossor.ana_sayfa_bottom_bar;

public class standart extends AppCompatActivity {
EditText meslek_edittext  ,hakkında_edittext ,egitim_edittext ,soyadı_edittext , isim_edittext, kullanıcı_adı_edittext ;
  Button kayıt_islemini_bittir_button;
  TextView galeri_textview ,fotograf_cek_textview;
CircleImageView profil_foto;
  FirebaseDatabase firebaseDatabase;
  DatabaseReference databaseReference;
  FirebaseAuth firebaseAuth;
  FirebaseUser firebaseUser;
  Spinner sehir_edittext;
private static final int GALLERY_REQUEST = 1;
private static final int CAMERA_REQUEST = 2;
private static final int PERMISSIONS_REQUEST_CAMERA = 3;
private static final String TAG = "standart";
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standart);
        tanımla();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        String[] items = getResources().getStringArray(R.array.iller);


        photo =    BitmapFactory.decodeResource(getResources(), R.drawable.bennaaa);

        sehir_edittext.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                items));


        galeri_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
galeri_cek();
            }

            private void galeri_cek() {
                Intent ıntent = new Intent();
                ıntent.setType("image/*");
                ıntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(ıntent,GALLERY_REQUEST);



            }
        });

        fotograf_cek_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
foto_cek();
            }

            private void foto_cek() {

                PermissionCamera();


            }

            private void PermissionCamera() {
                try {


                    if (Build.VERSION.SDK_INT >= 24  ) {
                        try {
                            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                            m.invoke(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    // Check the SDK version and whether the permission is already granted or not.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
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
                Camera camera;


                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(takePicture, CAMERA_REQUEST);


                try {
                    /* camera.takePicture();*/

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        });



        kayıt_islemini_bittir_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bilgileri_kaydet();
            }
        });

    }

    private void bilgileri_kaydet() {
        String kullanıcı_adı_string =kullanıcı_adı_edittext.getText().toString();
        String isim_string = isim_edittext.getText().toString();
        String soyadı_string = soyadı_edittext.getText().toString();
        String egitim_string = egitim_edittext.getText().toString();
        String hakkında_string = hakkında_edittext.getText().toString();
        String sehir_string = sehir_edittext.getSelectedItem().toString();
        String meslek_string = meslek_edittext.getText().toString();


        String[] separated = firebaseUser.getEmail().toString().split("@");
        final String firebase_usernames = separated[0] ;




        if (!kullanıcı_adı_string.isEmpty() && !meslek_string.isEmpty() && !sehir_string.isEmpty() && !egitim_string.isEmpty() && !hakkında_string.isEmpty())

        {


            databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler");
            HashMap kullanıcı_kaydı_hashmap = new HashMap();

            kullanıcı_kaydı_hashmap.put("E-mail", firebase_usernames);
            kullanıcı_kaydı_hashmap.put("isim", isim_string + " " + soyadı_string);
            kullanıcı_kaydı_hashmap.put("Kullanıcı_adı", kullanıcı_adı_string);
            kullanıcı_kaydı_hashmap.put("fotograf", imagetostring());
            kullanıcı_kaydı_hashmap.put("meslek", meslek_string);
            kullanıcı_kaydı_hashmap.put("sehir", sehir_string);
            kullanıcı_kaydı_hashmap.put("egitim", egitim_string);
            kullanıcı_kaydı_hashmap.put("hakkında", hakkında_string);
            kullanıcı_kaydı_hashmap.put("uyelik", "true");
            databaseReference.updateChildren(kullanıcı_kaydı_hashmap);


            databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler/");
            HashMap yetenek_ekle = new HashMap();

            yetenek_ekle.put("yetenek_bir", "");
            yetenek_ekle.put("yetenek_iki", "");
            yetenek_ekle.put("yetenek_uc", "");
            yetenek_ekle.put("yetenek_dort", "");
            yetenek_ekle.put("yetenek_bes", "");
            yetenek_ekle.put("yetenek_altı", "");
            yetenek_ekle.put("yetenek_yedi", "");




            databaseReference.updateChildren(yetenek_ekle);


            Intent ana_sayfa = new Intent(getApplicationContext(),ana_sayfa_bottom_bar.class);
            startActivity(ana_sayfa);
            standart.this.finish();
        }else {

            Toast.makeText(this, "Lütfen tüm satırları doldurunuz.", Toast.LENGTH_SHORT).show();
            
            
        }
    }

    private void tanımla() {
        fotograf_cek_textview = (TextView)findViewById(R.id.fotograf_cek_textview);
        galeri_textview = (TextView)findViewById(R.id.galeri_textview);
        kullanıcı_adı_edittext = (EditText)findViewById(R.id.kullanıcı_adı_edittext);
        isim_edittext = (EditText)findViewById(R.id.isim_edittext);
        soyadı_edittext = (EditText)findViewById(R.id.soyadı_edittext);
        egitim_edittext = (EditText)findViewById(R.id.egitim_edittext);
        hakkında_edittext = (EditText)findViewById(R.id.hakkında_edittext);
        sehir_edittext = (Spinner) findViewById(R.id.sehir_edittext);
        meslek_edittext = (EditText)findViewById(R.id.meslek_edittext);
        kayıt_islemini_bittir_button = (Button)findViewById(R.id.kayıt_islemini_bittir_button);
        profil_foto = (CircleImageView)findViewById(R.id.profil_fotos) ;



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if (requestCode != RESULT_CANCELED){
            if (requestCode == GALLERY_REQUEST) {
                Uri path = data.getData();
                try {
                    photo = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profil_foto.setImageURI(path);


            }}





        if(resultCode != RESULT_CANCELED){
            if (requestCode == CAMERA_REQUEST) {
                photo = (Bitmap) data.getExtras().get("data");
                profil_foto.setImageBitmap(photo);
            }



// FIXME: 9.02.2019 galleryden foto seçimi yapılcak.







                }
}


// hatayberkayis@gmail.com
// hatayberkay1234@gmail.com


  public String imagetostring() {
        ByteArrayOutputStream ByteArrayOutputStream = new ByteArrayOutputStream();

      photo.compress(Bitmap.CompressFormat.JPEG , 100, ByteArrayOutputStream);
        byte[] byt = ByteArrayOutputStream.toByteArray();
        String imagetoString =    Base64.encodeToString(byt,Base64.DEFAULT);

        return imagetoString ;}

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }


}

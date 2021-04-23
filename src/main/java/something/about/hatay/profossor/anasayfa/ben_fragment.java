package something.about.hatay.profossor.anasayfa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import something.about.hatay.profossor.R;
import something.about.hatay.profossor.uye_girisi;
import something.about.hatay.profossor.uzman_olmak.uzman_bilgileri;

import static android.app.Activity.RESULT_CANCELED;
import static android.content.Context.MODE_PRIVATE;

public class ben_fragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView ayarlar, onayla;
    TextView fotografı_degistir_textview, fotografı_kaldır_textview;
    private static final String TAG = "ben_fragment";
    ArrayList<String> yeteneklisttesi_array = new ArrayList<String>();
    private static final int GALLERY_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final int PERMISSIONS_REQUEST_CAMERA = 3;

    Bitmap photo;
    EditText sehir_textview, meslek_textview, kullanıcı_adı_textview, ad_edittext, egitim_textview, hakkımda_textview, uzmanlık_alanları_textview;
    Button cıkıs_yap_button, kimlik_button;
    CircleImageView profil_foto;
    String firebase_usernames;
    TextView ad_textview , egitim_textviewaaaa , hakkımdasss_textview;
String isim ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ben_fragment, container, false);
// hatayberkay1234@gmail.com
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        String[] separated = firebaseUser.getEmail().toString().split("@");
        final String firebase_usernames = separated[0];

        tanımla();

        kullanıcı_adı_textview.setEnabled(false);
        egitim_textview.setEnabled(false);
        hakkımda_textview.setEnabled(false);
        fotografı_degistir_textview.setEnabled(false);
        fotografı_kaldır_textview.setEnabled(false);
        sehir_textview.setEnabled(false);
        meslek_textview.setEnabled(false);
        ad_edittext.setEnabled(false);
        uzmanlık_alanları_textview.setEnabled(false);

        SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("id",firebase_usernames);
        editor.commit();



        onayla.setVisibility(View.INVISIBLE);
        ayarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kullanıcı_adı_textview.setEnabled(true);
                egitim_textview.setEnabled(true);
                hakkımda_textview.setEnabled(true);
                fotografı_degistir_textview.setEnabled(true);
                fotografı_kaldır_textview.setEnabled(true);

                ayarlar.setVisibility(View.INVISIBLE);
                onayla.setVisibility(View.VISIBLE);


                ad_textview.setText("Ad -> Değiştirilebilir.");
                egitim_textviewaaaa.setText("Eğitim -> Değiştirilebilir.");
                hakkımdasss_textview.setText("Hakkımda -> Değiştirilebilir.");

            }
        });


        onayla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onayla.setVisibility(View.INVISIBLE);
                ayarlar.setVisibility(View.VISIBLE);

    String kullanıcı_adı_textview_string = kullanıcı_adı_textview.getText().toString();
    String egitim_textview_string = egitim_textview.getText().toString();
    String hakkımda_textview_string = hakkımda_textview.getText().toString();

    databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler");
    HashMap kullanıcı_kaydı_hashmap = new HashMap();

    kullanıcı_kaydı_hashmap.put("Kullanıcı_adı", kullanıcı_adı_textview_string);

//    kullanıcı_kaydı_hashmap.put("fotograf", imagetostring());

    kullanıcı_kaydı_hashmap.put("egitim", egitim_textview_string);
    kullanıcı_kaydı_hashmap.put("hakkında", hakkımda_textview_string);
    databaseReference.updateChildren(kullanıcı_kaydı_hashmap);





                ad_textview.setText("Ad");
                egitim_textviewaaaa.setText("Eğitim");
                hakkımdasss_textview.setText("Hakkımda");

                kullanıcı_adı_textview.setEnabled(false);
                egitim_textview.setEnabled(false);
                hakkımda_textview.setEnabled(false);
                fotografı_degistir_textview.setEnabled(false);
                fotografı_kaldır_textview.setEnabled(false);



            }
        });


        verileri_koyma(firebase_usernames);


        fotografı_degistir_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                galeri_cek();


            }
        });


        fotografı_kaldır_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foto_cek();



            }


        });


        cıkıs_yap_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent i = new Intent(getActivity(), uye_girisi.class);
                startActivity(i);

            }
        });

        kimlik_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler");
                HashMap kullanıcı_kaydı_hashmap = new HashMap();

                kullanıcı_kaydı_hashmap.put("kimlik", "");
                databaseReference.updateChildren(kullanıcı_kaydı_hashmap);*/
               if (kimlik_button.getText().toString().equals("Kullanıcı")) {
                   Intent uzman_bilgileri_gonder = new Intent(getActivity(), uzman_bilgileri.class);
                   startActivity(uzman_bilgileri_gonder);
               }

            }
        });


        super.onActivityCreated(savedInstanceState);
    }

    private void verileri_koyma(String firebase_usernames) {


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/isim");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String isim_string = (String) dataSnapshot.getValue();

                isim = isim_string;
              //  ad_edittext.setText(isim_string);
                Log.i(TAG, "onDataChange: " + isim_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/soyad");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String soyad_string = (String) dataSnapshot.getValue();

                isim = isim + " " + soyad_string  ;
                ad_edittext.setText(isim);
            //    Log.i(TAG, "onDataChange: " + isim_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/Kullanıcı_adı");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kullanıcı_adı_string = (String) dataSnapshot.getValue();
                kullanıcı_adı_textview.setText(kullanıcı_adı_string);
                Log.i(TAG, "onDataChange: " + kullanıcı_adı_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/egitim");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String egitim_string = (String) dataSnapshot.getValue();
                egitim_textview.setText(egitim_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/hakkında");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String hakkında_string = (String) dataSnapshot.getValue();
                hakkımda_textview.setText(hakkında_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/meslek");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String meslek_string = (String) dataSnapshot.getValue();
                meslek_textview.setText(meslek_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/sehir");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sehir_string = (String) dataSnapshot.getValue();
                sehir_textview.setText(sehir_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/fotograf");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String foto = (String) dataSnapshot.getValue();
                profil_foto.setImageBitmap(StringToBitMap(foto));
                Log.i(TAG, "onDataChange: " + foto);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler/" + "uzman/kimlik");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kimlik_string = (String) dataSnapshot.getValue();
                if (kimlik_string != null) {
                    kimlik_button.setText(kimlik_string);

                } else {

                    kimlik_button.setText("Kullanıcı");
                }

                Log.i(TAG, "deneme " + kimlik_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // TODO: 16.02.2019 Yetenekler başlar
        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/yetenek_bir");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String yetenek_bir = (String) dataSnapshot.getValue();
                yeteneklisttesi_array.add(yetenek_bir);
                Log.i(TAG, "onActivityCreated: " + yetenek_bir);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/yetenek_iki");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String yetenek_iki = (String) dataSnapshot.getValue();
                yeteneklisttesi_array.add(yetenek_iki);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/yetenek_uc");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String yetenek_uc = (String) dataSnapshot.getValue();
                yeteneklisttesi_array.add(yetenek_uc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/yetenek_dort");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String yetenek_dort = (String) dataSnapshot.getValue();
                yeteneklisttesi_array.add(yetenek_dort);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/yetenek_bes");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String yetenek_bes = (String) dataSnapshot.getValue();
                yeteneklisttesi_array.add(yetenek_bes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/yetenek_altı");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String yetenek_altı_string = (String) dataSnapshot.getValue();
                yeteneklisttesi_array.add(yetenek_altı_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler" + "/yetenek_yedi");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String yetenek_yedi_string = (String) dataSnapshot.getValue();
                yeteneklisttesi_array.add(yetenek_yedi_string);

if (yeteneklisttesi_array.size() > 0){
                for (int i = 0; i < yeteneklisttesi_array.size(); i++) {


                    uzmanlık_alanları_textview.append(yeteneklisttesi_array.get(i) + " ");
                }

}else  if (yeteneklisttesi_array.size() == 0){

    uzmanlık_alanları_textview.setText("Uzman Degilsiniz.");
}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void tanımla() {
        ad_edittext = (EditText) getView().findViewById(R.id.ad_edittext);
        egitim_textview = (EditText) getView().findViewById(R.id.egitim_textview);
        sehir_textview = (EditText) getView().findViewById(R.id.sehir_textview);
        meslek_textview = (EditText) getView().findViewById(R.id.meslek_textview);
        kullanıcı_adı_textview = (EditText) getView().findViewById(R.id.kullanıcı_adı_textview);
        hakkımda_textview = (EditText) getView().findViewById(R.id.hakkımda_textview);
        uzmanlık_alanları_textview = (EditText) getView().findViewById(R.id.uzmanlık_alanları_textview);
        cıkıs_yap_button = (Button) getView().findViewById(R.id.cıkıs_yap_button);
        kimlik_button = (Button) getView().findViewById(R.id.kimlik_button);
        profil_foto = (CircleImageView) getView().findViewById(R.id.profil_foto);
        ayarlar = (ImageView) getView().findViewById(R.id.ayarlar);
        onayla = (ImageView) getView().findViewById(R.id.onayla);
        fotografı_degistir_textview = (TextView) getView().findViewById(R.id.fotografı_degistir_textview);
        fotografı_kaldır_textview = (TextView) getView().findViewById(R.id.fotografı_kaldır_textview);


        ad_textview = (TextView) getView().findViewById(R.id.ad_textview);
        egitim_textviewaaaa = (TextView) getView().findViewById(R.id.egitim_textviewaaaa);
        hakkımdasss_textview = (TextView) getView().findViewById(R.id.hakkımdasss_textview);
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public String imagetostring() {
        ByteArrayOutputStream ByteArrayOutputStream = new ByteArrayOutputStream();

        photo.compress(Bitmap.CompressFormat.JPEG, 100, ByteArrayOutputStream);
        byte[] byt = ByteArrayOutputStream.toByteArray();
        String imagetoString = Base64.encodeToString(byt, Base64.DEFAULT);

        return imagetoString;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);


        SharedPreferences settings = getActivity().getSharedPreferences("PREFS_NAME", MODE_PRIVATE);

        String id = settings.getString("id", null);



        if (requestCode != RESULT_CANCELED){
            if (requestCode == GALLERY_REQUEST){
                Uri path = data.getData();
               try {
                  photo = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profil_foto.setImageBitmap(photo);
                //hatayberkay1234@gmail.com

               databaseReference = firebaseDatabase.getReference("kullanıcılar/" + id + "/bilgiler/");
                HashMap kullanıcı_kaydı_hashmap = new HashMap();
                kullanıcı_kaydı_hashmap.put("fotograf", imagetostring());
                databaseReference.updateChildren(kullanıcı_kaydı_hashmap);
            }


            if(resultCode != RESULT_CANCELED) {
                if (requestCode == CAMERA_REQUEST) {
                    photo = (Bitmap) data.getExtras().get("data");
                    profil_foto.setImageBitmap(photo);


                    databaseReference = firebaseDatabase.getReference("kullanıcılar/" + id + "/bilgiler/");
                    HashMap kullanıcı_kaydı_hashmap = new HashMap();
                    kullanıcı_kaydı_hashmap.put("fotograf", imagetostring());
                    databaseReference.updateChildren(kullanıcı_kaydı_hashmap);



                }

            }








        }




    }


    private void galeri_cek() {
        Intent ıntent = new Intent();
        ıntent.setType("image/*");
        ıntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(ıntent,GALLERY_REQUEST);




    }





    private void openCamera() {
        Camera camera;


        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");



       // Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, CAMERA_REQUEST);


        try {
            /* camera.takePicture();*/

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void foto_cek() {

        PermissionCamera();

        Log.i(TAG, "onClick: It works foto_cek");
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


            openCamera();



        } catch (Exception e) {
            Log.e(TAG, "camera: " + e.toString() );
        } finally {

        }

    }}


















      /*  databaseReference_kullan = firebaseDatabase.getReference("Kullanıcı/");
        databaseReference_kullan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String s = child.getKey();
                    kullanıcı_kisiler_array.add(s);

                }*/


        /*uzmanlar_firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uzman_kisiler_ilanlar_array.clear();
                yetenekler_uzman_kisiler_ilanlar_array.clear();
                ad_soyad_bilgiler_uzman_kisiler_ilanlar_array.clear();
// TODO: 16.09.2018 ALLAHINI SEVİYORSAN ARŞİVE KOYARSIN. nOT DIŞARIDA ARRAYLAR 0 A GİDİYOR.
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String ss = child.child("yetenekler/").getValue().toString();

                    String ad_soyad_string = child.child("bilgiler/"+"ad_soyad").getValue().toString();
                    String profil_string = child.child("bilgiler/" +"profil_foto").getValue().toString();
                    String meslek_string = child.child("bilgiler/" +"meslek").getValue().toString();
                    //   String ssa = child.child("bilgiler").getValue().toString();
      */
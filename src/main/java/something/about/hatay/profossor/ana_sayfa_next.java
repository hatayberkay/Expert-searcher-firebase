package something.about.hatay.profossor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import something.about.hatay.profossor.mesajlasma_ve_konu_acma.konu_acma;

public class ana_sayfa_next extends AppCompatActivity {
CircleImageView profil_foto_CircleImageView;
Button soru_sor_ana_sayfa_button;
TextView isim_card_textview, meslek_card_textview , konum_card_textview ,hakkımda_textview_ana_sayfa,egitim_textview_ana_sayfa,uzmanlık_alanları_textview_ana_sayfa;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private static final String TAG = "ana_sayfa_next" ;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ArrayList<String> yeteneklisttesi_array = new ArrayList<String>();
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ana_sayfa_profil_inceleme);
        tanımla();

Bundle bundle = getIntent().getExtras();

final String id_string = bundle.getString("ids");

     //  Toast.makeText(this, "" + id_string, Toast.LENGTH_SHORT).show();

    hakkımda_textview_ana_sayfa.setMovementMethod(new ScrollingMovementMethod());


       verileri_yerleştir(id_string);


    soru_sor_ana_sayfa_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent next_to_konu_acma_intent = new Intent(getApplicationContext(),konu_acma.class);
            next_to_konu_acma_intent.putExtra("isim" ,id_string);
            startActivity(next_to_konu_acma_intent);




        }
    });


    }

    private void verileri_yerleştir(String id_string) {






        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/fotograf");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String fotograf_string = (String) dataSnapshot.getValue();

              profil_foto_CircleImageView.setImageBitmap(StringToBitMap(fotograf_string));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/Kullanıcı_adı");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Kullanıcı_adı_string = (String) dataSnapshot.getValue();

                isim_card_textview.setText(Kullanıcı_adı_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/meslek");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String meslek_string = (String) dataSnapshot.getValue();

                meslek_card_textview.setText(meslek_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/meslek");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String meslek_string = (String) dataSnapshot.getValue();

                meslek_card_textview.setText(meslek_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

     databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/sehir");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sehir_string = (String) dataSnapshot.getValue();

                konum_card_textview.setText(sehir_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

  databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/hakkında");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String hakkında_string = (String) dataSnapshot.getValue();

                hakkımda_textview_ana_sayfa.setText(hakkında_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

  databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/egitim");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String egitim_string = (String) dataSnapshot.getValue();

                egitim_textview_ana_sayfa.setText(egitim_string);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        // TODO: 16.02.2019 Yetenekler başlar
        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/yetenek_bir");
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



        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/yetenek_iki");
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


        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/yetenek_uc");
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


        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/yetenek_dort");
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



        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/yetenek_bes");
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




        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/yetenek_altı");
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




        databaseReference = firebaseDatabase.getReference("kullanıcılar/"+ id_string +"/bilgiler" + "/yetenek_yedi");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String yetenek_yedi_string = (String) dataSnapshot.getValue();
                yeteneklisttesi_array.add(yetenek_yedi_string);



                for(int i = 0; i < yeteneklisttesi_array.size(); i++) {


                    uzmanlık_alanları_textview_ana_sayfa.append(  yeteneklisttesi_array.get(i)  +" ");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {









                    String yetenek_bir_string = snapshot.child("bilgiler/"+"yetenek_bir").getValue(String.class);
                    String yetenek_iki_string = snapshot.child("bilgiler/"+"yetenek_iki").getValue(String.class);
                    String yetenek_uc_string = snapshot.child("bilgiler/"+"yetenek_uc").getValue(String.class);
                    String yetenek_dort_string = snapshot.child("bilgiler/"+"yetenek_dort").getValue(String.class);
                    String yetenek_bes_string = snapshot.child("bilgiler/"+"yetenek_bes").getValue(String.class);
                    String yetenek_altı_string = snapshot.child("bilgiler/"+"yetenek_altı").getValue(String.class);
                    String yetenek_yedi_string = snapshot.child("bilgiler/"+"yetenek_yedi").getValue(String.class);


                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }

    private void tanımla() {
        uzmanlık_alanları_textview_ana_sayfa = (TextView)findViewById(R.id.uzmanlık_alanları_textview_ana_sayfa) ;
                egitim_textview_ana_sayfa = (TextView)findViewById(R.id.egitim_textview_ana_sayfa) ;
        hakkımda_textview_ana_sayfa = (TextView)findViewById(R.id.hakkımda_textview_ana_sayfa) ;
                konum_card_textview = (TextView)findViewById(R.id.konum_card_textview) ;
        meslek_card_textview = (TextView)findViewById(R.id.meslek_card_textview) ;
       isim_card_textview = (TextView)findViewById(R.id.isim_card_textview) ;
        soru_sor_ana_sayfa_button = (Button)findViewById(R.id.soru_sor_ana_sayfa_button);
        profil_foto_CircleImageView = (CircleImageView) findViewById(R.id.profil_foto_imageview);


       firebaseAuth = FirebaseAuth.getInstance();
       firebaseUser =  firebaseAuth.getCurrentUser();
       firebaseDatabase = FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference();
    }


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

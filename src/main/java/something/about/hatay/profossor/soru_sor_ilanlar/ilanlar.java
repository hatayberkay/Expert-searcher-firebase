package something.about.hatay.profossor.soru_sor_ilanlar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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

import java.util.ArrayList;

import something.about.hatay.profossor.R;
import something.about.hatay.profossor.mesajlasma_ve_konu_acma.konu_acma;

public class ilanlar extends AppCompatActivity {
    card_view_custom_ilanlar card_view_custom_ilanlar;
    ListView ilanlar_listview ;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference ;
    private static final String TAG = "ilanlar";


    ArrayList<String> yetenekler_array = new ArrayList<String>();
    ArrayList<String> profil_foto_bilgiler_uzman_kisiler_ilanlar_array = new ArrayList<String>();
    ArrayList<String> kullanıcı_array = new ArrayList<String>();
    ArrayList<String> isim_ve_soyadı_array = new ArrayList<String>();
    ArrayList<String> ileri_kullanıcı_array = new ArrayList<String>();
    ArrayList<String> sehirler_list_array = new ArrayList<String>();
    ArrayList<String> ileri_sehirler_list_array = new ArrayList<String>();
    ArrayList<String> aranan_position = new ArrayList<>();
    ArrayList<String> meslek_array = new ArrayList<>();
    ArrayList<String> ileri_meslek_array = new ArrayList<>();
    ArrayList<String> ileri_profil_foto_kisiler_ilanlar_array = new ArrayList<>();
    ArrayList<String> ileri_isim_ve_soyadı_array = new ArrayList<String>();
    ArrayList<String> ileri_profil_foto_bilgiler_uzman_kisiler_ilanlar_array= new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilanlar);


        tanımla();


        Bundle extras = getIntent().getExtras();
       final String ara_string = extras.getString("position");

    //  Toast.makeText(this, "" + ara_string, Toast.LENGTH_SHORT).show();



        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];




        databaseReference =  firebaseDatabase.getReference("kullanıcılar/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    //kullanıcı listesi
String key = child.getKey().toString();
                    kullanıcı_array.add(key);

                    String isim_string = child.child("bilgiler/"+"isim").getValue().toString();
                    kullanıcı_array.add(isim_string);



                    String fotograf_string = child.child("bilgiler/" +"fotograf").getValue().toString();

                    profil_foto_bilgiler_uzman_kisiler_ilanlar_array.add(fotograf_string);


                    String sehir_string = child.child("bilgiler/" +"sehir").getValue().toString();


                    sehirler_list_array.add(sehir_string);

                    String meslek_string = child.child("bilgiler/" +"meslek").getValue().toString();

                    meslek_array.add(meslek_string);


                   // String isim_string = child.child("bilgiler/" +"isim").getValue().toString();


                    String soyad_string = child.child("bilgiler/"+"soyad").getValue().toString();

isim_ve_soyadı_array.add(isim_string +" " + soyad_string);

String yetenek_bir_string = child.child("bilgiler/yetenek_bir").getValue(String.class);
String yetenek_iki_string = child.child("bilgiler/yetenek_iki").getValue(String.class);
String yetenek_uc_string = child.child("bilgiler/yetenek_uc").getValue(String.class);
String yetenek_dort_string = child.child("bilgiler/yetenek_dort").getValue(String.class);
String yetenek_bes_string = child.child("bilgiler/yetenek_bes").getValue(String.class);
String yetenek_altı_string = child.child("bilgiler/yetenek_altı").getValue(String.class);
String yetenek_yedi_string = child.child("bilgiler/yetenek_yedi").getValue(String.class);


yetenekler_array.add(yetenek_bir_string + yetenek_iki_string  + yetenek_uc_string + yetenek_dort_string + yetenek_bes_string + yetenek_altı_string + yetenek_yedi_string);


                }

                for (int i = 0; i < yetenekler_array.size(); i++) {
                    if (yetenekler_array.get(i).toString().contains(ara_string)){

                        aranan_position.add(String.valueOf(i));



                    }

                }


                for (String aa: aranan_position
                        ) {
                    ileri_meslek_array.add(meslek_array.get(Integer.parseInt(aa)).toString());
                    ileri_kullanıcı_array.add(kullanıcı_array.get(Integer.parseInt(aa)).toString());
                    ileri_profil_foto_kisiler_ilanlar_array.add(profil_foto_bilgiler_uzman_kisiler_ilanlar_array.get(Integer.parseInt(aa)).toString());
                    ileri_sehirler_list_array.add(sehirler_list_array.get(Integer.parseInt(aa)).toString());
ileri_isim_ve_soyadı_array.add(isim_ve_soyadı_array.get(Integer.parseInt(aa)).toString());
                    ileri_profil_foto_bilgiler_uzman_kisiler_ilanlar_array.add(profil_foto_bilgiler_uzman_kisiler_ilanlar_array.get(Integer.parseInt(aa)).toString());

                   // ileri_kullanıcı_array.add(kullanıcı_array.get(Integer.parseInt(aa)).toString());
                    ilanlar_listview.setAdapter(card_view_custom_ilanlar);






                }


                if (ileri_kullanıcı_array.size() == 0){
                    Toast.makeText(ilanlar.this, "Üzgünüz. Bu alanda hiç uzmanımız yok." , Toast.LENGTH_SHORT).show();

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ilanlar_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                if (!email_string_result.equals(ileri_kullanıcı_array.get(i).toString())) {
                    Intent konu_acma_intent = new Intent(getApplicationContext() , konu_acma.class);
                    konu_acma_intent.putExtra("isim" , ileri_kullanıcı_array.get(i).toString());
                    startActivity(konu_acma_intent);
                }else {

                    Toast.makeText(getApplicationContext(), "Kendi kendinize soru soramazsınız.", Toast.LENGTH_SHORT).show();

                }



            }
        });

    }

    private void tanımla() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        ilanlar_listview = (ListView)findViewById(R.id.ilanlar_listview);
        card_view_custom_ilanlar = new card_view_custom_ilanlar();
    }


    class card_view_custom_ilanlar extends BaseAdapter{
        @Override
        public int getCount() {
            return ileri_kullanıcı_array.size();
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
            view = getLayoutInflater().inflate(R.layout.card_view_custom_ilanlar, null);
           final TextView isim_soyadı_textview = view.findViewById(R.id.isim_soyadı_textview);
           TextView meslek_card_textview = view.findViewById(R.id.meslek_card_textview);
           TextView sehir_card_textview = view.findViewById(R.id.sehir_card_textview);
           ImageView profil_foto_imageview = view.findViewById(R.id.profil_foto_imageview);




isim_soyadı_textview.setText(ileri_isim_ve_soyadı_array.get(i).toString());
            meslek_card_textview.setText(ileri_meslek_array.get(i).toString());
            sehir_card_textview.setText(ileri_sehirler_list_array.get(i).toString());
            profil_foto_imageview.setImageBitmap(StringToBitMap(ileri_profil_foto_bilgiler_uzman_kisiler_ilanlar_array.get(i).toString()));



            return view;
        }
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

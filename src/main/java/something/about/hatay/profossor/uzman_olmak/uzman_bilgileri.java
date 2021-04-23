package something.about.hatay.profossor.uzman_olmak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import something.about.hatay.profossor.R;
import something.about.hatay.profossor.ana_sayfa_bottom_bar;

public class uzman_bilgileri extends AppCompatActivity  implements TextWatcher{
    Button ikinci_uzman_olarak_uye_ol_button;
    Spinner konu_baslıgı_birinci , konu_baslıgı_ikinci , konu_baslıgı_ucuncu , konu_baslıgı_dorduncu , konu_baslıgı_besıncı;
    Spinner konu_baslıgı_altıncı , konu_baslıgı_yedinci;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
    ArrayList<String> yenekler_array = new ArrayList<String>();
    private static final String[] items={"" ,"Web yazılım","Mobil uygulamalar","E-ticaret","Web Sitesine Mobil uygulama haline getirme","Domain & Hosting hizmetleri"
            ,"Son kullanıcı Testleri","Teknolojik destek","Maden Mühendisliği","Makatronik mühendisliği","Makine Mühendisiliği","Malzeme Bilimi ve mühendisliği",
            "Metalurji ve Malzeme Mühendisliği","Nanoteknoloji Mühendisliği","Nükkeer Enerji Mühendisliği","Orman Endüstri Mühendisliği","Cinsel Sağlık"
            ,"Göz Hastalıkları","İç Hastalıkları","Kadın Sağlığı","Kulak Burun Boğaz","Nöroloji","Ortapedi","Ruh sağlığı","Sindirim Sistemi","Temel Sağlık bilgisi" , "Moda Tasarım" , "Endustriyel Tasarım" , "Logo Tasarım"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uzman_bilgileri);
        tanımla();


        yenekler_array.add("Web yazılım");
        yenekler_array.add("Mobil uygulamalar");
        yenekler_array.add("E-ticaret");
        yenekler_array.add("Web Sitesine Mobil uygulama haline getirme");
        yenekler_array.add("Domain & Hosting hizmetleri");
        yenekler_array.add("Son kullanıcı Testleri");
        yenekler_array.add("Teknolojik destek");
        yenekler_array.add("Maden Mühendisliği");




        adapter_set_etme();
ikinci_uzman_olarak_uye_ol_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        String konu_baslıgı_birinci_string = konu_baslıgı_birinci.getSelectedItem().toString();

        
        if (!konu_baslıgı_birinci_string.isEmpty() && !konu_baslıgı_birinci_string.equals("")){



            String konu_baslıgı_ikinci_string = konu_baslıgı_ikinci.getSelectedItem().toString();
            String konu_baslıgı_ucuncu_string = konu_baslıgı_ucuncu.getSelectedItem().toString();
            String konu_baslıgı_dorduncu_string = konu_baslıgı_dorduncu.getSelectedItem().toString();
            String konu_baslıgı_besinci_string = konu_baslıgı_besıncı.getSelectedItem().toString();
            String konu_baslıgı_altıncı_string = konu_baslıgı_altıncı.getSelectedItem().toString();
            String konu_baslıgı_yedinci_string = konu_baslıgı_yedinci.getSelectedItem().toString();


            String[] separated = firebaseUser.getEmail().toString().split("@");
            final String firebase_usernames = separated[0] ;

            databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler/uzman");
            HashMap kimlik_ekle = new HashMap();

            kimlik_ekle.put("kimlik","uzman");
            kimlik_ekle.put("cevaplanan_soru","1");

            databaseReference.updateChildren(kimlik_ekle);

            databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler/" );
            HashMap yetenek_ekle = new HashMap();

            yetenek_ekle.put("yetenek_bir", konu_baslıgı_birinci_string);
            yetenek_ekle.put("yetenek_iki", konu_baslıgı_ikinci_string);
            yetenek_ekle.put("yetenek_uc", konu_baslıgı_ucuncu_string);
            yetenek_ekle.put("yetenek_dort", konu_baslıgı_dorduncu_string);
            yetenek_ekle.put("yetenek_bes", konu_baslıgı_besinci_string);
            yetenek_ekle.put("yetenek_altı", konu_baslıgı_altıncı_string);
            yetenek_ekle.put("yetenek_yedi", konu_baslıgı_yedinci_string);

            databaseReference.updateChildren(yetenek_ekle);







            Intent i = new Intent(getApplicationContext(),ana_sayfa_bottom_bar.class);
    startActivity(i);
    uzman_bilgileri.this.finish();
        
        
        
        
        }else if (konu_baslıgı_birinci_string.isEmpty()){

            Toast.makeText(uzman_bilgileri.this, "Mutlaka 1 tane yetenek girilmelidir.", Toast.LENGTH_SHORT).show();
        }

    }
});

    }

// hatayberkayis@gmail.com
    private void tanımla() {
//Button
        ikinci_uzman_olarak_uye_ol_button = (Button)findViewById(R.id.ikinci_uzman_olarak_uye_ol_button);
        //Edittextsler

        konu_baslıgı_birinci = (Spinner)findViewById(R.id.konu_baslıgı_birinci);
        // ikinci
        konu_baslıgı_ikinci = (Spinner)findViewById(R.id.konu_baslıgı_ikinci);
        // ücüncü
        konu_baslıgı_ucuncu = (Spinner)findViewById(R.id.konu_baslıgı_ucuncu);
        // dorduncu
        konu_baslıgı_dorduncu = (Spinner)findViewById(R.id.konu_baslıgı_dorduncu);
        // besinci
        konu_baslıgı_besıncı = (Spinner)findViewById(R.id.konu_baslıgı_besıncı);
        // altıncı
        konu_baslıgı_altıncı = (Spinner)findViewById(R.id.konu_baslıgı_altıncı);
        // yedinci
        konu_baslıgı_yedinci = (Spinner)findViewById(R.id.konu_baslıgı_yedinci);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    private void adapter_set_etme() {


        konu_baslıgı_birinci.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                items));


        konu_baslıgı_ikinci.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                items));


        konu_baslıgı_ucuncu.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                items));
        konu_baslıgı_dorduncu.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                items));
        konu_baslıgı_besıncı.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                items));
        konu_baslıgı_altıncı.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                items));
        konu_baslıgı_yedinci.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                items));




    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {




    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

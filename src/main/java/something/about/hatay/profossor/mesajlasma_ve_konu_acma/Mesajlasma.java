package something.about.hatay.profossor.mesajlasma_ve_konu_acma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import something.about.hatay.profossor.R;
import something.about.hatay.profossor.ana_sayfa_bottom_bar;
import something.about.hatay.profossor.model.CustomAdapter;
import something.about.hatay.profossor.model.Message;

public class Mesajlasma extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferences;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Button gonder_buttons;
    EditText editText_mesaj_yaz;
    ListView mesajlaşma_listview;
    ImageView ayarlar_imageview;
    private CustomAdapter customAdapter;
    private ArrayList<Message> chatLists = new ArrayList<>();
    private static final String TAG = "mesajlasma";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesajlasma);

        tanımla();
        Bundle bundle = getIntent().getExtras();

        final String kisi = bundle.getString("kimden");
        final String konu = bundle.getString("sira");
//hatayberkayis@gmail.com

        //      hatayberkay1234@gmail.com

        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];

        //   Toast.makeText(this, "" + kisi + " " + konu + " " + email_string_result, Toast.LENGTH_SHORT).show();
        listview_konusmaları_koy(email_string_result, kisi, konu);

        ayarlar_imageview = (ImageView) findViewById(R.id.ayarlar_imageview);

        // Log.i(TAG, "onCreate: " + getPackageName());










// TODO: 11.05.2019  Karşısı engellemiş ise edittext engelli oluyor. ( Engellenen )

        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/engellemiş_olanlar/" + kisi);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String fotograf_string = (String) dataSnapshot.getValue(String.class);

                if (fotograf_string != null && fotograf_string.equals(kisi)) {

                    editText_mesaj_yaz.setEnabled(false);
                    gonder_buttons.setEnabled(false);
                }
                Log.i(TAG, "onDataChange: " + fotograf_string);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


// TODO: 11.05.2019  Karşısı engellemiş ise edittext engelli oluyor. ( Engelleyenin )


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + kisi + "/engellemiş_olanlar/" + email_string_result);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String fotograf_string = (String) dataSnapshot.getValue(String.class);

                if (fotograf_string != null && fotograf_string.equals(email_string_result)) {

                    editText_mesaj_yaz.setEnabled(false);
                    gonder_buttons.setEnabled(false);
                }
                Log.i(TAG, "onDataChange: " + fotograf_string);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ayarlar_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mesajlasma_next_intent = new Intent(getApplicationContext(), mesajlasma_next.class);
                mesajlasma_next_intent.putExtra("kimden", kisi);
                mesajlasma_next_intent.putExtra("sira", konu);


                startActivity(mesajlasma_next_intent);


                Mesajlasma.this.finish();

            }
        });


        gonder_buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String push_key_string = firebaseDatabase.getReference().push().getKey();
                databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu + "/" + "baslangıc" + "mesaj/").child(push_key_string);
                databaseReferences = firebaseDatabase.getReference("kullanıcılar/" + kisi + "/mesajlar/" + kisi + "|" + email_string_result + "|" + konu + "/" + "baslangıc" + "mesaj/").child(push_key_string);


                String input_chat_string = editText_mesaj_yaz.getText().toString();
                long msTime = System.currentTimeMillis();
                Date curDateTime = new Date(msTime);
                SimpleDateFormat formatter = new SimpleDateFormat("dd'/'MM'/'y hh:mm");
                String dateTime = formatter.format(curDateTime);

                Message message = new Message(input_chat_string, email_string_result, dateTime);

                databaseReference.setValue(message);
                databaseReferences.setValue(message);

                editText_mesaj_yaz.setText("");


            }
        });
    }

    private void listview_konusmaları_koy(String email_string_result, String kisi, String konu) {

        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu + "/" + "baslangıc" + "mesaj/");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatLists.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    chatLists.add(message);
                    Log.i(TAG, "onDataChange: " + message);
                }
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        customAdapter = new CustomAdapter(getApplicationContext(), chatLists, firebaseUser);


        mesajlaşma_listview.setAdapter(customAdapter);


    }

    private void tanımla() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        gonder_buttons = (Button) findViewById(R.id.gonder_buttons);
        editText_mesaj_yaz = (EditText) findViewById(R.id.editText_mesaj_yaz);
        mesajlaşma_listview = (ListView) findViewById(R.id.mesajlaşma_listview);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(Mesajlasma.this, ana_sayfa_bottom_bar.class);
        startActivity(i);
        Mesajlasma.this.finish();
    }



}

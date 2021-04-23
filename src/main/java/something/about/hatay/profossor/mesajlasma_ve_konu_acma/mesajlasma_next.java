package something.about.hatay.profossor.mesajlasma_ve_konu_acma;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import something.about.hatay.profossor.R;
import something.about.hatay.profossor.ana_sayfa_bottom_bar;

public class mesajlasma_next extends AppCompatActivity {
    ImageView image_dort, image_uc, image_iki, image_bir;
    CircleImageView profil_foto_mesajlasma_next;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferences;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    TextView konu_textview_next, sorun_textview_next;
    Button sorumu_cevapladı, engelle;
    private static final String TAG = "mesajlasma_next";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesajlasma_next);


        tanımla();

        Bundle bundle = getIntent().getExtras();

        final String kisi = bundle.getString("kimden");
        final String konu = bundle.getString("sira");


        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];

        yerine_koyma(email_string_result, kisi, konu);


        sorun_textview_next.setMovementMethod(new ScrollingMovementMethod());

        // databaseReference = firebaseDatabase.getReference("kullanıcılar/" + kisi + "/bilgiler/" +"/uzman/"+ "cevaplanan_soru");
        // databaseReference.setValue("1");


        sorumu_cevapladı.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu + "/baslangıc/" + "from");


                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String from_string = (String) dataSnapshot.getValue(String.class);
                        if (from_string.equals(email_string_result)) {


                            new AlertDialog.Builder(mesajlasma_next.this)
                                    .setTitle("Sorunuzu Cevaplandırdı mı ?")
                                    .setMessage(" Uzmanımızın sorunuzu cevvaplandırdığını düşünüyorsanız lütfen 'Tamam' butonuna basınız.")
                                    .setPositiveButton(
                                            "Tamam", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {


                                                    new AlertDialog.Builder(mesajlasma_next.this)
                                                            .setTitle("Emin misiniz ?")
                                                            .setMessage("Gerçekten sorunuzu cevaplandırdığını düşünüyor musunuz? 'Evet' butonuza bastıktan sonra" +
                                                                    "bütün konuşmalarınız silinecektir. ")
                                                            .setPositiveButton(
                                                                    "Tamam", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {


                                                                            databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu);
                                                                            databaseReferences = firebaseDatabase.getReference("kullanıcılar/" + kisi + "/mesajlar/" + kisi + "|" + email_string_result + "|" + konu);

                                                                            databaseReference.removeValue();
                                                                            databaseReferences.removeValue();


                                                                            Intent i = new Intent(getApplicationContext(), ana_sayfa_bottom_bar.class);
                                                                            startActivity(i);
                                                                            mesajlasma_next.this.finish();


                                                                        }
                                                                    })
                                                            .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                                    dialogInterface.dismiss();
                                                                }
                                                            }).show();


                                                }
                                            })
                                    .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            dialogInterface.dismiss();
                                        }
                                    })

                                    .show();

                        } else {
                            Toast.makeText(mesajlasma_next.this, "Sadece soruyu soran soruyu cevaplandırdığını belirtiğinde kullanılabilir.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

// TODO: 11.05.2019 Engelleme işlemleri.

        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + kisi + "/engellemiş_olanlar/" + email_string_result);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String fotograf_string = (String) dataSnapshot.getValue(String.class);

                if (fotograf_string != null && fotograf_string.equals(email_string_result)) {

                    engelle.setText("Engellemeyi Kaldır.");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        engelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (engelle.getText().toString().equals("Engelle")) {
                    databaseReference = firebaseDatabase.getReference("kullanıcılar/" + kisi + "/engellemiş_olanlar/").child(email_string_result);
                    databaseReference.setValue(email_string_result);
                    Intent i = new Intent(getApplicationContext(), ana_sayfa_bottom_bar.class);
                    mesajlasma_next.this.finish();
                    startActivity(i);


                } else {

                    databaseReference = firebaseDatabase.getReference("kullanıcılar/" + kisi + "/engellemiş_olanlar/").child(email_string_result);
                    databaseReference.removeValue();
                    Intent i = new Intent(getApplicationContext(), ana_sayfa_bottom_bar.class);
                    mesajlasma_next.this.finish();
                    startActivity(i);
                    engelle.setText("Engellemeyi Kaldır.");


                }

            }
        });


    }


    private void tanımla() {
        image_dort = (ImageView) findViewById(R.id.image_dort);
        image_uc = (ImageView) findViewById(R.id.image_uc);
        image_iki = (ImageView) findViewById(R.id.image_iki);
        image_bir = (ImageView) findViewById(R.id.image_bir);
        engelle = (Button) findViewById(R.id.engelle);
        sorumu_cevapladı = (Button) findViewById(R.id.sorumu_cevapladı);


        profil_foto_mesajlasma_next = (CircleImageView) findViewById(R.id.profil_foto_mesajlasma_next);

        konu_textview_next = (TextView) findViewById(R.id.konu_textview_next);
        sorun_textview_next = (TextView) findViewById(R.id.sorun_textview_next);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReferences = firebaseDatabase.getReference();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Bundle bundle = getIntent().getExtras();

        final String kisi = bundle.getString("kimden");
        final String konu = bundle.getString("sira");


        Intent Mesajlasma = new Intent(getApplicationContext(), something.about.hatay.profossor.mesajlasma_ve_konu_acma.Mesajlasma.class);
        Mesajlasma.putExtra("kimden", kisi);
        Mesajlasma.putExtra("sira", konu);

        startActivity(Mesajlasma);
        mesajlasma_next.this.finish();

    }


    private void yerine_koyma(String email_string_result, String kisi, final String konu) {


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + kisi + "/bilgiler/" + "fotograf");


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String fotograf_string = (String) dataSnapshot.getValue();

                profil_foto_mesajlasma_next.setImageBitmap(StringToBitMap(fotograf_string));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu + "/baslangıc/" + "konu");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String konu_string = (String) dataSnapshot.getValue();


                konu_textview_next.setText(konu_string);
                Log.i(TAG, "onDataChange: " + konu_string);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu + "/baslangıc/" + "sorun");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String konu_string = (String) dataSnapshot.getValue();


                sorun_textview_next.setText(konu_string);
                Log.i(TAG, "onDataChange: " + konu_string);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        fotograflar(email_string_result, kisi, konu);


    }

    private void fotograflar(String email_string_result, String kisi, final String konu) {

        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu + "/baslangıc/" + "fotograf_1");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String konu_string = (String) dataSnapshot.getValue();


                if (konu_string != null) {

                    image_bir.setImageBitmap(StringToBitMap(konu_string));
                } else {

                    image_iki.setEnabled(false);
                }

                Log.i(TAG, "onDataChange: " + konu_string);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu + "/baslangıc/" + "fotograf_2");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String konu_string = (String) dataSnapshot.getValue();


                if (konu_string != null) {

                    image_iki.setImageBitmap(StringToBitMap(konu_string));
                } else {

                    image_iki.setEnabled(false);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu + "/baslangıc/" + "fotograf_3");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String konu_string = (String) dataSnapshot.getValue();


                if (konu_string != null) {

                    image_uc.setImageBitmap(StringToBitMap(konu_string));
                } else {

                    image_uc.setEnabled(false);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result + "|" + kisi + "|" + konu + "/baslangıc/" + "fotograf_4");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String konu_string = (String) dataSnapshot.getValue();


                if (konu_string != null) {

                    image_dort.setImageBitmap(StringToBitMap(konu_string));
                } else {

                    image_dort.setEnabled(false);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        tıklayınca();

    }

    private void tıklayınca() {
        image_bir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


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
}

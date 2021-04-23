package something.about.hatay.profossor.uyelik_ve_giris;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import something.about.hatay.profossor.R;
import something.about.hatay.profossor.uye_girisi;

public class üye_ol extends AppCompatActivity {
EditText email_edittext , sifre_edittext , sifre_tekrar_edittext ;
Button üye_ol_buttonum ;


FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;

FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uye_ol);

tanımla();

firebaseAuth = FirebaseAuth.getInstance();

firebaseUser = firebaseAuth.getCurrentUser();

firebaseDatabase = FirebaseDatabase.getInstance();



        üye_ol_buttonum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email_edittext_string = email_edittext.getText().toString();
                final String sifre_edittext_string = sifre_edittext.getText().toString();
                final String sifre_tekrar_edittext_string = sifre_tekrar_edittext.getText().toString();

                if (sifre_edittext_string.equals(sifre_tekrar_edittext_string)){
                    firebaseAuth.createUserWithEmailAndPassword(email_edittext_string,sifre_edittext_string).addOnCompleteListener(üye_ol.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()){
                                Toast.makeText(üye_ol.this, "Başarılı. E-maillinizi onaylayınız", Toast.LENGTH_SHORT).show();





                                String[] separated = email_edittext_string.split("@");
                                final String firebase_usernames = separated[0] ;


                                databaseReference = firebaseDatabase.getReference("kullanıcılar/" + firebase_usernames + "/bilgiler");


                                HashMap kullanıcı_kaydı_hashmap = new HashMap();

                                kullanıcı_kaydı_hashmap.put("E-mail", firebase_usernames);
                                kullanıcı_kaydı_hashmap.put("isim", "");
                                kullanıcı_kaydı_hashmap.put("soyad", "");
                                kullanıcı_kaydı_hashmap.put("Kullanıcı_adı", "");
                                kullanıcı_kaydı_hashmap.put("fotograf", "");
                                kullanıcı_kaydı_hashmap.put("meslek", "");
                                kullanıcı_kaydı_hashmap.put("sehir", "");
                                kullanıcı_kaydı_hashmap.put("egitim", "");
                                kullanıcı_kaydı_hashmap.put("hakkında", "");
                                kullanıcı_kaydı_hashmap.put("uyelik", "false");
                                databaseReference.updateChildren(kullanıcı_kaydı_hashmap);

                                try {
                                    firebaseUser.sendEmailVerification();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Intent i = new Intent(getApplicationContext(),uye_girisi.class);
                                startActivity(i);
                                üye_ol.this.finish();


                            }else {
                                Toast.makeText(üye_ol.this, "Fail", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
                }else {

                    Toast.makeText(üye_ol.this, "sifreler esit degil", Toast.LENGTH_SHORT).show();
                }


                
                

            }
        });

        // TODO: 30.01.2019 Dillere göre yeniden yazılacak 1 . Gmail 2. sifre 3. sifre tekrar






    }

    private void tanımla() {
        email_edittext = (EditText)findViewById(R.id.email_edittext);
        sifre_edittext = (EditText)findViewById(R.id.sifre_edittext);
        sifre_tekrar_edittext = (EditText)findViewById(R.id.sifre_tekrar_edittext);
        üye_ol_buttonum = (Button)findViewById(R.id.üye_ol_buttonum);


    }
}

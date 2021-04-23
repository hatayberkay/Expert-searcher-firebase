package something.about.hatay.profossor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import something.about.hatay.profossor.bilgialimi.hosgeldiniz;
import something.about.hatay.profossor.uyelik_ve_giris.sifremi_unuttum;
import something.about.hatay.profossor.uyelik_ve_giris.üye_ol;

public class uye_girisi extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String TAG = "uye_girisi";
    private static final int RC_SIGN_IN = 9001;
    EditText email_edittext , parola_edittext ;
    Button giris_yap_button ,üye_ol_button;
    TextView sifreni_mi_unuttun_textview ,onay_mailini_tekrar_gonder_textview;
    SignInButton signInButton;
    //Firebase baslıkları
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    DatabaseReference databaseReference;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_girisi);

        tanımla();


        
        if (firebaseAuth.getCurrentUser() != null && firebaseUser.isEmailVerified() == true){

            Intent uye_ol_intent = new Intent(getApplicationContext(),ana_sayfa_bottom_bar.class);
            startActivity(uye_ol_intent);
            uye_girisi.this.finish();

        }
        
        


        giris_yap_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giris_yap();
            }


        });


        üye_ol_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gonder();
            }


        });

        onay_mailini_tekrar_gonder_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUser.sendEmailVerification();
            }
        });

sifreni_mi_unuttun_textview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(getApplicationContext(),sifremi_unuttum.class);
        startActivity(i);
    }
});


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }






    private void tanımla() {
        email_edittext = (EditText) findViewById(R.id.email_edittext);
        parola_edittext = (EditText) findViewById(R.id.parola_edittext);
        giris_yap_button = (Button) findViewById(R.id.giris_yap_button);
        üye_ol_button = (Button) findViewById(R.id.üye_ol_button);
        sifreni_mi_unuttun_textview = (TextView) findViewById(R.id.sifreni_mi_unuttun_textview);
        onay_mailini_tekrar_gonder_textview = (TextView) findViewById(R.id.onay_mailini_tekrar_gonder_textview);



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


    }

    private void gonder() {
        Intent uye_ol_intent = new Intent(getApplicationContext(),üye_ol.class);
        startActivity(uye_ol_intent);
        uye_girisi.this.finish();

     
     
       
    }


    private void giris_yap() {
        final String email_string = email_edittext.getText().toString();
        String sifre_string = parola_edittext.getText().toString();
if (!email_string.isEmpty() && !sifre_string.isEmpty()){
    firebaseAuth.signInWithEmailAndPassword(email_string,sifre_string).addOnCompleteListener(uye_girisi.this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

if (task.isSuccessful()){
    String[] separated = email_string.split("@");
    final String firebase_usernames = separated[0] ;




    databaseReference = database.getReference("kullanıcılar/"+ firebase_usernames +"/bilgiler" + "/uyelik");
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String uyelik_string = (String) dataSnapshot.getValue();


            if (uyelik_string.equals("false")){

                Intent uye_ol_intent = new Intent(getApplicationContext(),hosgeldiniz.class);
                startActivity(uye_ol_intent);
                uye_girisi.this.finish();
            }
            else {
                Intent uye_ol_intent = new Intent(getApplicationContext(),ana_sayfa_bottom_bar.class);
                startActivity(uye_ol_intent);
                uye_girisi.this.finish();


            }


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });


}

        }
    });
}else {

    Toast.makeText(this, "Lütfen alanları doldurunuz.", Toast.LENGTH_SHORT).show();

}



    }




}

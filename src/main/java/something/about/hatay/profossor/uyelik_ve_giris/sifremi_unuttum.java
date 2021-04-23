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
import com.google.firebase.auth.FirebaseAuth;

import something.about.hatay.profossor.R;
import something.about.hatay.profossor.uye_girisi;

public class sifremi_unuttum extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button gonder_button;
    EditText email_reset_edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);
        firebaseAuth = FirebaseAuth.getInstance();
        tanımla();


        gonder_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e_mail_string = email_reset_edittext.getText().toString();
                if (!e_mail_string.isEmpty())
                    
                {
                    reset_mail_password();
                    Intent i = new Intent(getApplicationContext(),uye_girisi.class);
                    startActivity(i);
                    sifremi_unuttum.this.finish();



                }
                    
                    if (e_mail_string.isEmpty()){

                        Toast.makeText(sifremi_unuttum.this, "Lütfen E- mail adresinizi yazınız.", Toast.LENGTH_SHORT).show();
                    }
                
            }
        });
        

    }


    private void tanımla() {
        gonder_button = (Button)findViewById(R.id.gonder_button);
        email_reset_edittext = (EditText)findViewById(R.id.email_reset_edittext);

    }

    private void reset_mail_password() {
        String e_mail_string = email_reset_edittext.getText().toString();
        try {
            FirebaseAuth.getInstance().sendPasswordResetEmail(e_mail_string).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(sifremi_unuttum.this, "Sıfırlama mailiniz başarıyla gönderildi.", Toast.LENGTH_SHORT).show();
                        email_reset_edittext.setText("");

                    }else {
                        Toast.makeText(sifremi_unuttum.this, "Sıfırlama mailiniz başarısız oldu.", Toast.LENGTH_SHORT).show();
                        email_reset_edittext.setText("");
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(sifremi_unuttum.this, "Sıfırlama mailiniz başarısız oldu.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),uye_girisi.class);
            startActivity(i);
            sifremi_unuttum.this.finish();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sifremi_unuttum.this.finish();
    }
}

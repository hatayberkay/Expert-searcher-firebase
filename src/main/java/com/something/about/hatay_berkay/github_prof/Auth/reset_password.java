package com.something.about.hatay_berkay.github_prof.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.something.about.hatay_berkay.github_prof.R;
import com.something.about.hatay_berkay.github_prof.enter_page;

public class reset_password extends AppCompatActivity {
private EditText password_rest_edittext;
    private Button reset_button;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseAuth = FirebaseAuth.getInstance();
        definitions();

        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_mail_string = password_rest_edittext.getText().toString();
                if (!e_mail_string.isEmpty())

                {
                    reset_mail_password(e_mail_string);
                    Intent i = new Intent(getApplicationContext(), enter_page.class);
                    startActivity(i);
                    reset_password.this.finish();



                }

                if (e_mail_string.isEmpty()){

                    Toast.makeText(reset_password.this, "Your e-mail adress box is empty!!!", Toast.LENGTH_SHORT).show();
                }




    }
        });


    }

    private void definitions() {
        password_rest_edittext = (EditText)findViewById(R.id.password_rest_edittext);
        reset_button = (Button)findViewById(R.id.reset_button);


    }

    private void reset_mail_password(String e_mail_string) {

        try {
            FirebaseAuth.getInstance().sendPasswordResetEmail(e_mail_string).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Succesful.", Toast.LENGTH_SHORT).show();
                        reset_button.setText("");

                    }else {
                        Toast.makeText(getApplicationContext(), "Reset job has denied..", Toast.LENGTH_SHORT).show();
                        reset_button.setText("");
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mail has denied...", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),enter_page.class);
            startActivity(i);
            reset_password.this.finish();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


            Intent intent = new Intent(getApplicationContext(), enter_page.class);
            startActivity(intent);
            reset_password.this.finish();
    }
}



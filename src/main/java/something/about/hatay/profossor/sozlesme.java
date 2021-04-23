package something.about.hatay.profossor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class sozlesme extends AppCompatActivity {
    CheckBox checkbox;
    Button button;
    TextView textView;
    String deger = "yanlıs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sozlesme);


        checkbox = (CheckBox)findViewById(R.id.checkbox);

        button = (Button)findViewById(R.id.uygulamaya_basla_button) ;

        textView = (TextView)findViewById(R.id.text);

        textView.setMovementMethod(new ScrollingMovementMethod());


      //  Toast.makeText(this, "Lütfen gizlilik sözleşmesini okuyup onayladıktan sonra 'Uygulamaya Başla' butonuna dokununz. ", Toast.LENGTH_SHORT).show();

        SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);

        String old = settings.getString("selam", deger);


        if (old.equals("dogru") && old != null){

            Intent i = new Intent(getApplicationContext(),uye_girisi.class);

            sozlesme.this.finish();

            startActivity(i);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkbox.isChecked()){

                    Intent i = new Intent(getApplicationContext(),uye_girisi.class);
                    sozlesme.this.finish();
                    startActivity(i);


                    SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();

                    editor.putString( "selam" , "dogru");

                    editor.commit();



                }else {

                    Toast.makeText(sozlesme.this, "Lütfen kullanıcı sözleşmesini kabul ediniz.", Toast.LENGTH_SHORT).show();
                }





            }
        });





    }
}

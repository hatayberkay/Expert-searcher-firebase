package something.about.hatay.profossor.bilgialimi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import something.about.hatay.profossor.R;

public class hosgeldiniz extends AppCompatActivity {
Button ileri_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosgeldiniz);
        ileri_button = (Button)findViewById(R.id.ileri_button);


        ileri_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startdart = new Intent(getApplicationContext(),standart.class);
                startActivity(startdart);
                hosgeldiniz.this.finish();
            }
        });

    }
}

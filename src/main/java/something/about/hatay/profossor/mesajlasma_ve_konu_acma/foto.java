package something.about.hatay.profossor.mesajlasma_ve_konu_acma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import something.about.hatay.profossor.R;

public class foto extends AppCompatActivity {
ImageView imageview_foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);


        imageview_foto = (ImageView)findViewById(R.id.imageview_foto);
    }
}

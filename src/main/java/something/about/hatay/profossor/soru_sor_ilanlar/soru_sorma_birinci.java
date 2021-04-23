package something.about.hatay.profossor.soru_sor_ilanlar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import something.about.hatay.profossor.R;

public class soru_sorma_birinci extends AppCompatActivity {
    ListView soru_sorma_birinci_listview ;
    ArrayList<String> strings = new ArrayList<>();
    ArrayAdapter adapter;
    ImageView birinci_imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_sorma_birinci);


        Bundle bundle = getIntent().getExtras();

        final String s = bundle.getString("gonder");

        soru_sorma_birinci_listview = (ListView)findViewById(R.id.soru_sorma_listview);
        birinci_imageview = (ImageView)findViewById(R.id.birinci_imageview);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,  strings);

        if (s.equals("Sağlık")){
            strings.clear();
            strings.add("Cinsel Sağlık");
            strings.add("Göz Hastalıkları");
            strings.add("İç Hastalıkları");
            strings.add("Kadın Sağlığı");
            strings.add("Kulak Burun Boğaz");
            strings.add("Nöroloji");
            strings.add("Ortapedi");
            strings.add("Ruh sağlığı ");
            strings.add("Sindirim Sistemi ");
            birinci_imageview.setImageResource(R.drawable.health);

        }

        if (s.equals("Mühendislik")){
            strings.clear();
            strings.add("Maden Mühendisliği");
            strings.add("Makatronik mühendisliği");
            strings.add("Makine Mühendisiliği");
            strings.add("Malzeme Bilimi ve mühendisliği");
            strings.add("Metalurji ve Malzeme Mühendisliği");
            strings.add("Nanoteknoloji Mühendisliği");
            strings.add("Nükkeer Enerji Mühendisliği");
            strings.add("Orman Endüstri Mühendisliği");
            birinci_imageview.setImageResource(R.drawable.enginer);

        }

        if (s.equals("Tasarım")){
            strings.clear();
            strings.add("Moda Tasarım");
            strings.add("Endustriyel Tasarım");
            strings.add("Logo Tasarım");
            birinci_imageview.setImageResource(R.drawable.desing);

        }

        if (s.equals("Yazılım")){
            strings.clear();
            strings.add("Web yazılım");
            strings.add("Mobil uygulamalar");
            strings.add("E-ticaret");
            strings.add("Web Sitesine Mobil uygulama haline getirme");
            strings.add("Domain & Hosting hizmetleri");
            strings.add("Son kullanıcı Testleri");
            strings.add("Teknolojik destek");

            birinci_imageview.setImageResource(R.drawable.software);
        }







        soru_sorma_birinci_listview.setAdapter(adapter);

        soru_sorma_birinci_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String positon_string  =  strings.get(i).toString();


                Intent ilanlar_gonder = new Intent(getApplicationContext() , ilanlar.class);
                ilanlar_gonder.putExtra("position" , positon_string);
                startActivity(ilanlar_gonder);
                soru_sorma_birinci.this.finish();




            }
        });

    }


}

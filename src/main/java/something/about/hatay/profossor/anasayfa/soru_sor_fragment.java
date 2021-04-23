package something.about.hatay.profossor.anasayfa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import something.about.hatay.profossor.R;
import something.about.hatay.profossor.soru_sor_ilanlar.soru_sorma_birinci;

public class soru_sor_fragment extends Fragment  {
    card_view_custom_view_soru_sor_fragment card_view_custom_view_soru_sor_fragment ;
    ArrayList<String> katagoroler = new ArrayList<>();
    TextView konu_textview_fragment;
    GridView gridView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private static final String TAG = "soru_sor_fragment" ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_soru_sor_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        card_view_custom_view_soru_sor_fragment = new card_view_custom_view_soru_sor_fragment();
        katagoroler.add("Sağlık");
        katagoroler.add("Mühendislik");
        katagoroler.add("Tasarım");
        katagoroler.add("Yazılım");


        tanımla();












        gridView.setAdapter(card_view_custom_view_soru_sor_fragment);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent first = new Intent(getContext(),soru_sorma_birinci.class);
                        first.putExtra("gonder", katagoroler.get(0));
                        startActivity(first);
                        break;
                    case 1:
                        Intent second = new Intent(getContext(),soru_sorma_birinci.class);
                        second.putExtra("gonder", katagoroler.get(1));
                        startActivity(second);
                        break;
                    case 2:
                        Intent third = new Intent(getContext(),soru_sorma_birinci.class);
                        third.putExtra("gonder", katagoroler.get(2));
                        startActivity(third);


                        break;
                    case 3:
                        Intent forth = new Intent(getContext(),soru_sorma_birinci.class);
                        forth.putExtra("gonder", katagoroler.get(3));
                        startActivity(forth);

                        break;

                }


            }
        });



        super.onActivityCreated(savedInstanceState);
    }

    private void tanımla() {
         gridView = (GridView)getView().findViewById(R.id.genel_uzmanlıklar_gridview);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }




    class card_view_custom_view_soru_sor_fragment extends BaseAdapter {


        @Override
        public int getCount() {

            return katagoroler.size();
        }

        @Override
        public Object getItem(int i) {
            return katagoroler.size();
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.card_view_custom_view_soru_sor_fragment, null);
            konu_textview_fragment = (TextView) view.findViewById(R.id.konu_textview_fragment_ssss);
            konu_textview_fragment.setText(katagoroler.get(i).toString());

            return view;
        }
    }


}

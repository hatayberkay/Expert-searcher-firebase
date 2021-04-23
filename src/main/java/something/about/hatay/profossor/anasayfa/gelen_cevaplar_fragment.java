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
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import something.about.hatay.profossor.R;
import something.about.hatay.profossor.mesajlasma_ve_konu_acma.Mesajlasma;

public class gelen_cevaplar_fragment extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public static final String TAG = "GELEN";
    custom_gelen_cevaplar custom_gelen_cevaplar;
    ListView listView;
    ArrayList<String> kisi_array = new ArrayList<String>();
    ArrayList<String> sorun_array = new ArrayList<String>();
    ArrayList<String> gonderen_array = new ArrayList<String>();
    ArrayList<String> karsı_array = new ArrayList<String>();
    ArrayList<String> konu_array = new ArrayList<String>();
    ArrayList<String> to_array = new ArrayList<String>();
    InterstitialAd mInterstitialAd;
     String firebase_usernames;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_gelen_cevaplar_fragment,container,false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    //  String[] separated = firebaseUser.getEmail().toString().split("@");
      // final String firebase_usernames = separated[0] ;

        final String user_name_string = getArguments().getString("user_name");
tanımla();
        custom_gelen_cevaplar = new custom_gelen_cevaplar();

        listView = (ListView) getView().findViewById(R.id.listview);


        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];


        mInterstitialAd = new InterstitialAd(getContext());



        mInterstitialAd.setAdUnitId("Add id"); //reklam id



        requestNewInterstitial();




        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + user_name_string + "/mesajlar");

databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        for (DataSnapshot child : dataSnapshot.getChildren()){

            String from_string = child.child("baslangıc/from").getValue(String.class);
            String konu_string = child.child("baslangıc/konu").getValue(String.class);
            String to_string = child.child("baslangıc/to").getValue(String.class);
            String sorun_string = child.child("baslangıc/sorun").getValue(String.class);


            String gonderen_string = child.child("baslangıc/gonderenin").getValue(String.class);
            String kullanıcı_adı_karsı_string = child.child("baslangıc/karsı").getValue(String.class);

            kisi_array.add(from_string);
            sorun_array.add(sorun_string);

to_array.add(to_string);

            gonderen_array.add(gonderen_string);

            karsı_array.add(kullanıcı_adı_karsı_string);


            for (int i = 0; i <kisi_array.size() ; i++) {

                if (user_name_string.equals(kisi_array.get(i).toString()) ){
                    gonderen_array.set(i,karsı_array.get(i).toString());


                }



            }
            for (int i = 0; i <to_array.size() ; i++) {
                if (user_name_string.equals(kisi_array.get(i).toString())){
                    kisi_array.set(i,to_array.get(i).toString());

                }
            }





            konu_array.add(konu_string);

        }
        listView.setAdapter(custom_gelen_cevaplar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                if (mInterstitialAd.isLoaded()) { //reklam yüklenmişse
                    mInterstitialAd.show(); //reklam gösteriliyor
                } else {
                    //Reklam yüklenmediyse yapılacak işlemler


                    Intent mesajlasma_intent = new Intent(getContext(), Mesajlasma.class);
                    mesajlasma_intent.putExtra("kimden" ,kisi_array.get(i).toString() );
                    mesajlasma_intent.putExtra("sira" ,"" + konu_array.get(i).toString() );
                    startActivity(mesajlasma_intent);
                    getActivity().finish();

                }

                mInterstitialAd.setAdListener(new AdListener() { //reklamımıza listener ekledik ve kapatıldığında haberimiz olacak
                    @Override
                    public void onAdClosed() { //reklam kapatıldığı zaman tekrardan reklamın yüklenmesi için

                        Intent mesajlasma_intent = new Intent(getContext(), Mesajlasma.class);
                        mesajlasma_intent.putExtra("kimden" ,kisi_array.get(i).toString() );
                        mesajlasma_intent.putExtra("sira" ,"" + konu_array.get(i).toString() );
                        startActivity(mesajlasma_intent);
                        getActivity().finish();



                    }
                });




            }
        });

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});







    }

    private void tanımla() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    class  custom_gelen_cevaplar extends BaseAdapter {


        @Override
        public int getCount() {
            return gonderen_array.size();
        }

        @Override
        public Object getItem(int i) {
            return gonderen_array.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_gelen_cevaplar, null);
            TextView kullanıcı_adı_textview = (TextView) view.findViewById(R.id.kullanıcı_adı_textview);
            TextView konu_textview = (TextView) view.findViewById(R.id.konu_textview);
            TextView son_mesaj_textview = (TextView) view.findViewById(R.id.son_mesaj_textview);



            kullanıcı_adı_textview.setText(gonderen_array.get(i).toString());
           // kullanıcı_adı_textview.setText(gonderen_array.get(i).toString());
            konu_textview.setText("Konu : " +" " +konu_array.get(i).toString());




            return view;

        }
    }

    private void requestNewInterstitial() { //Test cihazı ekliyoruz Admob dan ban yememek için
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("Special İd")

                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}

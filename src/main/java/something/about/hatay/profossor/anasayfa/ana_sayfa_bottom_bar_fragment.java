package something.about.hatay.profossor.anasayfa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import something.about.hatay.profossor.InstanceIdService;
import something.about.hatay.profossor.R;
import something.about.hatay.profossor.ana_sayfa_next;

public class ana_sayfa_bottom_bar_fragment extends Fragment {
    ArrayList<String> kisi_array = new ArrayList<String>();
    ArrayList<String> id_array = new ArrayList<String>();
    ArrayList<String> fotograf_array = new ArrayList<String>();
    ArrayList<String> meslek_array = new ArrayList<String>();
    ArrayList<String> isim_array = new ArrayList<String>();
    custom_ana_sayfa custom_ana_sayfa;
GridView vitrin_gridview;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
    private static final String TAG = "ana_sayfa_fragment" ;
FirebaseAuth firebaseAuth;
FirebaseUser firebaseUser;
   InterstitialAd mInterstitialAd;

    InstanceIdService ınstanceIdService;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ana_sayfa_bottom_bar_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        tanımla();


        ınstanceIdService = new InstanceIdService();

        ınstanceIdService.onTokenRefresh();

        final custom_ana_sayfa custom_ana_sayfa = new  custom_ana_sayfa();


        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];


        mInterstitialAd = new InterstitialAd(getContext());


        mInterstitialAd.setAdUnitId("Special İd"); //reklam id




        requestNewInterstitial();









        databaseReference = firebaseDatabase.getReference("kullanıcılar/");

databaseReference.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {







    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {




        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

            id_array.clear();
            kisi_array.clear();
            fotograf_array.clear();
            meslek_array.clear();
            //  String uzman_string = snapshot.getKey();


            String uzman_mı_string = snapshot.child("bilgiler/" + "uzman/kimlik").getValue(String.class);
            String e_mail_string = snapshot.child("bilgiler/" + "E-mail").getValue(String.class);
            String kullanıcı_adı_string = snapshot.child("bilgiler/" + "Kullanıcı_adı").getValue(String.class);
            String fotograf_string = snapshot.child("bilgiler/" + "fotograf").getValue(String.class);
            String meslek_string = snapshot.child("bilgiler/" + "meslek").getValue(String.class);
            String isim_string = snapshot.child("bilgiler/"+"isim").getValue(String.class);

            if (uzman_mı_string != null) {


                id_array.add(e_mail_string);
                kisi_array.add(kullanıcı_adı_string);
                fotograf_array.add(fotograf_string);
                meslek_array.add(meslek_string);
isim_array.add(isim_string);



                vitrin_gridview.setAdapter(custom_ana_sayfa);


            }
        }




    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
           for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


             //  String uzman_string = snapshot.getKey();




               String uzman_mı_string = snapshot.child("bilgiler/"+"uzman/kimlik").getValue(String.class);
               String e_mail_string = snapshot.child("bilgiler/"+"E-mail").getValue(String.class);
               String kullanıcı_adı_string = snapshot.child("bilgiler/"+"Kullanıcı_adı").getValue(String.class);
               String fotograf_string = snapshot.child("bilgiler/"+"fotograf").getValue(String.class);
               String meslek_string = snapshot.child("bilgiler/"+"meslek").getValue(String.class);
               String isim_string = snapshot.child("bilgiler/"+"isim").getValue(String.class);

if (uzman_mı_string != null){


    id_array.add(e_mail_string);
    kisi_array.add(kullanıcı_adı_string);
    fotograf_array.add(fotograf_string);
    meslek_array.add(meslek_string);
isim_array.add(isim_string);




    vitrin_gridview.setAdapter(custom_ana_sayfa);


}


           }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








        vitrin_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                if (!email_string_result.equals(id_array.get(i).toString())) {
                    Intent next_intent = new Intent(getActivity(), ana_sayfa_next.class);
                    next_intent.putExtra("ids", id_array.get(i).toString());
                    startActivity(next_intent);
                }else {

                    Toast.makeText(getContext(), "Kendi kendinize soru soramazsınız.", Toast.LENGTH_SHORT).show();

                }


               /* if (mInterstitialAd.isLoaded()) { //reklam yüklenmişse
                    mInterstitialAd.show(); //reklam gösteriliyor
                }else{


                    if (!email_string_result.equals(id_array.get(i).toString())) {
                        Intent next_intent = new Intent(getActivity(), ana_sayfa_next.class);
                        next_intent.putExtra("ids", id_array.get(i).toString());
                        startActivity(next_intent);
                    }else {

                        Toast.makeText(getContext(), "Kendi kendinize soru soramazsınız.", Toast.LENGTH_SHORT).show();

                    }
                    //Reklam yüklenmediyse yapılacak işlemler
                }

                mInterstitialAd.setAdListener(new AdListener() { //reklamımıza listener ekledik ve kapatıldığında haberimiz olacak
                    @Override
                    public void onAdClosed() { //reklam kapatıldığı zaman tekrardan reklamın yüklenmesi için
                        // requestNewInterstitial();


                  *//*      if (!email_string_result.equals(id_array.get(i).toString())) {
                            Intent next_intent = new Intent(getActivity(), ana_sayfa_next.class);
                            next_intent.putExtra("ids", id_array.get(i).toString());
                            startActivity(next_intent);
                        }else {

                            Toast.makeText(getContext(), "Kendi kendinize soru soramazsınız.", Toast.LENGTH_SHORT).show();

                        }
*//*
                    }
                });
*/
            //    Toast.makeText(getContext(), "" + id_array.size(), Toast.LENGTH_SHORT).show();


            }
        });


        super.onActivityCreated(savedInstanceState);
    }

    private void tanımla() {
        vitrin_gridview = (GridView) getView().findViewById(R.id.vitrin_gridview);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser =  firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    class  custom_ana_sayfa extends BaseAdapter {


        @Override
        public int getCount() {
            return id_array.size();
        }

        @Override
        public Object getItem(int i) {
            return id_array.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.custom_ana_sayfa, null);
            CircleImageView image_profil_imageview = (CircleImageView) view.findViewById(R.id.image_profil_imageview);

            TextView user_name_textview = (TextView) view.findViewById(R.id.user_name_textview);
            TextView skill_textview = (TextView) view.findViewById(R.id.skill_textview);


            image_profil_imageview.setImageBitmap(StringToBitMap(fotograf_array.get(i).toString()));

            user_name_textview.setText(isim_array.get(i).toString());
            skill_textview.setText(meslek_array.get(i).toString()) ;

            // kullanıcı_adı_textview.setText(gonderen_array.get(i).toString());




            return view;

        }
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void requestNewInterstitial() { //Test cihazı ekliyoruz Admob dan ban yememek için
        AdRequest adRequest = new AdRequest.Builder() .addTestDevice("special id")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


}

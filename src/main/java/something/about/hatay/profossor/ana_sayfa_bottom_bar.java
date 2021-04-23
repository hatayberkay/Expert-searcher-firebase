package something.about.hatay.profossor;


import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import something.about.hatay.profossor.anasayfa.ana_sayfa_bottom_bar_fragment;
import something.about.hatay.profossor.anasayfa.ben_fragment;
import something.about.hatay.profossor.anasayfa.gelen_cevaplar_fragment;
import something.about.hatay.profossor.anasayfa.soru_sor_fragment;

public class ana_sayfa_bottom_bar extends AppCompatActivity {
    FragmentManager fragmentManager;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    String  firebase_user_string_email ;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    boolean mBounded;
    service mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa_bottom_bar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        String[] separated = firebaseUser.getEmail().toString().split("@");
        firebase_user_string_email = separated[0] ;

        BottomBar   bottomBar = (BottomBar)findViewById(R.id.bottomBar) ;


        fragmentManager = getSupportFragmentManager();












        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];


      NotificationManager nm = (NotificationManager)getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        nm.cancel(1);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ana_sayfa_bottom_bar_fragment ana_sayfa_fragment = new ana_sayfa_bottom_bar_fragment();
        transaction.replace(R.id.container , ana_sayfa_fragment ,"ana_sayfa");
        transaction.addToBackStack(null);
        transaction.commit();

Intent i = new Intent(getApplicationContext(),service.class);
startService(i);

        if (firebaseUser.isEmailVerified() == false){

            Intent uye_girisi_gonder = new Intent(getApplicationContext(), uye_girisi.class);
            startActivity(uye_girisi_gonder);
            ana_sayfa_bottom_bar.this.finish();
            Toast.makeText(this, "E-mailinizi onaylayınız.", Toast.LENGTH_SHORT).show();

        }if (firebaseUser.isEmailVerified() == true){
            Toast.makeText(this, "Hoşgeldiniz.", Toast.LENGTH_SHORT).show();



// set Fragmentclass Arguments




        }


    bottomBar.setTabTitleTextAppearance(R.style.CustomTitleTextAppearance);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {















                bottom_bar_fragmentler(tabId);



            }

            private void bottom_bar_fragmentler(int tabId) {


                switch (tabId){
                    case R.id.ana_sayfa :
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        ana_sayfa_bottom_bar_fragment ana_sayfa_fragment = new ana_sayfa_bottom_bar_fragment();
                        transaction.replace(R.id.container , ana_sayfa_fragment ,"ana_sayfa");

                        transaction.addToBackStack(null);


                        transaction.commit();

                        break;

                    case R.id.soru_sor:
                        FragmentTransaction transaction_soru_sor = fragmentManager.beginTransaction();
                        soru_sor_fragment soru_sor_fragment = new soru_sor_fragment();
                        transaction_soru_sor.replace(R.id.container , soru_sor_fragment ,"soru_sor");
                        transaction_soru_sor.addToBackStack(null);
                        transaction_soru_sor.commit();

                        break;
                    case R.id.Mesajlar :

                        FragmentTransaction transaction_gelen_cevaplar = fragmentManager.beginTransaction();
                        gelen_cevaplar_fragment gelen_cevaplar_fragment = new gelen_cevaplar_fragment();
                        transaction_gelen_cevaplar.replace(R.id.container , gelen_cevaplar_fragment ,"gelen_cevaplar");
                        transaction_gelen_cevaplar.addToBackStack(null);
                        transaction_gelen_cevaplar.commit();

                        Bundle bundle = new Bundle();
                        bundle.putString("user_name", firebase_user_string_email);
                        gelen_cevaplar_fragment.setArguments(bundle);

                        break;

                    case R.id.Profil :

                        FragmentTransaction transaction_ben = fragmentManager.beginTransaction();
                        ben_fragment ben_fragment = new ben_fragment();
                        transaction_ben.replace(R.id.container , ben_fragment ,"ben");
                        transaction_ben.addToBackStack(null);


                        transaction_ben.commit();

                        break;



                }



                }
        });










    }


    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent mIntent = new Intent(this, service.class);
        bindService(mIntent, mConnection, BIND_AUTO_CREATE);

    };

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBounded = false;
            mServer = null;

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBounded = true;
            something.about.hatay.profossor.service.LocalBinder mLocalBinder = (something.about.hatay.profossor.service.LocalBinder)service;
            mServer = mLocalBinder.getServerInstance();
        }

    };

    @Override
    protected void onStop() {
        super.onStop();
        if(mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

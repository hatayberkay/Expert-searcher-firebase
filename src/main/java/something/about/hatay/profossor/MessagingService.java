package something.about.hatay.profossor;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import something.about.hatay.profossor.mesajlasma_ve_konu_acma.Mesajlasma;

public class MessagingService extends FirebaseMessagingService {
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this , Mesajlasma.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 ,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder  = new NotificationCompat.Builder(this);

//Uygulamanın adı gelicek.
      //  builder.setContentTitle("FCM NOTİFİCATİON");

        builder.setContentTitle(remoteMessage.getNotification().getTitle());


        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setAutoCancel(true);
     //  builder.setSmallIcon(R.drawable.common_google_signin_btn_icon_dark);
       builder.setSmallIcon(R.drawable.geri_icon);
     //  builder.setSmallIcon(R.drawable.logom);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , builder.build());


    }

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];

        databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar/" + email_string_result);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
          //      Toast.makeText(getApplicationContext(), "" + dataSnapshot, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}




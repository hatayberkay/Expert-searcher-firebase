package something.about.hatay.profossor;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class service extends Service {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    IBinder mBinder = new LocalBinder();
int atla_integer = 0;
    @Override
    public void onCreate() {
      super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser =  firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        final String split_firebase_email_string[] = firebaseUser.getEmail().toString().split("@");
        final String email_string_result = split_firebase_email_string[0];


//  databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result + "/mesajlar");


   databaseReference = firebaseDatabase.getReference("kullanıcılar/" + email_string_result +"/" + "bilgiler");



        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(getBaseContext(),"1")
                        .setSmallIcon(R.drawable.soru_isareti)
                        .setContentTitle("Profesör")
                        .setContentText("Yeni bir mesajınız var.")
                        .setDefaults(NotificationCompat.DEFAULT_SOUND)
                        .build();




              if (!isForeground("something.about.hatay.profossor")) {
                  notificationManager.notify(0, notification);

              }










            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {




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


        //Toast.makeText(service.this, "started", Toast.LENGTH_SHORT).show();
        return START_REDELIVER_INTENT;

    }


    @Nullable
    @Override
    public IBinder onBind(Intent ıntent) {
        return mBinder;
    }



    @Override
    public void onDestroy() {

        Toast.makeText(this, "The service has stopped. This message came from service class.", Toast.LENGTH_LONG).show();

    }
    public class LocalBinder extends Binder {
        public service getServerInstance() {
            return service.this;
        }
    }
    public boolean isForeground(String myPackage) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        return componentInfo.getPackageName().equals(myPackage);
    }


}

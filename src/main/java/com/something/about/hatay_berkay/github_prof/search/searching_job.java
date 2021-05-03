package com.something.about.hatay_berkay.github_prof.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.something.about.hatay_berkay.github_prof.R;

public class searching_job extends AppCompatActivity {
ImageView image_jobs;
ListView job_list;


            final String[] items_software={"Web Software","Mobile Application","E-Commerce","Domain & Hosting ","Technological support"};
            final String[] items_enginering ={"Mechatronic Engineering","Mechanical Engineering"};
            final String[] items_healty ={"Eye diseases","Neurology","Mental health", "Basic Health knowledge"};
            final String[] items_design ={"Fashion design" , "Industrial design","Logo design"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_expert);

        definations();

        Bundle extras = getIntent().getExtras();
        String value = extras.getString("send_string");

        if (value.equals("software")) {

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,  items_software);
            job_list.setAdapter(adapter);


        } else   if (value.equals("enginering")) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,  items_enginering);
            job_list.setAdapter(adapter);


        } else   if (value.equals("healty")) {
            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,  items_healty);
            job_list.setAdapter(adapter);


        }else   if (value.equals("design")) {

            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,  items_design);
            job_list.setAdapter(adapter);

        }


        job_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (value.equals("software")) {



                 Intent i = new Intent(getApplicationContext(), searching_expert.class);
                 i.putExtra("send_string",items_software[position]);
                 startActivity(i);
                 searching_job.this.finish();




                } else   if (value.equals("enginering")) {




                    Intent i = new Intent(getApplicationContext(), searching_expert.class);
                    i.putExtra("send_string",items_enginering[position]);
                    startActivity(i);
                    searching_job.this.finish();

                } else   if (value.equals("healty")) {




                    Intent i = new Intent(getApplicationContext(), searching_expert.class);
                    i.putExtra("send_string",items_healty[position]);
                    startActivity(i);
                    searching_job.this.finish();

                }else   if (value.equals("design")) {

                    Intent i = new Intent(getApplicationContext(), searching_expert.class);
                    i.putExtra("send_string",items_design[position]);
                    startActivity(i);
                    searching_job.this.finish();


                }







            }
        });


            }

    private void definations() {


        image_jobs = (ImageView)  findViewById(R.id.image_jobs);
        job_list = (ListView) findViewById(R.id.job_list);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        searching_job.this.finish();
    }
}
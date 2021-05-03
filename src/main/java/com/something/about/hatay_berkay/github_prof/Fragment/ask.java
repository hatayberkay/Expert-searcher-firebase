package com.something.about.hatay_berkay.github_prof.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.something.about.hatay_berkay.github_prof.R;
import com.something.about.hatay_berkay.github_prof.search.searching_job;

public class ask  extends Fragment {
CardView first_cardivew ,second_cardivew ,third_cardivew ,forth_cardivew;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ask_fragment,container,false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        definations();

        first_cardivew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), searching_job.class);
                i.putExtra("send_string", "software");
                startActivity(i);

            }
        });
        second_cardivew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), searching_job.class);
                i.putExtra("send_string", "enginering");
                startActivity(i);
            }
        });
        third_cardivew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), searching_job.class);
                i.putExtra("send_string", "healty");
                startActivity(i);
            }
        });
        forth_cardivew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), searching_job.class);
                i.putExtra("send_string", "design");
                startActivity(i);



            }
        });


    }

    private void definations() {
        first_cardivew = (CardView) getView().findViewById(R.id.first_cardivew);
        second_cardivew = (CardView) getView().findViewById(R.id.second_cardivew);
        third_cardivew = (CardView) getView().findViewById(R.id.third_cardivew);
        forth_cardivew = (CardView) getView().findViewById(R.id.forth_cardivew);


    }


}

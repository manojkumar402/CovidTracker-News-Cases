package com.example.bottomnavigation;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CartFragment extends Fragment {
    RecyclerView stateRV;
    StateAdapter StateRVAdapter;
    List<StateModel> stateList;
    List<StateModel> sortStateList;
    LinearLayoutManager layoutManager;
    private TextView wwTCases;
    private TextView wwRCases;
    private TextView wwDCases;
    private Button button;
    private Button Desbutton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
        View v = inflater.inflate(R.layout.fragment_cart,container,false);
//        teacher_id_number = v.findViewById(R.id.teacher_id_number); //Note this line
//other tasks you need to do

        stateRV = v.findViewById(R.id.recycler_view);
        wwTCases = v.findViewById(R.id.totalCases);
        wwRCases = v.findViewById(R.id.recoveredCases);
        wwDCases = v.findViewById(R.id.deathCases);
        button = v.findViewById(R.id.Sortbutton);
        Desbutton = v.findViewById(R.id.DesButton);
        getStateInfo();

        Desbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(sortStateList, new Comparator<StateModel>() {
                    @Override
                    public int compare(StateModel s1, StateModel s2) {
                        return s1.getmCases() - s2.getmCases();
                    }
                });
                StateRVAdapter.notifyDataSetChanged();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(sortStateList);
                layoutManager = new LinearLayoutManager(getActivity());
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                stateRV.setLayoutManager(layoutManager);
                StateRVAdapter = new StateAdapter(sortStateList);
                stateRV.setAdapter(StateRVAdapter);
                StateRVAdapter.notifyDataSetChanged();

            }
        });
        return v;
    }


    private void getStateInfo(){
        String url = "https://api.rootnet.in/covid19-in/stats/latest";
        JsonObjectRequest request = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            stateList = new ArrayList<StateModel>();
                            sortStateList = new ArrayList<StateModel>();
                            JSONObject dataObject = response.getJSONObject("data");
                            JSONObject summaryObj = dataObject.getJSONObject("summary");

                            int tCases = summaryObj.getInt("total");
                            int rCases = summaryObj.getInt("discharged");
                            int dCases = summaryObj.getInt("deaths");
                            wwTCases.setText(String.valueOf(tCases));
                            wwRCases.setText(String.valueOf(rCases));
                            wwDCases.setText(String.valueOf(dCases));

                            JSONArray regionalArray = dataObject.getJSONArray("regional");
                            for(int i=1;i<regionalArray.length();i++){
                                JSONObject regionalObj = regionalArray.getJSONObject(i);
                                String stateName = regionalObj.getString("loc");
                                int totalCases = regionalObj.getInt("totalConfirmed");
                                int deathCases = regionalObj.getInt("deaths");
                                int recoverdCases = regionalObj.getInt("discharged");

                                StateModel stateModel = new StateModel(stateName,recoverdCases,deathCases,totalCases);

                                stateList.add(stateModel);
                                sortStateList.add(stateModel);

//                                Log.v("mainActivity", "The number of case are " + stateName);
//                                Log.v("mainActivity", "The total number of case are " + totalCases);
//                                Log.v("mainActivity", "The Death number of case are " + deathCases);
//                                Log.v("mainActivity", "The Recovered number of case are " + recoverdCases);

                            }

                            layoutManager = new LinearLayoutManager(getActivity());
                            layoutManager.setOrientation(RecyclerView.VERTICAL);
                            stateRV.setLayoutManager(layoutManager);
                            StateRVAdapter = new StateAdapter(stateList);
                            stateRV.setAdapter(StateRVAdapter);
                            StateRVAdapter.notifyDataSetChanged();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Unable to Fetch Data\nCheck your wifi connection", Toast.LENGTH_SHORT).show();

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(request);
    }

//        return inflater.inflate(R.layout.fragment_cart, container, false);
}
package com.example.bottomnavigation;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CasesFragment extends Fragment {
    RecyclerView stateRV;
    StateAdapter StateRVAdapter;
    List<StateModel> stateList;
    List<StateModel> sortStateList;
    LinearLayoutManager layoutManager;
    private TextView wwTCases;
    private TextView wwRCases;
    private TextView wwDCases;
    private TextView wwPCases;
    private TextView ChangeTotCases,ChangeRecCases,ChangeDetCases,ChangePositiveCases;
//    private Button button;
//    private Button Desbutton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_cases,container,false);
        
        stateRV = v.findViewById(R.id.recycler_view);
        wwTCases = v.findViewById(R.id.totalCases);
        wwRCases = v.findViewById(R.id.recoveredCases);
        wwDCases = v.findViewById(R.id.deathCases);
        wwPCases = v.findViewById(R.id.postiveCases);
        ChangeTotCases = v.findViewById(R.id.totalIncrement);
        ChangeRecCases = v.findViewById(R.id.RecoveredIncrement);
        ChangeDetCases = v.findViewById(R.id.deathIncrement);
        ChangePositiveCases = v.findViewById(R.id.positiveIncrement);

//        button = v.findViewById(R.id.Sortbutton);
//        Desbutton = v.findViewById(R.id.DesButton);
       getStateInfo();



//        Desbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Collections.sort(sortStateList, new Comparator<StateModel>() {
//                    @Override
//                    public int compare(StateModel s1, StateModel s2) {
//                        return s1.getmCases() - s2.getmCases();
//                    }
//                });
//                StateRVAdapter.notifyDataSetChanged();
//            }
//        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Collections.sort(sortStateList);
//                layoutManager = new LinearLayoutManager(getActivity());
//                layoutManager.setOrientation(RecyclerView.VERTICAL);
//                stateRV.setLayoutManager(layoutManager);
//                StateRVAdapter = new StateAdapter(sortStateList);
//                stateRV.setAdapter(StateRVAdapter);
//                StateRVAdapter.notifyDataSetChanged();
//
//            }
//        });
        return v;
    }



    private void getStateInfo(){
        String url = "https://www.mohfw.gov.in/data/datanew.json";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    stateList = new ArrayList<StateModel>();
                    JSONObject indiaObject = response.getJSONObject(response.length()-1);
                    String IndiaTCases = indiaObject.getString("new_positive");
                    String IndiaRCases = indiaObject.getString("new_cured");
                    String IndiaDCases = indiaObject.getString("new_death");
                    String IndiaPCases = indiaObject.getString("new_active");
                    String INCtCases = indiaObject.getString("positive");
                    String INCrCases = indiaObject.getString("cured");
                    String INCdCases = indiaObject.getString("death");
                    String INCpCases = indiaObject.getString("active");
                    int c = Integer.parseInt(IndiaTCases) - Integer.parseInt(INCtCases);
                    int c1 = Integer.parseInt(IndiaRCases) - Integer.parseInt(INCrCases);
                    int c2 = Integer.parseInt(IndiaDCases) - Integer.parseInt(INCdCases);
                    int c3 = Integer.parseInt(IndiaPCases) - Integer.parseInt(INCpCases);
                    Toast.makeText(getContext(), "C3 is "+c3, Toast.LENGTH_SHORT).show();
                    if(c3<0){
                        //c3 = c3 - (2*c3);
                        Drawable d = getContext().getResources().getDrawable(R.drawable.ic_down);
                        ChangePositiveCases.setCompoundDrawables(null,d,null,null);
                    }else{
                        Drawable d = getContext().getResources().getDrawable(R.drawable.ic_up);
                        ChangePositiveCases.setCompoundDrawables(null,d,null,null);

                    }

                    ChangeTotCases.setText(String.valueOf(c));
                    ChangeRecCases.setText(String.valueOf(c1));
                    ChangeDetCases.setText(String.valueOf(c2));
                    ChangePositiveCases.setText(String.valueOf(c3));
                    wwTCases.setText(IndiaTCases);
                    wwRCases.setText(IndiaRCases);
                    wwDCases.setText(IndiaDCases);
                    wwPCases.setText(IndiaPCases);
                    //Toast.makeText(getContext(), "Length"+response.length(), Toast.LENGTH_SHORT).show();
                    for(int i=0;i<response.length()-1;i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String stateName = jsonObject.getString("state_name");
                        String totalCases = jsonObject.getString("new_active");
                        String deathCases = jsonObject.getString("new_death");
                        String recoveredCases = jsonObject.getString("new_cured");
                        String prevtotalCases = jsonObject.getString("active");
                        String prevdeathCases = jsonObject.getString("death");
                        String prevRecCases = jsonObject.getString("cured");
                        int TotIncre = getIncrementTotCases(totalCases,prevtotalCases);
                        int DeathIncr = getIncremtnDeathCases(deathCases,prevdeathCases);
                        int RecvrdIncr = getIncrementRecCases(recoveredCases,prevRecCases);
                        StateModel stateModel = new StateModel(stateName,recoveredCases,deathCases,totalCases,RecvrdIncr,DeathIncr,TotIncre);
                        stateList.add(stateModel);

                    }

                    layoutManager = new LinearLayoutManager(getActivity());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    stateRV.setLayoutManager(layoutManager);
                    StateRVAdapter = new StateAdapter(stateList);
                    stateRV.setAdapter(StateRVAdapter);
                    StateRVAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private int getIncrementRecCases(String RecCases, String prevRecCases) {
                int today = Integer.parseInt(RecCases);
                int yesterday = Integer.parseInt(prevRecCases);
                int c = yesterday - today;
                return c;
            }

            private int getIncremtnDeathCases(String deathCases, String prevDeathCases) {
                int today = Integer.parseInt(deathCases);
                int yesterday = Integer.parseInt(prevDeathCases);
                int c = yesterday - today;
                return c;
            }

            private int getIncrementTotCases(String totalCases, String prevtotalCases) {
                int today = Integer.parseInt(totalCases);
                int yesterday = Integer.parseInt(prevtotalCases);
                int c = yesterday - today;
                return c;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Unable to Fetch data", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(request);
    }


}
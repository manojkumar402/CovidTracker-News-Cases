package com.example.bottomnavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.StateRVViewHolder> {

    private final List<StateModel> stateList;
    public StateAdapter(List<StateModel> stateList){
        this.stateList = stateList;
    }
    Context context;
    @NonNull
    @Override
    public StateRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cases_list,parent,false);
        return new StateRVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateRVViewHolder holder, int position) {

        StateModel stateDate = stateList.get(position);

        holder.stateNameTV.setText(stateDate.getmStateName());
        holder.deathsTV.setText(String.valueOf(stateDate.getmDeaths()));
        holder.recoveredTV.setText(String.valueOf(stateDate.getmRecovered()));
        holder.casesTV.setText(String.valueOf(stateDate.getmCases()));
        CheckIncrement(stateDate.getmIncreTot(), holder.IncrementCasesTV);
        CheckIncrement(stateDate.getmIncreDeath(),holder.IncrementDeathTV);
        CheckIncrement(stateDate.getmIncreRecoverd(),holder.IncrementRecoveredTV);
        //holder.IncrementCasesTV.setText(String.valueOf(stateDate.getmIncreTot()));
//        holder.IncrementDeathTV.setText(String.valueOf(stateDate.getmIncreDeath()));
//        holder.IncrementRecoveredTV.setText(String.valueOf(stateDate.getmIncreRecoverd()));

    }

    private void CheckIncrement(int val,TextView txtView) {
        if(val == 0){
            txtView.setText(String.valueOf(val));
            txtView.setCompoundDrawablesWithIntrinsicBounds(null,null, null,null);

        }
        else if(val<0){
            val = val - (2*val);
            txtView.setText(String.valueOf(val));

            txtView.setCompoundDrawablesWithIntrinsicBounds(null,null, ContextCompat.getDrawable(context,R.drawable.ic_down),null);
        }else{
            txtView.setText(String.valueOf(val));
            txtView.setCompoundDrawablesWithIntrinsicBounds(null,null, ContextCompat.getDrawable(context,R.drawable.ic_up),null);
        }

    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public static class StateRVViewHolder extends RecyclerView.ViewHolder {

        private final TextView stateNameTV;
        private final TextView casesTV;
        private final TextView deathsTV;
        private final TextView recoveredTV;
        private final TextView IncrementCasesTV;
        private final TextView IncrementRecoveredTV;
        private final TextView IncrementDeathTV;
        public StateRVViewHolder(@NonNull View itemView) {
            super(itemView);

            stateNameTV = itemView.findViewById(R.id.stateName);
            casesTV = itemView.findViewById(R.id.totalCases);
            deathsTV = itemView.findViewById(R.id.deathCases);
            recoveredTV = itemView.findViewById(R.id.recoveredCases);
            IncrementCasesTV = itemView.findViewById(R.id.incrInPositive);
            IncrementDeathTV = itemView.findViewById(R.id.incrInDeath);
            IncrementRecoveredTV = itemView.findViewById(R.id.incrInRecovered);
        }

    }

}

package com.android.smartami;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout root;
        TextView voltageInput, currentCheck, powerConsumed, energyConsumed, energyRemaining;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            voltageInput = itemView.findViewById(R.id.voltageInput);
            currentCheck = itemView.findViewById(R.id.currentCheck);
            powerConsumed = itemView.findViewById(R.id.powerConsumed);
            energyConsumed = itemView.findViewById(R.id.energyConsumed);
            energyRemaining = itemView.findViewById(R.id.energyRemaining);
            root = itemView.findViewById(R.id.root);
        }

        public void setVoltageInput(String string) { voltageInput.setText(string);
        }

        public void setCurrentCheck(String string) { currentCheck.setText(string);
        }

        public void setPowerConsumed(String string) {powerConsumed.setText(string);
        }
        public void setEnergyConsumed(String string) { energyConsumed.setText(string);
        }

        public void setEnergyRemaining(String string) {energyRemaining.setText(string);
        }
    }

    private ArrayList<Model> meterValues;

    public RecyclerAdapter(Model model) {
        meterValues.add(model);
    }

    @Override
    public int getItemCount() {
        return meterValues.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Model object = meterValues.get(position);

        String voltageInput = object.getVoltageInput();
        String Current = object.getCurrent();
        String Power = object.getPower();
        String EnergyConsumed = object.getEnergyConsumed();
        String EnergyAvailable = object.getEnergyAvailable();

        holder.setVoltageInput(voltageInput);
        holder.setCurrentCheck(Current);
        holder.setPowerConsumed(Power);
        holder.setEnergyConsumed(EnergyConsumed);
        holder.setEnergyRemaining(EnergyAvailable);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
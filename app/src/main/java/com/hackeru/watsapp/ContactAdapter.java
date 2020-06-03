package com.hackeru.watsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends  RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ArrayList<Contact> list = new ArrayList<>();

    public void setList(ArrayList<Contact> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_contact_layout, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.contactDetails.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView contactDetails;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            contactDetails = itemView.findViewById(R.id.contact_details_tv);
        }
    }
}

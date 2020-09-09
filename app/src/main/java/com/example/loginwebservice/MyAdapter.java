package com.example.loginwebservice;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<ListItem> listitems;
    private Context context;

    public MyAdapter(List<ListItem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    //For Viewing the data
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // for getting the list item resource file
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return  new ViewHolder(v);
    }

    //For binding the data
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListItem listItem = listitems.get(position);

        holder.tvcid.setText(listItem.getCid());
        holder.tvdesc.setText(listItem.getDesc());
        holder.tvstatus.setText(listItem.getStatus());
        holder.tvctype.setText(listItem.getCtype());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You Clicked "+listItem.getCid(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,AdminChangeStatus.class);
                intent.putExtra("cid",listItem.getCid());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvcid;
        public TextView tvdesc;
        public TextView tvstatus;
        public TextView tvctype;
        public LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvcid=(TextView) itemView.findViewById(R.id.tvcardcid);
            tvdesc =(TextView) itemView.findViewById(R.id.tvcarddesc);
            tvstatus =(TextView) itemView.findViewById(R.id.tvcardstatus);
            tvctype =(TextView) itemView.findViewById(R.id.tvcardctype);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.llcard);
        }
    }

}
package com.example.yasirnoor.foodfactory.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yasirnoor.foodfactory.Interface.ItemClickListener;
import com.example.yasirnoor.foodfactory.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView textOrderId,textOrderStatus,textOrderPhone,textOrderAddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        textOrderId = (TextView)itemView.findViewById(R.id.order_id);
        textOrderStatus =(TextView)itemView.findViewById(R.id.order_status);
        textOrderPhone =(TextView)itemView.findViewById(R.id.order_phone);
        textOrderAddress =(TextView)itemView.findViewById(R.id.order_address);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}

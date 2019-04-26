package com.example.yasirnoor.foodfactory;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.yasirnoor.foodfactory.Common.Common;
import com.example.yasirnoor.foodfactory.Database.Database;
import com.example.yasirnoor.foodfactory.Model.Order;
import com.example.yasirnoor.foodfactory.Model.Request;
import com.example.yasirnoor.foodfactory.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.Picasso;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase Database
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

       // loadOrders(Common.currentUser.getPhone());
    }

/*    private void loadOrders(String phone) {

       FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder viewHolder, int position, @NonNull Request model) {
                viewHolder.textOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.textOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.textOrderAddress.setText(model.getAddress());
                viewHolder.textOrderPhone.setText(model.getPhone());
            }


            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
        recyclerView.setAdapter(adapter);
    }
    private String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }
   /* private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Request model) {
                createViewHolder().textOrder

            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
    }*/
}

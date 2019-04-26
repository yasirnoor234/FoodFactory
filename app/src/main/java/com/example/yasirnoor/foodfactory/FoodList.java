package com.example.yasirnoor.foodfactory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yasirnoor.foodfactory.Interface.ItemClickListener;
import com.example.yasirnoor.foodfactory.Model.Food;
import com.example.yasirnoor.foodfactory.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId= "";

    FirebaseRecyclerAdapter<Food,FoodViewHolder>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get Intent here
        if(getIntent()!= null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if(!categoryId.isEmpty() && categoryId!=null)
        {
  //          loadListFood(categoryId);
        }

    }

/*    private void loadListFood(String categoryId) {
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("MenuId").equalTo(categoryId)) // Like : Select * From  Foods where MenuId =
        {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder viewHolder, final int position, @NonNull Food model) {
                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int positon, boolean isLongClick) {
                        //Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();

                        //Start new Activity
                        Intent foodDetail = new Intent(FoodList.this,FoodDetails.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey()); //Send view Id to new activity
                        startActivity(foodDetail);

                    }
                });

                //Set Adapter
                //Log.d("TAG",""+adapter.getItemCount());
                recyclerView.setAdapter(adapter);
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
    }*/
}

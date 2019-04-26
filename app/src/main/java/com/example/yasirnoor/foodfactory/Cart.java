package com.example.yasirnoor.foodfactory;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasirnoor.foodfactory.Common.Common;
import com.example.yasirnoor.foodfactory.Database.Database;
import com.example.yasirnoor.foodfactory.Model.Order;
import com.example.yasirnoor.foodfactory.Model.Request;
import com.example.yasirnoor.foodfactory.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView textTotalPrice;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase Database
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //Initialize
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        textTotalPrice= (TextView)findViewById(R.id.total);
        btnPlace = (FButton)findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        loadListFood();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address: ");

        final EditText editAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editAddress.setLayoutParams(lp);
        alertDialog.setView(editAddress); //Add edit Text to alert dialog
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Create new Request
                Request  request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        editAddress.getText().toString(),
                        textTotalPrice.getText().toString(),
                        cart
                );

                //Submit to Firebase
                //I will using System.CurrentMilli to key

                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                //Delete Cart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Thank you, Order Place",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    private void loadListFood() {
        cart = new Database(this).getsCart();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //Calculate Total Price
        int total = 0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt((order.getQuantity())));

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        textTotalPrice.setText(fmt.format(total));

    }
}

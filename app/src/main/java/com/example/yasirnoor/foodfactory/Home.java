package com.example.yasirnoor.foodfactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yasirnoor.foodfactory.Common.Common;
import com.example.yasirnoor.foodfactory.Interface.ItemClickListener;
import com.example.yasirnoor.foodfactory.Model.Category;
import com.example.yasirnoor.foodfactory.Model.Order;
import com.example.yasirnoor.foodfactory.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;

    TextView textFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Initialize Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(Home.this,Cart.class);
                startActivity(cartIntent);
                //           Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //                 .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set Name for user
        View headerView = navigationView.getHeaderView(0);
        textFullName = (TextView)headerView.findViewById(R.id.textFullName);
        textFullName.setText(Common.currentUser.getName());

        //Load Menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();


    }

    private void loadMenu() {
        FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category)                                                                                                                                                                                                                                                                                                                                                                                                                                                                          {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder viewHolder, int position, @NonNull Category model) {
                viewHolder.textMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
             final Category clckItem = model;
             viewHolder.setItemClickListener(new ItemClickListener() {
                 @Override
                 public void onClick(View view, int positon, boolean isLongClick) {
                     Toast.makeText(Home.this,""+clckItem.getName(),Toast.LENGTH_LONG).show();


                 }
             });


            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
    //    recycler_menu.setAdapter(adapter);
    }


    /*
       private void loadMenu() {
           FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
               @Override
               protected void popularViewHolder(@NonNull MenuViewHolder viewholder, int position, @NonNull Category model) {

                   viewholder.textMenuName.setText(model.getName());
                   Picasso.with(getBaseContext()).load(model.getImage()).into(viewholder.imageView);

                   final Category clickItem = model;
                   viewholder.setItemClickListener(new ItemClickListener() {
                       @Override
                       public void onClick(View view, int positon, boolean isLongClick) {
                           Toast.makeText(Home.this,""+clickItem.getName(),Toast.LENGTH_LONG);
                       }
                   });
               }

           };
           recycler_menu.setAdapter(adapter);
       }
   */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
            Intent cartIntent = new Intent(Home.this,Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_orders) {
            Intent orderIntent = new Intent(Home.this,OrderStatus.class);
            startActivity(orderIntent);


        } else if (id == R.id.nav_log_out) {
            Intent signIn = new Intent(Home.this,SignIn.class);
            signIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signIn);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

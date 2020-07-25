package com.example.anshulsingh.foodpilu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OneRestaurantMenu extends AppCompatActivity implements CustomListener{
RecyclerView starters_list,mainCourse_list,dessert_list;

    rest_menu menu;
    ArrayList<per_product> starterArray= new ArrayList<>();
    ArrayList<per_product> MainCourseArray= new ArrayList<>();
    ArrayList<per_product> DessertsArray= new ArrayList<>();
    TextView rest_name,rest_address,rest_phone,total_cost;
    ImageView rest_image;
    String id;
    Button go_to_cart;

    ArrayList<per_product> Total_Products = new ArrayList<per_product>();
    Integer TotalCost = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_restaurant_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        id = getIntent().getExtras().getString("id");
        rest_image = findViewById(R.id.rest_image);
        rest_name = findViewById(R.id.rest_name);
        rest_address = findViewById(R.id.rest_address);
        rest_phone = findViewById(R.id.rest_phone);
        starters_list= findViewById(R.id.staters_list);
        mainCourse_list = findViewById(R.id.mainCourse_list);
        dessert_list = findViewById(R.id.desserts_list);
        total_cost = findViewById(R.id.total_cost);
        go_to_cart = findViewById(R.id.go_to_cart);


        FirebaseDatabase.getInstance().getReference().child("Rest").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    ResItems rest = dataSnapshot.getValue(ResItems.class);
                    Picasso.get().load(rest.res_image).fit().into(rest_image);
                    rest_name.setText(rest.name);
                    rest_address.setText(rest.res_addr);
                    rest_phone.setText(rest.res_phno);


                    for(int i=0;i<rest.items.size();i++){
                        if(rest.items.get(i).type == 0) {
                            starterArray.add(rest.items.get(i));
                        }
                        if(rest.items.get(i).type == 1) {
                            MainCourseArray.add(rest.items.get(i));
                        }
                        if(rest.items.get(i).type == 2) {
                            DessertsArray.add(rest.items.get(i));
                        }
                    }


                    starters_list.setLayoutManager(new LinearLayoutManager(OneRestaurantMenu.this));
                    final StatersAdapter statersAdapter= new StatersAdapter(starterArray,0,OneRestaurantMenu.this,OneRestaurantMenu.this);
                    starters_list.setAdapter(statersAdapter);


                    mainCourse_list.setLayoutManager(new LinearLayoutManager(OneRestaurantMenu.this));
                    final StatersAdapter maincourseAdapter= new StatersAdapter(MainCourseArray,0,OneRestaurantMenu.this,OneRestaurantMenu.this);
                    mainCourse_list.setAdapter(maincourseAdapter);


                    dessert_list.setLayoutManager(new LinearLayoutManager(OneRestaurantMenu.this));
                    final StatersAdapter dessertAdapter= new StatersAdapter(DessertsArray,0,OneRestaurantMenu.this,OneRestaurantMenu.this);
                    dessert_list.setAdapter(dessertAdapter);

                    go_to_cart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(OneRestaurantMenu.this,Cart.class);
                            i.putExtra("id",id);
                            i.putExtra("items",Total_Products);

                            startActivity(i);
                        }
                    });



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });












    }

    @Override
    public void onAdded(per_product cost,String trigger) {

        if(trigger.equals("add")){
            Total_Products.add(cost);
            TotalCost = TotalCost + cost.rate;
            total_cost.setText(String.valueOf(TotalCost));
        }

        if(trigger.equals("sub")){
            for(Integer i = 0;i<Total_Products.size();i++){
                if(Total_Products.get(i).productName.equals(cost.productName)){
                    Total_Products.remove(i);
                    break;
                }
                TotalCost = TotalCost - cost.rate;
                total_cost.setText(String.valueOf(TotalCost));
            }
        }


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

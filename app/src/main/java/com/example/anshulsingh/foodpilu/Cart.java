package com.example.anshulsingh.foodpilu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    ArrayList<per_product> items = new ArrayList<>();
    TextView restname,totalFinal,totalCost,totalPackCharges,totalGST,textAddressCart;
    RecyclerView listCart;
    private FirebaseUser firebaseUser;
    String perProductkey;
    Integer cost = 0;
    MaterialButton placeOrder;
    String id;
    ArrayList<per_product> finalItems = new ArrayList<>();
    ArrayList<Integer> count = new ArrayList<>();
    ArrayList<String> doneItems = new ArrayList<>();
    CheckBox chboxTnC;
    EditText etAdress;
    KeyListener variable;
    ProgressDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cart");
        dialog = new ProgressDialog(Cart.this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Placing Your order");


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        placeOrder=findViewById(R.id.placeOrder);
        restname = findViewById(R.id.restnameCart);
        totalFinal=findViewById(R.id.totalFinal);
        totalCost=findViewById(R.id.totalCost);
        chboxTnC = findViewById(R.id.chboxTnC);
        textAddressCart = findViewById(R.id.textAddressCart);
        etAdress = findViewById(R.id.etAdress);

        etAdress.setEnabled(false);


        id = getIntent().getStringExtra("id");
        items = (ArrayList<per_product>) getIntent().getSerializableExtra("items");

        etAdress.setVisibility(View.INVISIBLE);
        textAddressCart.setVisibility(View.INVISIBLE);


        chboxTnC.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                    textAddressCart.setTextColor(Color.BLACK);
                    etAdress.setBackground(getResources().getDrawable(R.drawable.border_et));
                    etAdress.setEnabled(true);
                    etAdress.setVisibility(View.VISIBLE);
                    textAddressCart.setVisibility(View.VISIBLE);


                }else{


                    textAddressCart.setTextColor(Color.GRAY);
                    etAdress.setBackground(getResources().getDrawable(R.drawable.when_not_typing));
                    etAdress.setEnabled(false);
                    etAdress.setVisibility(View.INVISIBLE);
                    textAddressCart.setVisibility(View.INVISIBLE);
                }
            }
        });



        for(int i= 0;i<items.size();i++){
            int counter = 0;
            for(int j=0;j<doneItems.size();j++){
                if(doneItems.get(j).equals(items.get(i).productName)){
                    counter = 1;
                    break;
                }
            }

            if(counter == 0){
                for(int j=0;j<items.size();j++){
                    if(items.get(j).productName.equals(items.get(i).productName)){
                        counter++;
                    }
                }
                doneItems.add(items.get(i).productName);
                finalItems.add(items.get(i));
                count.add(counter);
            }


        }


        for(int i=0;i<finalItems.size();i++){
            cost = cost + finalItems.get(i).rate*count.get(i);
        }

        totalCost.setText("Rs. "+String.valueOf(cost));
        totalFinal.setText("Rs. "+String.valueOf(10+cost));



        listCart = findViewById(R.id.listCart);
        listCart.setLayoutManager(new LinearLayoutManager(this));

        final CartAdapter cartAdapter= new CartAdapter(finalItems,count);
        listCart.setAdapter(cartAdapter);

        FirebaseDatabase.getInstance().getReference().child("Rest").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ResItems rest = dataSnapshot.getValue(ResItems.class);
                restname.setText(rest.name);

                placeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.show();
                        Handler h= new Handler();
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {

                                Order order = new Order();
                                order.Address = etAdress.getText().toString();
                                order.resturantID = id;
                                order.resturantName =rest.name;
                                order.items = items;
                                order.cost = cost+10;

                                        FirebaseDatabase.getInstance().getReference().child("Orders").push().setValue(order);
                                dialog.dismiss();
                                Toast.makeText(Cart.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                        };
                        h.postDelayed(r,5000);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

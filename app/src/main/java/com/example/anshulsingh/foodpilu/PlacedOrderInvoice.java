package com.example.anshulsingh.foodpilu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlacedOrderInvoice extends AppCompatActivity {
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<per_product> items = new ArrayList<>();
    RecyclerView listCartPO;
    TextView restnamePO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order_invoice);
        restnamePO = findViewById(R.id.restnamePO);
        listCartPO = findViewById(R.id.listCartPO);

        Bundle extras = getIntent().getExtras();
        String keyForRest = extras.getString("RestKey");

        FirebaseDatabase.getInstance().getReference().child("Restaurant").child(keyForRest).child("resName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String resItems= dataSnapshot.getValue(String.class);
                Log.d("hey",resItems);
                restnamePO.setText(resItems);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listCartPO.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase.getInstance().getReference().child("PlacedOrder").child(firebaseUser.getUid())
        .child(keyForRest).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                per_product perProduct = dataSnapshot.getValue(per_product.class);

                items.add(perProduct);



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }
}

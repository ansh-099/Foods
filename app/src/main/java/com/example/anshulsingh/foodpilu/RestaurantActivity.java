package com.example.anshulsingh.foodpilu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ResRecyclerViewAdapter mAdapter;
    private List<ResItems> ResItemsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        ResItemsList = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.restaurant_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ResRecyclerViewAdapter(RestaurantActivity.this, ResItemsList);

       FirebaseDatabase.getInstance().getReference().child("Rest").addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.getChildrenCount() > 0) {
                   for (DataSnapshot child : dataSnapshot.getChildren()) {
                       ResItems resItems = child.getValue(ResItems.class);
                       mAdapter.ResItemsList.add(resItems);
                   }

                   mRecyclerView.setAdapter(mAdapter);
               }




           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


//        ArrayList<per_product> start = new ArrayList<>();
//        start.add(new per_product("Chilli Patato","34","10%"));
//        start.add(new per_product("Chilli Paneer","100","5%"));
//        start.add(new per_product("Honey Chilli Patato","150","7%"));
//
//
//
//
//        ArrayList<per_product> start1 = new ArrayList<>();
//        start1.add(new per_product("Dal","200","6%"));
//        start1.add(new per_product("Chilli Paneer","100","5%"));
//        start1.add(new per_product("Honey Chilli Patato","140","3%"));
//
//
//
//        ArrayList<per_product> start2 = new ArrayList<>();
//        start2.add(new per_product("Chilli Patato","200","6%"));
//        start2.add(new per_product("Chilli Paneer","140","8%"));
//        start2.add(new per_product("Honey Chilli Patato","200","4%"));

//        rest_menu rest= new rest_menu(start,start1,start2);

//        ResItemsList.add(
//                new ResItems("Foodzilla Restaurant", "8AM - 9PM", "Min Time\n40 min", "Min Order\nRs. 200", "Gamma 1, Greater Noida","9999999999","https://cdn-image.foodandwine.com/sites/default/files/1501607996/opentable-scenic-restaurants-marine-room-FT-BLOG0818.jpg",rest)
//        );
//
//        ResItemsList.add(
//                new ResItems("Mehfil Restaurant", "10AM - 10PM", "Min Time\n30 min", "Min Order\nRs. 100", "Gamma 1, Greater Noida","1111111111","http://static.asiawebdirect.com/m/bangkok/portals/bali-indonesia-com/homepage/top10/top10-restaurants-sanur/pagePropertiesImage/best-restaurants-sanur.jpg.jpg",rest)
//        );
//
//        ResItemsList.add(
//                new ResItems("Laziz Restaurant", "10AM - 10PM", "Min Time\n20 min", "Min Order\nRs. 150", "Gamma 1, Greater Noida","1121212111","https://insideguide.co.za/app/uploads/sites/2/2018/03/top-restaurants-in-cape-town-2018.jpg",rest)
//        );
//
//        ResItemsList.add(
//                new ResItems("AL-Karim Restaurant", "10AM - 10PM", "Min Time\n60 min", "Min Order\nRs. 250", "Gamma 1, Greater Noida","112312111","http://www.alfuttaim.com/wps/wcm/connect/alfuttaim/7b53362d-d3cb-4eea-a867-7094857241ad/Barilla+Customer+Focus+%281%29.jpg?MOD=AJPERES&CACHEID=ROOTWORKSPACE.Z18_JALI1J40M0EKE0ALQ3H535BB64-7b53362d-d3cb-4eea-a867-7094857241ad-lLehyBZ",rest)
//        );
//
//        ResItemsList.add(
//                new ResItems("Khans Rolls", "10AM - 10PM", "Min Time\n30 min", "Min Order\nRs. 300", "Gamma 1, Greater Noida","1122312333","https://paymentweek.com/wp-content/uploads/2018/03/opentable-scenic-restaurants-bertrand-at-mister-a-FT-BLOG0818-1024x576.jpg",rest)
//        );
//
//        ResItemsList.add(
//                new ResItems("KFC Restaurant", "10AM - 10PM", "Min Time\n45 min", "Min Order\nRs. 200", "Gamma 1, Greater Noida","21211122","https://www.phnompenhpost.com/sites/default/files/styles/full-screen/public/field/image/rlr_fb_restaurant-le-royal_b-4.jpg?itok=WZxOMm5r",rest)
//        );
//
//        ResItemsList.add(
//                new ResItems("Dawat Restaurant", "10AM - 10PM", "Min Time\n30 min", "Min Order\nRs. 250", "Gamma 1, Greater Noida","32322222","\"https://www.phnompenhpost.com/sites/default/files/styles/full-screen/public/field/image/rlr_fb_restaurant-le-royal_b-4.jpg?itok=WZxOMm5r",rest)
//        );


//        Registered_Users registered_users = new Registered_Users("Anshul","7838447218","heyPass","ansh.099@gmail.com");
//        FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid()).push().setValue(registered_users);



//        ResItems item =   new ResItems("Foodzilla Restaurant", "8AM - 9PM", "Min Time\n40 min", "Min Order\nRs. 200", "Gamma 1, Greater Noida","9999999999","https://cdn-image.foodandwine.com/sites/default/files/1501607996/opentable-scenic-restaurants-marine-room-FT-BLOG0818.jpg",rest);

//        String s= firebaseUser.getUid();
//
//        FirebaseDatabase.getInstance().getReference().child("Restaurant").push().setValue(item);


//ArrayList<String> lists= new ArrayList<>();
//lists.add("31");
//lists.add("42");
//lists.add("32");
//FirebaseDatabase.getInstance().getReference().child("hey").push().setValue(lists);



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

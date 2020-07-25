package com.example.anshulsingh.foodpilu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.android.gms.maps.model.Dash;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    AdvanceDrawerLayout drawer;
    Toolbar toolbar;
    final int SHOWCASEVIEW_ID = 28;
    private static final String SHOWCASE_ID = "simple example";
    private boolean hasManualPostion = false;
    private int xPosition, yPosition, width;
    ImageView image2,image3,image4,image5,image6;
    CardView cardVeg,cardRest,cardGroc,cardChic;
    ImageView qrcode;
    TextView textName,headername;


    public int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    public static final String TAG = "Foodpilu2";

    // SignOut Button
    private Button signOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("food", MODE_PRIVATE);
        String email = pref.getString("email", null);

        String time = pref.getString("time","2");
        if(time.equals("1")){
            setShowCaseView();
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("time","2");
            editor.apply();
        }

        cardRest = findViewById(R.id.cardRest);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Food");


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        headername = headerLayout.findViewById(R.id.headername);

//         <Button
//        android:id="@+id/sign_out_btn"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:layout_marginBottom="50dp"
//        android:text="Sign Out" />
//        signOutBtn = (Button) findViewById(R.id.sign_out_btn);
//        signOutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signOut();
//            }
//        });

        textName =findViewById(R.id.textName);

        FirebaseDatabase.getInstance().getReference().child("Users").child(email.replace(".",":"))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        textName.setText(user.name);
                        headername.setText(user.name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        cardRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this,RestaurantActivity.class);
                startActivity(i);
            }
        });

        textName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                placesAutocomplete();
            }
        });

        image2= findViewById(R.id.image2);
        Picasso.get().load("https://thumbs.dreamstime.com/b/restaurant-logo-template-abstract-vector-design-hand-drawn-letters-hand-written-font-good-food-industry-business-any-62608986.jpg").fit().into(image2);

        image3=findViewById(R.id.image3);
        Picasso.get().load("https://www.grocersapp.com/blog/wp-content/uploads/2019/07/How-can-you-make-your-online-grocery-shopping-app-stand-out_-GrocersApp.png").fit().into(image3);

        image4=findViewById(R.id.image4);
        Picasso.get().load("https://lh3.googleusercontent.com/proxy/SHvjectlFroqhGWqXRYEEoEgFePL1qTOT5EzDJ1By2CqN1jhbkuwFdV8b1wt7TetIl3b09PbLsoQ5Vt2qJ870wAs").fit().into(image4);

        image5 = findViewById(R.id.image5);
        Picasso.get().load("https://i.ytimg.com/vi/EZ-MXloBm1Q/maxresdefault.jpg").fit().into(image5);


        qrcode= findViewById(R.id.qrcode);
        Picasso.get().load("https://i1.wp.com/www.macsolutionsplus.com/wp-content/uploads/2017/11/you-can-now-scan-qr-codes-with-the-camera-in-ios-11.jpg?resize=1080%2C675&ssl=1").fit().into(qrcode);

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this,AddressActivity.class);
                startActivity(i);
            }
        });

        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashboard.this,ScanQRcode.class);
                startActivity(i);
            }
        });

        drawer=findViewById(R.id.drawer_layout);
        drawer = (AdvanceDrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawer.setViewScale(Gravity.START, 0.9f);
        drawer.setViewElevation(Gravity.START, 20);
        drawer.setRadius(Gravity.START, 30);
        drawer.useCustomBehavior(Gravity.END);

//        FirebaseDatabase.getInstance().getReference().child("Rest").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    ResItems item = child.getValue(ResItems.class);
//                    Log.d("item",item.getName());
//                    String key = child.getKey();
//                    Log.d("item",key);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


ResItems item = new ResItems();
item.name = "Leela Hotel";
item.timing = "10 Am - 12 AM";
item.min_order = "Rs. 200";
item.res_addr = "Karkardooma, Delhi";
item.res_phno = "8493746372";
item.res_image = "https://www.theleela.com/img/resized-iamges/brand-page/destination-hotels/LACH.jpg";
item.id = "-M5q1gTwSmn3L_6b4jgX";
item.items = new ArrayList<>();
per_product product = new per_product();
product.productName = "Paneer Tikka";
product.rate = 140;
product.type = 0;
item.items.add(product);
product = new per_product();
product.productName = "Dal";
product.rate = 300;
product.type = 1;
item.items.add(product);
product = new per_product();
product.productName = "Ras Malai";
product.rate = 25;
product.type = 2;

item.items.add(product);
        product = new per_product();
        product.productName = "Chilli Paneer";
        product.rate = 180;
        product.type = 0;
        item.items.add(product);
        product = new per_product();
        product.productName = "Paneer";
        product.rate = 200;
        product.type = 1;
        item.items.add(product);
        product = new per_product();
        product.productName = "Kheer";
        product.rate = 35;
        product.type = 2;

        item.items.add(product);

FirebaseDatabase.getInstance().getReference().child("Rest").child("-M5q1gTwSmn3L_6b4jgX").setValue(item);

    }


    //Places Search
    private void placesAutocomplete() {


        try {

            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry("IN")
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                    .build();

            // .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES) filter for cities

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(typeFilter)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.

        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
            e.printStackTrace();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);
                String city = place.getName().toString();

                Toast.makeText(this, "City: " + city, Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(Dashboard.this, RestaurantActivity.class);

                myIntent.putExtra("city", city);
                startActivity(myIntent);

                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }





    public void setShowCaseView(){
        Target homeTarget = new Target() {
            @Override
            public Point getPoint() {
                // Get approximate position of home icon's center
                int actionBarSize = toolbar.getHeight();
                int x = actionBarSize /2 ;
                int y = actionBarSize /2 ;
                return new Point(x, y);
            }
        };
        new ShowcaseView.Builder(this)
                .setContentTitle("Welcome to FOOD")
                .setContentText("Here You can manage your orders!")
                .setTarget(homeTarget)
                .build();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        drawer.closeDrawer(GravityCompat.START);

        int id = item.getItemId();

        if (id == R.id.log_out){
            signOut();
        }

        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }


    private void signOut(){

        SharedPreferences pref = getApplicationContext().getSharedPreferences("food", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email",null);
        editor.putString("time","1");
        editor.apply();


        Intent myIntent = new Intent(Dashboard.this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(myIntent);


        Toast.makeText(Dashboard.this, " User Sign Out Successfully", Toast.LENGTH_SHORT).show();
    }



}

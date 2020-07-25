package com.example.anshulsingh.foodpilu;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CardAdapterViewHolder>{
    ArrayList<per_product> cartItems;
    ArrayList<Integer> count ;


    public CartAdapter(ArrayList<per_product> cartItems, ArrayList<Integer> count) {
        this.cartItems = cartItems;
        this.count = count;
    }

    public CartAdapter() {
    }


    @NonNull
    @Override
    public CardAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.cart_one_unit, parent, false);
        return new CardAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapterViewHolder holder, int position) {
        final per_product thisProduct = cartItems.get(position);
              Integer thisProductCount = count.get(position);


        holder.ItemName.setText(thisProduct.productName);
        holder.Price.setText(String.valueOf(thisProduct.rate));
        holder.costCart.setText(String.valueOf(thisProduct.rate*thisProductCount));
        holder.NoOfItemsCart.setText(String.valueOf(thisProductCount));
        holder.PerCostItem.setText(String.valueOf(thisProduct.rate));


    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CardAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView Price,ItemName,costCart,PerCostItem,NoOfItemsCart;


        public CardAdapterViewHolder(@NonNull View itemView) {

            super(itemView);
            Price = itemView.findViewById(R.id.priceCart);
            ItemName= itemView.findViewById(R.id.itemNameCart);
            costCart= itemView.findViewById(R.id.costCart);
            NoOfItemsCart = itemView.findViewById(R.id.NoOfItemsCart);
            PerCostItem = itemView.findViewById(R.id.PerCostItem);

        }
    }
}

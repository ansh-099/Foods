package com.example.anshulsingh.foodpilu;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StatersAdapter extends RecyclerView.Adapter<StatersAdapter.StatersAdapterViewHolder>{

    ArrayList<per_product> allProducts= new ArrayList<>();
    Integer currno;
    CustomListener listener;
    Context mContext;


    public StatersAdapter(ArrayList<per_product> allProducts, Integer currno, CustomListener listener, Context mContext) {
        this.allProducts = allProducts;
        this.currno = currno;
        this.listener = listener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public StatersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(mContext);
        View itemView = li.inflate(R.layout.one_unit_menu, parent, false);
        return new StatersAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StatersAdapterViewHolder holder, int position) {

        final per_product perProduct = allProducts.get(position);
        holder.ItemName.setText(perProduct.productName);
        holder.Price.setText(String.valueOf(perProduct.rate));

        holder.additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer curr = Integer.valueOf(holder.curr_no.getText().toString());
                holder.curr_no.setText(String.valueOf(curr + 1));
                listener.onAdded(perProduct,"add");
            }
        });


        holder.lessitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer curr = Integer.valueOf(holder.curr_no.getText().toString());
                if(curr <= 0 ){
                    Toast.makeText(mContext, "Can't Be less than 0", Toast.LENGTH_SHORT).show();
                }else {
                    holder.curr_no.setText(String.valueOf(curr - 1));
                    listener.onAdded(perProduct,"sub");
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return allProducts.size();
    }



    public class StatersAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView Price,ItemName,curr_no ;
        Button additem,lessitem ;

        public StatersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            Price = itemView.findViewById(R.id.price);
            ItemName= itemView.findViewById(R.id.itemName);
            additem= itemView.findViewById(R.id.additem);
            lessitem= itemView.findViewById(R.id.lessitem);
            curr_no= itemView.findViewById(R.id.curr_no);

        }
    }
}

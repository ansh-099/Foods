package com.example.anshulsingh.foodpilu;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ResRecyclerViewAdapter extends RecyclerView.Adapter<ResRecyclerViewAdapter.RestaurantViewHolder> {

     Context mContext;
     List<ResItems> ResItemsList;


    public ResRecyclerViewAdapter(Context mContext, List<ResItems> ResItemsList) {
        this.mContext = mContext;
        this.ResItemsList = ResItemsList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.restaurant_row, parent, false);

        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantViewHolder holder, int position) {

        final ResItems ResItemsOBJ = ResItemsList.get(position);
        holder.ResName.setText(ResItemsOBJ.name);
        holder.ResTiming.setText(ResItemsOBJ.timing);
        holder.ResAddress.setText(ResItemsOBJ.res_addr);
        holder.ResPhoneNo.setText(ResItemsOBJ.res_phno);
        holder.ResMinOrder.setText(ResItemsOBJ.min_order);
        Picasso.get().load(ResItemsOBJ.res_image).fit().into(holder.ResImage);

        holder.ResCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(mContext,OneRestaurantMenu.class);
                i.putExtra("id",ResItemsOBJ.id);
                mContext.startActivity(i);

            }
        });



    }

    @Override
    public int getItemCount() {
        return ResItemsList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        TextView ResName, ResTiming, ResAddress, ResPhoneNo,ResMinOrder;
        ImageView ResImage;
        CardView ResCard;


        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            ResPhoneNo= itemView.findViewById(R.id.res_phone);
            ResName = itemView.findViewById(R.id.res_name);
            ResMinOrder = itemView.findViewById(R.id.res_min_order);
            ResAddress = itemView.findViewById(R.id.res_address);
            ResImage = itemView.findViewById(R.id.ResImage);
            ResTiming = itemView.findViewById(R.id.res_timing);
            ResCard = itemView.findViewById(R.id.restCard);


        }
    }
}

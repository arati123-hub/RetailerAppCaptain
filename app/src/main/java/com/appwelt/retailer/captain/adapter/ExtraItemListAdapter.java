package com.appwelt.retailer.captain.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.model.ExtraItem;
import com.appwelt.retailer.captain.utils.DateConversionClass;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.GenerateRandom;
import com.appwelt.retailer.captain.utils.SharedPref;

import java.util.ArrayList;

public class ExtraItemListAdapter extends RecyclerView.Adapter<ExtraItemListAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ExtraItem item);
    }

    private LayoutInflater inflater;
    public static ArrayList<ExtraItem> response;
    Context context;
    Activity activity;
    String viewType;
    private final OnItemClickListener listener;


    public ExtraItemListAdapter(Context ctx, ArrayList<ExtraItem> response,String vs, OnItemClickListener listener) {
        context = ctx;
        activity = (Activity) ctx;
        viewType = vs;
        inflater = LayoutInflater.from(ctx);
        this.response = response;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_extra_item_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        FontStyle.FontStyle(context);
        holder.itemName.setTypeface(FontStyle.getFontRegular());
        holder.itemPrice.setTypeface(FontStyle.getFontRegular());

        String name =response.get(position).getExtra_item_name();
        name = name.replace("(EXT)","");

        holder.itemName.setText(name);
        holder.itemPrice.setText(response.get(position).getExtra_item_price());
        holder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.extra_item));
        holder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.black));

        holder.mainDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(response.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return response.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView itemName,itemPrice;
        ImageView itemImage;
        LinearLayoutCompat mainDiv;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.name);
            itemPrice = itemView.findViewById(R.id.price);
            itemImage = itemView.findViewById(R.id.item_img);
            mainDiv = itemView.findViewById(R.id.main_div);
        }

    }
}
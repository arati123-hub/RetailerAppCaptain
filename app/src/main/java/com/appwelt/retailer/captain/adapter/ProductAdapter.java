package com.appwelt.retailer.captain.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.model.ProductDetails;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.SharedPref;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(List<ProductDetails> item, int position);
    }

    private LayoutInflater inflater;
    public static List<ProductDetails> editModelArrayList;
    Context context;
    Activity activity;
    private final OnItemClickListener listener;


    public ProductAdapter(Context ctx, List<ProductDetails> editModelArrayList, OnItemClickListener listener) {
        context = ctx;
        activity = (Activity) ctx;
        inflater = LayoutInflater.from(ctx);
        this.editModelArrayList = editModelArrayList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_menuitem_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        FontStyle.FontStyle(context);

        String product_id = editModelArrayList.get(position).getProduct_id();
        holder.itemName.setTypeface(FontStyle.getFontRegular());
        holder.itemPrice.setTypeface(FontStyle.getFontRegular());
        holder.itemNo.setTypeface(FontStyle.getFontRegular());
        holder.itemName1.setTypeface(FontStyle.getFontRegular());
        holder.itemPrice1.setTypeface(FontStyle.getFontRegular());
        holder.itemNo1.setTypeface(FontStyle.getFontRegular());

        String productSr = product_id;
        holder.itemNo.setText("PRO0"+productSr);
        String productName = editModelArrayList.get(position).getProduct_name();
        String productPrice = editModelArrayList.get(position).getProduct_price();

        holder.itemName.setText(productName);
        holder.itemName.setText(productPrice);

        holder.itemNo1.setText(editModelArrayList.get(position).getProduct_code());
        holder.itemName1.setText(editModelArrayList.get(position).getProduct_name());
        holder.itemPrice1.setText(editModelArrayList.get(position).getProduct_price());

        String productPhoto =editModelArrayList.get(position).getProduct_photo();
        File imgFile = new File(Environment.getExternalStorageDirectory()+"/RetailerApp/images/" + productPhoto);
        if (imgFile!=null){
            Picasso.with(context)
                    .load(imgFile)
                    .error(R.drawable.ic_photo)
                    .into(holder.itemImage);
        }
        if(!imgFile.exists() || SharedPref.getString(context,"pro_cat_img").equals("0")){
            holder.mainDiv.setVisibility(View.GONE);
            holder.noImgDiv.setVisibility(View.VISIBLE);
        }else{
            holder.mainDiv.setVisibility(View.VISIBLE);
            holder.noImgDiv.setVisibility(View.GONE);
        }
        holder.mainDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(editModelArrayList,position);
                holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.gray));
                Thread background = new Thread() {
                    public void run() {
                        try {
                            sleep(300);
                            holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.white));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                background.start();

            }
        });
        holder.noImgDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(editModelArrayList,position);
                holder.noImgDiv.setBackgroundColor(context.getResources().getColor(R.color.gray));
                Thread background = new Thread() {
                    public void run() {

                        try {
                            sleep(300);
                            holder.noImgDiv.setBackgroundColor(context.getResources().getColor(R.color.white));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                background.start();

            }
        });
    }
    @Override
    public int getItemCount() {
        return editModelArrayList.size();
    }

    public void updateList(ArrayList<ProductDetails> list){
        editModelArrayList = list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView itemNo,itemName,itemPrice,itemNo1,itemName1,itemPrice1;
        ImageView itemImage;
        LinearLayoutCompat mainDiv,noImgDiv;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemNo= itemView.findViewById(R.id.item_id);
            itemName = itemView.findViewById(R.id.name);
            itemPrice = itemView.findViewById(R.id.price);
            itemNo1 = itemView.findViewById(R.id.itemId);
            itemName1 = itemView.findViewById(R.id.itemName);
            itemPrice1 = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.item_img);
            mainDiv = itemView.findViewById(R.id.main_div);
            noImgDiv = itemView.findViewById(R.id.no_img_div);
        }

    }
}

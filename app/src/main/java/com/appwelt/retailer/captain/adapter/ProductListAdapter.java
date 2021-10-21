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
import com.appwelt.retailer.captain.model.ProductCategoryDetails;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.SharedPref;
import com.appwelt.retailer.captain.utils.sqlitedatabase.DatabaseHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ArrayList<ProductCategoryDetails> item, int position);
    }

    private LayoutInflater inflater;
    public static ArrayList<ProductCategoryDetails> editModelArrayList;
    Context context;
    Activity activity;
    private final OnItemClickListener listener;


    public ProductListAdapter(Context ctx, ArrayList<ProductCategoryDetails> editModelArrayList, OnItemClickListener listener) {
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

        String DATABASE_NAME = SharedPref.getString(context,"database_name");
        DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);
        String product_id = editModelArrayList.get(position).getProduct_id();
        holder.itemName.setTypeface(FontStyle.getFontRegular());
        holder.itemPrice.setTypeface(FontStyle.getFontRegular());
        holder.itemNo.setTypeface(FontStyle.getFontRegular());

        String productSr = databaseHelper.selectByID("tbl_product","product_id",product_id,"product_sequence");
        holder.itemNo.setText("PRO0"+productSr);
        holder.itemName.setText(databaseHelper.selectByID("tbl_language_text","language_reference_id","PN_"+product_id,"language_text"));
        holder.itemPrice.setText(databaseHelper.selectByID("tbl_product","product_id",product_id,"product_price"));
        File imgFile = new File(Environment.getExternalStorageDirectory()+"/RetailerApp/images/"+databaseHelper.selectByID("tbl_product","product_id",product_id,"product_photo"));
        if (imgFile!=null){
            Picasso.with(context)
                    .load(imgFile)
                    .error(R.drawable.ic_photo)
                    .into(holder.itemImage);
        }
        if(!imgFile.exists()){
            holder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_photo));
            holder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.black));
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
    }
    @Override
    public int getItemCount() {
        return editModelArrayList.size();
    }

    public void updateList(ArrayList<ProductCategoryDetails> list){
        editModelArrayList = list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView itemNo,itemName,itemPrice;
        ImageView itemImage;
        LinearLayoutCompat mainDiv;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemNo= itemView.findViewById(R.id.item_id);
            itemName = itemView.findViewById(R.id.name);
            itemPrice = itemView.findViewById(R.id.price);
            itemImage = itemView.findViewById(R.id.item_img);
            mainDiv = itemView.findViewById(R.id.main_div);
        }

    }
}

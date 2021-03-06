package com.appwelt.retailer.captain.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.model.OrderDetail;
import com.appwelt.retailer.captain.utils.FontStyle;

import java.util.List;

public class KOTListAdapter extends RecyclerView.Adapter<KOTListAdapter.MyViewHolder> {

    private static final String TAG = "ORDER_LIST";
    public interface OnItemClickListener {
        void onItemClick(List<OrderDetail> item);
    }

    private LayoutInflater inflater;
    public static List<OrderDetail> response;
    Context context;
    Activity activity;
    private final OnItemClickListener listener;

    public KOTListAdapter(Context ctx, List<OrderDetail> response, OnItemClickListener listener) {

        context = ctx;
        activity = (Activity) ctx;
        inflater = LayoutInflater.from(ctx);
        this.response = response;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_orderitem_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        FontStyle.FontStyle(context);
        holder.itemName.setTypeface(FontStyle.getFontRegular());
        holder.itemPrice.setTypeface(FontStyle.getFontRegular());
        holder.itemQuanitity.setTypeface(FontStyle.getFontRegular());
        holder.itemAmount.setTypeface(FontStyle.getFontRegular());

        holder.itemName.setText(response.get(position).getProduct_name());
        holder.itemPrice.setText(response.get(position).getProduct_price());
        holder.itemQuanitity.setText(response.get(position).getProduct_quantity());
        holder.itemAmount.setText(String.valueOf(
                Double.parseDouble(response.get(position).getProduct_price())
                * Double.parseDouble(response.get(position).getProduct_quantity())
        ));
        for (Drawable drawable : holder.itemAmount.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN));
            }
        }

        holder.btnDelete.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return response.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView itemName,itemPrice,itemQuanitity,itemAmount;
        LinearLayoutCompat mainDiv,qunatityDiv;
        ImageView btnDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.name);
            itemPrice = itemView.findViewById(R.id.price);
            itemQuanitity = itemView.findViewById(R.id.quanitity);
            itemAmount = itemView.findViewById(R.id.amount);

            btnDelete = itemView.findViewById(R.id.btn_delete);

            mainDiv = itemView.findViewById(R.id.main_div);
            qunatityDiv = itemView.findViewById(R.id.quantity_div);
        }

    }
}

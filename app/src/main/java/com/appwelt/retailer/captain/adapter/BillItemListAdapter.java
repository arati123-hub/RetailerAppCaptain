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
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.activities.TableSelectionActivity;
import com.appwelt.retailer.captain.model.KOTItems;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.SharedPref;
import com.appwelt.retailer.captain.utils.sqlitedatabase.DatabaseHelper;

import java.util.ArrayList;

public class BillItemListAdapter extends RecyclerView.Adapter<BillItemListAdapter.MyViewHolder> {

    private static final String TAG = "ORDER_LIST";
    public interface OnItemClickListener {
        void onItemClick(ArrayList<KOTItems> item);
    }

    private LayoutInflater inflater;
    public static ArrayList<KOTItems> response;
    Context context;
    Activity activity;
    private final OnItemClickListener listener;
    String food_id,bar_id ;

    public BillItemListAdapter(Context ctx, ArrayList<KOTItems> response, String food_id, String bar_id, OnItemClickListener listener) {

        context = ctx;
        activity = (Activity) ctx;
        inflater = LayoutInflater.from(ctx);
        this.response = response;
        this.listener = listener;
        this.food_id = food_id;
        this.bar_id = bar_id;
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

        String DATABASE_NAME = SharedPref.getString(context,"database_name");
        DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);
        holder.itemName.setText(response.get(position).getName());
        holder.itemPrice.setText(response.get(position).getPrice());
        holder.itemQuanitity.setText(response.get(position).getQuantity());
        holder.itemAmount.setText(String.valueOf(
                Double.parseDouble(response.get(position).getPrice())
                * Double.parseDouble(response.get(position).getQuantity())
        ));
        for (Drawable drawable : holder.itemAmount.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN));
            }
        }

        if (response.get(position).getType().equals("FOOD_PAID")){
            holder.btnDelete.setVisibility(View.GONE);
        }else if (response.get(position).getType().equals("BAR_PAID")){
            holder.btnDelete.setVisibility(View.GONE);
        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (response.get(position).getType().equals("FOOD")){
                    if (databaseHelper.deleteDetailsByTwo("tbl_bill_details","bill_id","bill_detail_product_id",food_id,response.get(position).getId())){
                        response.remove(position);
                        listener.onItemClick(response);
                    }else{
                        DialogBox(context.getResources().getString(R.string.fail_order_item_delete),null);
                    }
                }else if (response.get(position).getType().equals("BAR")){
                    if (databaseHelper.deleteDetailsByTwo("tbl_bill_details","bill_id","bill_detail_product_id",bar_id,response.get(position).getId())){
                        response.remove(position);
                        listener.onItemClick(response);
                    }else{
                        DialogBox(context.getResources().getString(R.string.fail_order_item_delete),null);
                    }
                }else if (response.get(position).getType().equals("FOOD_EXTRA")){
                    if (databaseHelper.deleteDetailsByTwo("tbl_order_extra_item","order_id","order_extra_item_id",food_id,response.get(position).getId())){
                        response.remove(position);
                        listener.onItemClick(response);
                    }else{
                        DialogBox(context.getResources().getString(R.string.fail_item_delete),null);
                    }
                }else if (response.get(position).getType().equals("BAR_EXTRA")){
                    if (databaseHelper.deleteDetailsByTwo("tbl_order_extra_item","order_id","order_extra_item_id",bar_id,response.get(position).getId())){
                        response.remove(position);
                        listener.onItemClick(response);
                    }else{
                        DialogBox(context.getResources().getString(R.string.fail_item_delete),null);
                    }
                }
            }
        });
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

    public ArrayList<KOTItems> getSelected() {
        return response;
    }

    private void DialogBox(String msg, Class<TableSelectionActivity> tableSelectionActivityClass) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatTextView edt_dialog_title = dialog.findViewById(R.id.edit_title);
        AppCompatTextView edt_dialog_msg = dialog.findViewById(R.id.edit_msg);
        AppCompatTextView btn_dialog_cofirm = dialog.findViewById(R.id.confirm_button);

        edt_dialog_title.setText(context.getResources().getString(R.string.app_name));
        edt_dialog_msg.setText(msg);
        btn_dialog_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableSelectionActivityClass != null){
                    context.startActivity(new Intent(context,tableSelectionActivityClass));
                }else{
                    dialog.dismiss();
                }
            }
        });

        edt_dialog_title.setTypeface(FontStyle.getFontRegular());
        edt_dialog_msg.setTypeface(FontStyle.getFontRegular());
        btn_dialog_cofirm.setTypeface(FontStyle.getFontRegular());

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }
}

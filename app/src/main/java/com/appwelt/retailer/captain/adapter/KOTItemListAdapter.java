package com.appwelt.retailer.captain.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
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

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
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

public class KOTItemListAdapter extends RecyclerView.Adapter<KOTItemListAdapter.MyViewHolder> {

    private static final String TAG = "ORDER_LIST";
    public interface OnItemClickListener {
        void onItemClick(ArrayList<KOTItems> item);
    }
    private LayoutInflater inflater;
    public static ArrayList<KOTItems> response;
    Context context;
    Activity activity;
    private final OnItemClickListener listener;
    String order_id ;

    public KOTItemListAdapter(Context ctx, ArrayList<KOTItems> response, String order_id, OnItemClickListener listener) {

        context = ctx;
        activity = (Activity) ctx;
        inflater = LayoutInflater.from(ctx);
        this.response = response;
        this.listener = listener;
        this.order_id = order_id;
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

        if (response.get(position).getType().equals("KOT")){
            holder.qunatityDiv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Dialog customDialog = new Dialog(context);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.view_special_instruction);
                    customDialog.setCancelable(true);
                    customDialog.setCanceledOnTouchOutside(true);
                    Window window = customDialog.getWindow();
                    assert window != null;
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    AppCompatTextView name = customDialog.findViewById(R.id.product_name);
                    AppCompatTextView description = customDialog.findViewById(R.id.product_description);
                    AppCompatEditText specialInstruction = customDialog.findViewById(R.id.special_instruction);

                    AppCompatTextView saveBtn = customDialog.findViewById(R.id.btn_submit);
                    AppCompatImageView btnClose = customDialog.findViewById(R.id.close_btn);

                    name.setTypeface(FontStyle.getFontRegular());
                    description.setTypeface(FontStyle.getFontRegular());
                    specialInstruction.setTypeface(FontStyle.getFontRegular());
                    saveBtn.setTypeface(FontStyle.getFontRegular());

                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    String DATABASE_NAME = SharedPref.getString(context,"database_name");
                    DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);

                    String order_detail_id = databaseHelper.selectByTwoID("tbl_order_details","order_id","product_id",order_id,response.get(position).getId(),"order_details_id");
                    name.setText(databaseHelper.selectByID("tbl_language_text","language_reference_id","PN_"+response.get(position).getId(),"language_text"));
                    description.setText("( "+databaseHelper.selectByID("tbl_language_text","language_reference_id","PD_"+response.get(position).getId(),"language_text")+" )");
                    specialInstruction.setText(databaseHelper.selectByID("tbl_order_details","order_details_id",order_detail_id,"order_details_special_note"));
                    saveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String instruction = specialInstruction.getText().toString();
                            if (instruction.isEmpty()){
                                specialInstruction.setError(context.getResources().getString(R.string.field_required));
                            }else{
                                ContentValues values = new ContentValues();
                                values.put("order_details_special_note",instruction);
                                if (databaseHelper.updateDetails("tbl_order_details","order_details_id",order_detail_id,values)){
                                    listener.onItemClick(response);
                                }else{
                                    DialogBox(context.getResources().getString(R.string.fail_to_update_special_note),null);customDialog.dismiss();
                                }
                                customDialog.dismiss();
                            }
                        }
                    });
                    customDialog.setCanceledOnTouchOutside(false);
                    customDialog.setCancelable(false);
                    customDialog.show();
                    return true;
                }
            });

            holder.qunatityDiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog customDialog = new Dialog(context);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.view_change_quanitity);
                    customDialog.setCancelable(true);
                    customDialog.setCanceledOnTouchOutside(true);
                    Window window = customDialog.getWindow();
                    assert window != null;
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    AppCompatTextView name = customDialog.findViewById(R.id.product_name);
                    AppCompatTextView description = customDialog.findViewById(R.id.product_description);
                    AppCompatEditText quantity = customDialog.findViewById(R.id.quanitity);
                    AppCompatButton addBtn = customDialog.findViewById(R.id.btn_add);
                    AppCompatButton subBtn = customDialog.findViewById(R.id.btn_sub);
                    AppCompatImageView btnClose = customDialog.findViewById(R.id.close_btn);

                    AppCompatTextView saveBtn = customDialog.findViewById(R.id.btn_submit);
                    AppCompatTextView closeBtn = customDialog.findViewById(R.id.btn_cancel);

                    name.setTypeface(FontStyle.getFontRegular());
                    description.setTypeface(FontStyle.getFontRegular());
                    quantity.setTypeface(FontStyle.getFontRegular());
                    addBtn.setTypeface(FontStyle.getFontRegular());
                    subBtn.setTypeface(FontStyle.getFontRegular());
                    saveBtn.setTypeface(FontStyle.getFontRegular());
                    closeBtn.setTypeface(FontStyle.getFontRegular());

                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    String order_detail_id = databaseHelper.selectByTwoID("tbl_order_details","order_id","product_id",order_id,response.get(position).getId(),"order_details_id");
                    name.setText(databaseHelper.selectByID("tbl_language_text","language_reference_id","PN_"+response.get(position).getId(),"language_text"));
                    description.setText("( "+databaseHelper.selectByID("tbl_language_text","language_reference_id","PD_"+response.get(position).getId(),"language_text")+" )");
                    quantity.setText(holder.itemQuanitity.getText().toString());
                    closeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (databaseHelper.deleteDetails("tbl_order_details","order_details_id",order_detail_id)){
                                listener.onItemClick(response);
                            }else{
                                DialogBox(context.getResources().getString(R.string.fail_order_item_delete),null);
                            }
                            customDialog.dismiss();
                        }
                    });
                    addBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString())+1));
                        }});
                    subBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (Integer.valueOf(quantity.getText().toString())==1){
                                if (databaseHelper.deleteDetails("tbl_order_details","order_details_id",order_detail_id)){
                                    listener.onItemClick(response);
                                }else{
                                    DialogBox(context.getResources().getString(R.string.fail_order_item_delete),null);
                                }
                                customDialog.dismiss();
                            }else if (Integer.valueOf(quantity.getText().toString())!=0){
                                quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString())-1));
                            }
                        }
                    });
                    saveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int total = Integer.valueOf(quantity.getText().toString());

                            if (total==0){
                                if (databaseHelper.deleteDetails("tbl_order_details","order_details_id",order_detail_id)){
                                    listener.onItemClick(response);
                                }else{
                                    DialogBox(context.getResources().getString(R.string.fail_order_item_delete),null);
                                }
                                customDialog.dismiss();
                            }else{

                                ContentValues values = new ContentValues();
                                values.put("order_details_order_qty",String.valueOf(total));
                                if (databaseHelper.updateDetails("tbl_order_details","order_details_id",order_detail_id,values)){
                                    listener.onItemClick(response);
                                }else{
                                    DialogBox(context.getResources().getString(R.string.fail_to_update_quantity),null);customDialog.dismiss();
                                }
                            }
                            customDialog.dismiss();
                        }
                    });
//                    customDialog.setCanceledOnTouchOutside(false);
//                    customDialog.setCancelable(false);
                    customDialog.show();
                }
            });

        }else if (response.get(position).getType().equals("BILL")){
            holder.btnDelete.setVisibility(View.GONE);
        }else if (response.get(position).getType().equals("EXTRA_BILL")){
            holder.btnDelete.setVisibility(View.GONE);
        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (response.get(position).getType().equals("KOT")){
                    if (databaseHelper.deleteDetailsByTwo("tbl_order_details","order_id","product_id",order_id,response.get(position).getId())){
                        response.remove(position);
                        listener.onItemClick(response);
                    }else{
                        DialogBox(context.getResources().getString(R.string.fail_order_item_delete),null);
                    }
                }else if (response.get(position).getType().equals("EXTRA_KOT")){
                    if (databaseHelper.deleteDetailsByTwo("tbl_order_extra_item","order_id","order_extra_item_id",order_id,response.get(position).getId())){
                        response.remove(position);
                        listener.onItemClick(response);
                    }else{
                        DialogBox(context.getResources().getString(R.string.fail_order_item_delete),null);
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

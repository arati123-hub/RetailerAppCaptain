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
import com.appwelt.retailer.captain.utils.sqlitedatabase.DatabaseHelper;

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
//        holder.itemId.setTypeface(FontStyle.getFontRegular());

        String DATABASE_NAME = SharedPref.getString(context,"database_name");
        DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);

        if (response.get(position).getExtra_item_id().equals("0")){
            holder.itemName.setText(context.getResources().getString(R.string.add_new));
            holder.itemPrice.setVisibility(View.GONE);
            holder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
            holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
        }else {

            holder.itemName.setText(response.get(position).getExtra_item_name());
            holder.itemPrice.setText(response.get(position).getExtra_item_price());
            holder.itemImage.setImageDrawable(context.getResources().getDrawable(R.drawable.extra_item));
            holder.itemImage.setColorFilter(ContextCompat.getColor(context, R.color.black));
        }
        holder.mainDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (response.get(position).getExtra_item_id().equals("0")){
                    addMenuItem(response.get(position));
                }else{
                    listener.onItemClick(response.get(position));
                }
            }
        });
    }

    private void addMenuItem(ExtraItem extraItem) {
        Dialog customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.view_add_extra_item_dialog);
        customDialog.setCancelable(true);
        customDialog.setCanceledOnTouchOutside(true);
        Window window = customDialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatTextView dialogTitle = customDialog.findViewById(R.id.edit_title);
        AppCompatTextView selectTxt = customDialog.findViewById(R.id.select_txt);
        AppCompatTextView txtName = customDialog.findViewById(R.id.txt_name);
        AppCompatTextView txtPrice = customDialog.findViewById(R.id.txt_price);

        AppCompatTextView btnSubmit = customDialog.findViewById(R.id.btn_submit);
        AppCompatTextView btnDelete = customDialog.findViewById(R.id.btn_delete);

        AppCompatEditText edtName = customDialog.findViewById(R.id.edt_name);
        AppCompatEditText edtPrice = customDialog.findViewById(R.id.edt_price);

        LinearLayout dragDropText = customDialog.findViewById(R.id.drapDiv);

        dialogTitle.setTypeface(FontStyle.getFontRegular());
        selectTxt.setTypeface(FontStyle.getFontRegular());
        txtName.setTypeface(FontStyle.getFontRegular());
        txtPrice.setTypeface(FontStyle.getFontRegular());
        edtName.setTypeface(FontStyle.getFontRegular());
        edtPrice.setTypeface(FontStyle.getFontRegular());
        btnSubmit.setTypeface(FontStyle.getFontRegular());

        String DATABASE_NAME = SharedPref.getString(context,"database_name");
        DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);

        btnDelete.setVisibility(View.GONE);
        dialogTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });
        dragDropText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                importCSVMenuItem(category_id);
//                customDialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String price = edtPrice.getText().toString();
                if (name.isEmpty()){
                    edtName.setError(context.getResources().getString(R.string.field_required));
                }else if (price.isEmpty()){
                    edtPrice.setError(context.getResources().getString(R.string.field_required));
                }else {

                    String LangId = new GenerateRandom().getRandomString();
                    String proId = new GenerateRandom().getRandomString();

                    ContentValues nameLangValues = new ContentValues();
                    nameLangValues.put("language_text_id", LangId);
                    nameLangValues.put("language_id", context.getResources().getString(R.string.langauge_selected));
                    nameLangValues.put("language_text", name);
                    nameLangValues.put("language_reference_id", "EN_"+proId);
                    nameLangValues.put("table_id", proId);
                    nameLangValues.put("language_created_on", new DateConversionClass().currentDateApoch());
                    nameLangValues.put("language_created_by", SharedPref.getString(context,"organisation_id"));

                    ContentValues provalues = new ContentValues();
                    provalues.put("extra_item_id", proId);
                    provalues.put("extra_item_name",LangId );
                    provalues.put("extra_item_price", price);
                    provalues.put("extra_item_created_on", new DateConversionClass().currentDateApoch());
                    provalues.put("extra_item_created_by", SharedPref.getString(context,"organisation_id"));

                    String DATABASE_NAME = SharedPref.getString(context,"database_name");
                    DatabaseHelper dataBaseHelper = new DatabaseHelper(context,DATABASE_NAME);
                    if (dataBaseHelper.insertDetails(nameLangValues, "tbl_language_text")){
                        if (dataBaseHelper.insertDetails(provalues, "tbl_extra_item")){
                            listener.onItemClick(extraItem);
                            customDialog.dismiss();
                        }else{ DialogBox(context.getResources().getString(R.string.extra_item_insert_fail)); }
                    }else{ DialogBox(context.getResources().getString(R.string.product_name_insert_fail)); }
                }
            }
        });

        dialogTitle.setText(context.getResources().getString(R.string.add_extra_item));
        customDialog.setCanceledOnTouchOutside(false);
        customDialog.setCancelable(false);
        customDialog.show();
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

    private void DialogBox(String msg) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.view_dialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AppCompatTextView edt_dialog_title = dialog.findViewById(R.id.edit_title);
        AppCompatTextView edt_dialog_msg = dialog.findViewById(R.id.edit_msg);
        AppCompatTextView btn_dialog_cofirm = dialog.findViewById(R.id.confirm_button);

        edt_dialog_title.setText(context.getResources().getString(R.string.app_name));
        edt_dialog_msg.setText(msg);
        btn_dialog_cofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
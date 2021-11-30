package com.appwelt.retailer.captain.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import com.appwelt.retailer.captain.model.OrderDetail;
import com.appwelt.retailer.captain.utils.FontStyle;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

        private static final String TAG = "ORDER_LIST";
        public interface OnItemClickListener {
            void onItemClick(List<OrderDetail> item);
        }
        private LayoutInflater inflater;
        public static List<OrderDetail> response;
        Context context;
        Activity activity;
        private final OnItemClickListener listener;

    public OrderListAdapter(Context ctx, List<OrderDetail> response, OnItemClickListener listener) {

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

            if (response.get(position).getProduct_kot_yn().equals("NO")){
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
                        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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

                        name.setText(response.get(position).getProduct_name());
                        specialInstruction.setText(response.get(position).getProduct_special_note());
                        saveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String instruction = specialInstruction.getText().toString();
                                if (instruction.isEmpty()){
                                    specialInstruction.setError(context.getResources().getString(R.string.field_required));
                                }else{
                                    response.get(position).setProduct_special_note(instruction);
                                    listener.onItemClick(response);
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
                        window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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

                        name.setText(response.get(position).getProduct_name());
                        quantity.setText(holder.itemQuanitity.getText().toString());
                        closeBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                response.remove(position);
                                listener.onItemClick(response);
                                customDialog.dismiss();
                            }
                        });
                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (quantity.getText().toString().length() != 0 && Integer.valueOf(quantity.getText().toString()) < 999){
                                    quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString())+1));
                                }else{
                                    quantity.setError(context.getResources().getString(R.string.required));
                                }
                            }});
                        subBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (quantity.getText().toString().length() != 0){
                                    if (Integer.valueOf(quantity.getText().toString())==1){
                                        response.remove(position);
                                        listener.onItemClick(response);
                                        customDialog.dismiss();
                                    }else if (Integer.valueOf(quantity.getText().toString())!=0){
                                        quantity.setText(String.valueOf(Integer.valueOf(quantity.getText().toString())-1));
                                    }
                                }else{
                                    quantity.setError(context.getResources().getString(R.string.required));
                                }
                            }
                        });
                        saveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (quantity.getText().toString().length() != 0){
                                    int total = Integer.valueOf(quantity.getText().toString());

                                    if (total==0){
                                        response.remove(position);
                                        customDialog.dismiss();
                                    }else{
                                        response.get(position).setProduct_quantity(String.valueOf(total));
                                        listener.onItemClick(response);
                                    }
                                    customDialog.dismiss();
                                }else{
                                    quantity.setError(context.getResources().getString(R.string.required));
                                }

                            }
                        });
                        customDialog.show();
                    }
                });

            }else if (response.get(position).getProduct_kot_yn().equals("YES")){
                holder.btnDelete.setVisibility(View.GONE);
            }
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    response.remove(position);
                    listener.onItemClick(response);
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


}

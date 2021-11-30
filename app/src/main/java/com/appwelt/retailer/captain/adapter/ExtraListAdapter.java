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

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.activities.TableSelectionActivity;
import com.appwelt.retailer.captain.model.ExtraItemList;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.appwelt.retailer.captain.utils.SharedPref;

import java.util.List;

public class ExtraListAdapter extends RecyclerView.Adapter<ExtraListAdapter.MyViewHolder> {

        private static final String TAG = "ORDER_LIST";
        public interface OnItemClickListener {
            void onItemClick(List<ExtraItemList> item);
        }
        private LayoutInflater inflater;
        public static List<ExtraItemList> response;
        Context context;
        Activity activity;
        private final OnItemClickListener listener;

    public ExtraListAdapter(Context ctx, List<ExtraItemList> response, OnItemClickListener listener) {

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

            String name =response.get(position).getOrder_extra_item_name();
            name = name.replace("(EXT)","");

            holder.itemName.setText(name);
            holder.itemPrice.setText(response.get(position).getOrder_extra_item_price());
            holder.itemQuanitity.setText(response.get(position).getOrder_extra_item_qty());
            holder.itemAmount.setText(String.valueOf(
                    Double.parseDouble(response.get(position).getOrder_extra_item_price())
                            * Double.parseDouble(response.get(position).getOrder_extra_item_qty())
            ));
            for (Drawable drawable : holder.itemAmount.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN));
                }
            }

           if (response.get(position).getOrder_extra_item_kot_yn().equals("YES")){
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

        public List<ExtraItemList> getSelected() {
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
            window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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

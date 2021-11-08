package com.appwelt.retailer.captain.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appwelt.retailer.captain.R;
import com.appwelt.retailer.captain.model.TableListDetails;
import com.appwelt.retailer.captain.utils.FontStyle;

import java.io.File;
import java.util.ArrayList;

public class TableDetailsAdapter extends RecyclerView.Adapter<TableDetailsAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(TableListDetails item);
    }
    private Context context;
    private ArrayList<TableListDetails> tableListDetails;
    private int checkedPosition = 100;
    private final OnItemClickListener listener;

    public TableDetailsAdapter(Context context, ArrayList<TableListDetails> tableListDetails, OnItemClickListener listener) {
        this.context = context;
        this.tableListDetails = tableListDetails;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_table_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FontStyle.FontStyle(context);
        holder.tableNo.setTypeface(FontStyle.getFontRegular());
        holder.tableTitle.setTypeface(FontStyle.getFontRegular());

        if (checkedPosition == -1) {
            holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.tableTitle.setTextColor(context.getResources().getColor(R.color.black));
            holder.tableNo.setTextColor(context.getResources().getColor(R.color.black));
        } else {
            if (checkedPosition == position) {
                holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.gray));
                holder.tableTitle.setTextColor(context.getResources().getColor(R.color.black));
                holder.tableNo.setTextColor(context.getResources().getColor(R.color.black));
            } else {
                holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.tableTitle.setTextColor(context.getResources().getColor(R.color.black));
                holder.tableNo.setTextColor(context.getResources().getColor(R.color.black));
            }
        }

        String table_food_status =  "", table_bar_status =  "";

        if (tableListDetails.get(position).getFood_data() != null){
            table_food_status =  tableListDetails.get(position).getFood_data().getStatus();
            if (table_food_status == null) { table_food_status = ""; }
        }


        if (tableListDetails.get(position).getBar_data() != null){
            table_bar_status =  tableListDetails.get(position).getBar_data().getStatus();
            if (table_bar_status == null) { table_bar_status = ""; }
        }

        String tableName = tableListDetails.get(position).getCollector_name();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (table_food_status.length()!=0 && table_bar_status.length()!=0){
                if (table_food_status.equals("1") && table_bar_status.equals("1")) {
                    try {
                        holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.tableKOT));
                    }
                    catch (Resources.NotFoundException e)
                    {
                        Log.e("Error", e.getStackTrace().toString());
                    }
                    holder.tableNo.setText(tableName+" - Multiple KOT");
                    holder.tableNo.setTextColor(context.getResources().getColor(R.color.white));
                }else if (table_food_status.equals("2") && table_bar_status.equals("2")){
                    try {
                        holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.tableBilled));
                    }
                    catch (Resources.NotFoundException e)
                    {
                        Log.e("Error", e.getStackTrace().toString());
                    }
                    holder.tableNo.setText(tableName+" - Multiple Billed");
                    holder.tableNo.setTextColor(context.getResources().getColor(R.color.white));
                }else {
                    try {
                        holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.tableOrdered));
                    }
                    catch (Resources.NotFoundException e)
                    {
                        Log.e("Error", e.getStackTrace().toString());
                    }
                    holder.tableNo.setText(tableName+" - Multiple Ordered");
                    holder.tableNo.setTextColor(context.getResources().getColor(R.color.white));
                }
            }else{
                if (table_food_status.equals("0") || table_bar_status.equals("0")){
                    holder.tableNo.setText(tableName+" - Ordered");
                    holder.tableNo.setTextColor(context.getResources().getColor(R.color.white));
                    try {
                        holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.tableOrdered));
                    }
                    catch (Resources.NotFoundException e)
                    {
                        Log.e("Error", e.getStackTrace().toString());
                    }
                }else if (table_food_status.equals("1") || table_bar_status.equals("1")){
                    holder.tableNo.setText(tableName+" - KOT");
                    holder.tableNo.setTextColor(context.getResources().getColor(R.color.white));
                    try {
                        holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.tableKOT));
                    }
                    catch (Resources.NotFoundException e)
                    {
                        Log.e("Error", e.getStackTrace().toString());
                    }
                }else if (table_food_status.equals("2") || table_bar_status.equals("2")) {
                    holder.tableNo.setText(tableName+" - Billed");
                    holder.tableNo.setTextColor(context.getResources().getColor(R.color.white));
                    try {
                        holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.tableBilled));
                    }
                    catch (Resources.NotFoundException e)
                    {
                        Log.e("Error", e.getStackTrace().toString());
                    }
                }else{
                    holder.tableNo.setText(tableListDetails.get(position).getCollector_id());
                    holder.tableNo.setTextColor(context.getResources().getColor(R.color.black));
                    try {
                        holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.tablenormal));
                    }
                    catch (Resources.NotFoundException e)
                    {
                        Log.e("Error", e.getStackTrace().toString());
                    }
                }
            }
        }

        if (tableListDetails.get(position).getCollector_id().equals("6") || tableListDetails.get(position).getCollector_id().equals("13")){
            holder.tableNo.setVisibility(View.GONE);
            holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.gray));
        }

        if (!tableListDetails.get(position).getCollector_id().equals("6") && !tableListDetails.get(position).getCollector_id().equals("13")){
            holder.mainDiv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(tableListDetails.get(position));
                    if (checkedPosition != position) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = position;
                    }

                    holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.gray));
                    holder.tableTitle.setTextColor(context.getResources().getColor(R.color.black));
                    holder.tableNo.setTextColor(context.getResources().getColor(R.color.black));

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tableListDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView tableNo, tableTitle;
        CardView cardMain;
        CoordinatorLayout mainDiv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tableNo = itemView.findViewById(R.id.table_no);
            tableTitle = itemView.findViewById(R.id.table_title);
            cardMain = itemView.findViewById(R.id.main_card);
            mainDiv = itemView.findViewById(R.id.main_div);
        }
    }
}

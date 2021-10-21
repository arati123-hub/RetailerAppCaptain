package com.appwelt.retailer.captain.adapter;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
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

public class ProductCategoryDetailsAdapter extends RecyclerView.Adapter<ProductCategoryDetailsAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ProductCategoryDetails item);
    }
    private Context context;
    private ArrayList<ProductCategoryDetails> responseData;
    private int checkedPosition = 0;
    private final OnItemClickListener listener;

    public ProductCategoryDetailsAdapter(Context context, ArrayList<ProductCategoryDetails> ProductCategoryDetails, OnItemClickListener listener) {
        this.context = context;
        this.responseData = ProductCategoryDetails;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_category_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FontStyle.FontStyle(context);

        String DATABASE_NAME = SharedPref.getString(context,"database_name");
        DatabaseHelper databaseHelper = new DatabaseHelper(context,DATABASE_NAME);
        holder.itemName.setTypeface(FontStyle.getFontRegular());
        holder.itemId.setTypeface(FontStyle.getFontRegular());

        if (checkedPosition == -1) {
            holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            if (checkedPosition == position) {
                holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.gray));
            } else {
                holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }
        String filename = databaseHelper.selectByID("tbl_category","category_id",responseData.get(position).getCategory_id(),"category_image");
        File imgFile = new File(Environment.getExternalStorageDirectory()+"/RetailerApp/images/"+filename);
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
        String srNo = databaseHelper.selectByID("tbl_category","category_id",responseData.get(position).getCategory_id(),"sequence_nr");
        String catType = databaseHelper.selectByID("tbl_category","category_id",responseData.get(position).getCategory_id(),"category_type");
        if (catType.equals("1")){
            holder.itemId.setText("FCAT0"+srNo);
        }else if (catType.equals("2")){
            holder.itemId.setText("BCAT0"+srNo);
        }else{
            holder.itemId.setText("CAT0"+srNo);
        }
//        holder.itemId.setText(responseData.get(position).getCategory_id());
        holder.itemName.setText(databaseHelper.selectByID("tbl_language_text","language_reference_id","CN_"+responseData.get(position).getCategory_id(),"language_text"));
        holder.mainDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(responseData.get(position));
                holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.gray));
                if (checkedPosition != position) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = position;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return responseData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        protected AppCompatTextView itemName,itemId;
        CardView cardMain;
        ImageView itemImage;
        LinearLayoutCompat mainDiv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.name);
            itemImage = itemView.findViewById(R.id.item_img);
            itemId = itemView.findViewById(R.id.item_id);
            mainDiv = itemView.findViewById(R.id.main_div);
        }
    }
}

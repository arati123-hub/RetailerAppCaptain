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
import com.appwelt.retailer.captain.model.CategoryDetails;
import com.appwelt.retailer.captain.utils.FontStyle;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(CategoryDetails item);
    }
    private Context context;
    private ArrayList<CategoryDetails> responseData;
    private int checkedPosition = 0;
    private final OnItemClickListener listener;

    public CategoryAdapter(Context context, ArrayList<CategoryDetails> categoryDetails, OnItemClickListener listener) {
        this.context = context;
        this.responseData = categoryDetails;
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

        holder.itemName.setTypeface(FontStyle.getFontRegular());
        holder.itemId.setTypeface(FontStyle.getFontRegular());
        holder.itemName1.setTypeface(FontStyle.getFontRegular());
        holder.itemId1.setTypeface(FontStyle.getFontRegular());

        if (checkedPosition == -1) {
            holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            if (checkedPosition == position) {
                holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.gray));
            } else {
                holder.mainDiv.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }
        //String filename = databaseHelper.selectByID("tbl_category","category_id",responseData.get(position).getCategory_id(),"category_image");
        String filename = responseData.get(position).getCategory_image();
        File imgFile = new File(Environment.getExternalStorageDirectory()+"/RetailerApp/images/"+filename);
        if (imgFile!=null){
            Picasso.with(context)
                    .load(imgFile)
                    .error(R.drawable.ic_photo)
                    .into(holder.itemImage);
            if(!imgFile.exists()){
                holder.mainDiv.setVisibility(View.GONE);
                holder.noImgDiv.setVisibility(View.VISIBLE);
            }else{
                holder.mainDiv.setVisibility(View.VISIBLE);
                holder.noImgDiv.setVisibility(View.GONE);
            }
        }

        String srNo = responseData.get(position).getSequence_nr();
        String catType = responseData.get(position).getCategory_type();

        holder.itemId.setText("CAT0"+srNo);
        holder.itemId1.setText("CAT0"+srNo);

        holder.itemName.setText(responseData.get(position).getCategory_name());
        holder.itemName1.setText(responseData.get(position).getCategory_name());

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
        holder.noImgDiv.setOnClickListener(new View.OnClickListener() {
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

        protected AppCompatTextView itemName,itemId,itemName1,itemId1;
        CardView cardMain;
        ImageView itemImage;
        LinearLayoutCompat mainDiv,noImgDiv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.name);
            itemName1 = itemView.findViewById(R.id.itemName);
            itemImage = itemView.findViewById(R.id.item_img);
            itemId = itemView.findViewById(R.id.item_id);
            itemId1 = itemView.findViewById(R.id.itemId);
            mainDiv = itemView.findViewById(R.id.main_div);
            noImgDiv = itemView.findViewById(R.id.no_img_div);
        }
    }
}

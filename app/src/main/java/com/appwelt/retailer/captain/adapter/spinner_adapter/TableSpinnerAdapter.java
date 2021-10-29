package com.appwelt.retailer.captain.adapter.spinner_adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appwelt.retailer.captain.model.TableListDetails;

import java.util.List;

public class TableSpinnerAdapter extends ArrayAdapter<TableListDetails> {
        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private List<TableListDetails> values;

        public TableSpinnerAdapter(Context context, int textViewResourceId, List<TableListDetails> values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Nullable
        @Override
        public TableListDetails getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }


        // And the "magic" goes here
        // This is for the "passive" state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
            TextView label = (TextView) super.getView(position, convertView, parent);
            label.setTextColor(Color.BLACK);
            // Then you can get the current item using the values array (Users array) and the current position
            // You can NOW reference each method you has created in your bean object (User class)

            String tableName = values.get(position).getCollector_name();
            if (values.get(position).getCollector_status().equals("1")){
                tableName = tableName + "-" + values.get(position).getCollector_split_series_no();
            }
            label.setText("Table "+tableName);

            // And finally return your dynamic (or custom) view for each spinner item
            return label;
        }

        // And here is when the "chooser" is popped up
        // Normally is the same view, but you can customize it if you want
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = (TextView) super.getDropDownView(position, convertView, parent);
            label.setTextColor(Color.BLACK);
            String tableName = values.get(position).getCollector_name();
            if (values.get(position).getCollector_status().equals("1")){
                tableName = tableName + "-" + values.get(position).getCollector_split_series_no();
            }
            label.setText("Table "+tableName);

            return label;
        }



}

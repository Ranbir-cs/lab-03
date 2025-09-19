package com.example.listycitylab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City> {
    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Renamed 'view' to 'listItemView' to avoid conflict with 'android.view.View'
        View listItemView;
        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.content, parent, false);
        } else {
            listItemView = convertView;
        }
        City city = getItem(position);
        TextView cityName = listItemView.findViewById(R.id.city_text);
        TextView provinceName = listItemView.findViewById(R.id.province_text);

        if (city != null) { // Good practice to check if city is null
            cityName.setText(city.getName());
            provinceName.setText(city.getProvince());
        }
        return listItemView;
    }
}

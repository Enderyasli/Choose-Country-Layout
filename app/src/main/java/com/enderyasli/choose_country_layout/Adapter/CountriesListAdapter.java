package com.enderyasli.choose_country_layout.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enderyasli.choose_country_layout.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CountriesListAdapter extends ArrayAdapter<String> implements Filterable {
    private final Context context;
    private ArrayList<String> values;
    private List<String> originalData = null;
    private List<String> filteredData = null;
    private ItemFilter mFilter = new ItemFilter();


    public CountriesListAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.country_list_item, values);
        this.filteredData = values;
        this.originalData = values;
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return filteredData.size();
    }

    public String getItem(int position) {
        return filteredData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.country_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.country_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.country_image);

        // String[] g = values.get(position).split(",");
        textView.setText(filteredData.get(position).trim());


        String pngName = filteredData.get(position).trim();
        String imagename = pngName.toLowerCase();
        String image_name = imagename.replace(" ", "_");
        // Log.d("gelenUlke", imagename);

        // kötü telefonlarda ram hatası oluşturuyor
        //  imageView.setImageResource(context.getResources().getIdentifier("drawable/" + image_name, null, context.getPackageName()));


        Glide.with(context).load(context.getResources().getIdentifier("drawable/" + image_name, null, context.getPackageName())).into(imageView);

        return rowView;
    }

    private String calculateCountryCode(String countryName) {

        Map<String, String> countries = new HashMap<>();
        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            countries.put(l.getDisplayCountry(), iso);
        }

        return countries.get(countryName);
    }

    private String GetCountryZipCode(String ssid) {
        Locale loc = new Locale("", ssid);

        return loc.getDisplayCountry().trim();
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<String> list = originalData;

            int count = list.size();
            final ArrayList<String> nlist = new ArrayList<String>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredData = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }

    public Filter getFilter() {
        return mFilter;
    }
}


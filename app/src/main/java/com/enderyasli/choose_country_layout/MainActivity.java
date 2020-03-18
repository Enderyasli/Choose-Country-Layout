package com.enderyasli.choose_country_layout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enderyasli.choose_country_layout.Adapter.CountriesListAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    Context mContext;
    TextView choose_location;
    RelativeLayout choose_location_list_layout;
    Button close_countries;
    ListView listview;
    CountriesListAdapter adapter;
    EditText search_edittext;
    int selectCountry = 0;
    String countryName = "United Kingdom of America";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;

        findvbyıd();
        initListview();



        choose_location.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                choose_location_list_layout.setVisibility(View.VISIBLE);
                choose_location.setVisibility(View.INVISIBLE);

            }
        });

        close_countries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose_location_list_layout.setVisibility(View.INVISIBLE);
                choose_location.setVisibility(View.VISIBLE);

                search_edittext.setText("");


            }
        });

        search_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView txtTechCharacteristic = (TextView) view.findViewById(R.id.country_name);
                String txt = (String) txtTechCharacteristic.getText();


                choose_location.setText(txt);

                choose_location_list_layout.setVisibility(View.INVISIBLE);
                search_edittext.setText("");

                // FOR HİDE KEYBOARD
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                // FOR COUNTRY CONTROL
                selectCountry = 1;
                countryName = txt;
                choose_location.setVisibility(View.VISIBLE);



            }
        });
    }

    private void findvbyıd() {

        listview = (ListView) findViewById(R.id.listView);
        close_countries = findViewById(R.id.close_countries);
        choose_location_list_layout = findViewById(R.id.choose_location_list_layout);
        search_edittext = findViewById(R.id.input_search);
        choose_location = findViewById(R.id.choose_location);
    }
    private void initListview() {

        String[] recourseList = this.getResources().getStringArray(R.array.countries_array);

        ArrayList<String> values = new ArrayList<String>();
        values.addAll(Arrays.asList(recourseList));

        Log.d("gelenArray", values.get(0));

        adapter = new CountriesListAdapter(this, values);
        listview.setAdapter(adapter);


    }

}

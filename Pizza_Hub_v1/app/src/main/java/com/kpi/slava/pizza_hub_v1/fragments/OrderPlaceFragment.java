package com.kpi.slava.pizza_hub_v1.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kpi.slava.pizza_hub_v1.JSON.JSONParser;
import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.URLConstants;
import com.kpi.slava.pizza_hub_v1.entity.Item;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderPlaceFragment extends DialogFragment {

    private static final String TAG_SUCCESS = "success";

    public static final String TAG = "OrderPlaceFragment";

    private final int LAYOUT = R.layout.fragment_order_place;

    private View view;

    private ArrayList<Item> itemListOrder = new ArrayList<Item>();

    private TextInputLayout tilAddress;

    private EditText edtAddress;

    private TextView tvName, tvNumber, tvTotalPrice;

    Button btnBack, btnCreateOrder;

    private String address, date;

    ProgressDialog progressDialog;

    JSONParser jsonParser = new JSONParser();

    SharedPreferences sharedPreferences;
    final String SAVED_NAME = "Name Saved";
    final String SAVED_NUMBER = "Number Saved";
    final String SAVED_ID = "Id Saved";

    boolean flagCreateOrder = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        view = inflater.inflate(LAYOUT, null);

        sharedPreferences = getActivity().getSharedPreferences("client", 0);

        Bundle bundle = getArguments();
        if (bundle != null) {
            itemListOrder = bundle.getParcelableArrayList("itemListOrder");
        }

        tilAddress = (TextInputLayout) view.findViewById(R.id.til_order_account_address);
        edtAddress = (EditText) tilAddress.findViewById(R.id.edt_order_account_address);
        tilAddress.setHint("Enter address");

        tvName = (TextView) view.findViewById(R.id.tv_order_account_name);
        tvNumber = (TextView) view.findViewById(R.id.tv_order_account_number);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_order_account_total_price);

        tvName.setText(sharedPreferences.getString(SAVED_NAME, ""));
        tvNumber.setText("(" + sharedPreferences.getString(SAVED_NUMBER, "") + ")");

        tvTotalPrice.setText("Total price : " + getTotalPrice());

        btnBack = (Button) view.findViewById(R.id.btn_order_back);
        btnCreateOrder = (Button) view.findViewById(R.id.btn_order_create);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnCreateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtAddress.getText().toString().equals("")) {

                    address = edtAddress.getText().toString();

                    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy ");
                    Date curDate= new Date();
                    date = dateFormat.format(curDate);

                    new OrderCreate().execute();

                }
                else
                    Toast.makeText(getContext(), "Address is empty", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private class OrderCreate extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Creating...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> newOrderParams = new ArrayList<NameValuePair>();
            newOrderParams.add(new BasicNameValuePair("address", address));
            newOrderParams.add(new BasicNameValuePair("total_price", getTotalPrice()));
            newOrderParams.add(new BasicNameValuePair("date", date));
            newOrderParams.add(new BasicNameValuePair("id_client", getIdClient()));

            JSONObject json = jsonParser.makeHttpRequest(URLConstants.URL_INSERT_ORDER, "POST", newOrderParams);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // insert data into orders_items
                    for(int i=0; i<itemListOrder.size(); i++){

                        List<NameValuePair> newOrderItemParams= new ArrayList<NameValuePair>();
                        newOrderItemParams.add(new BasicNameValuePair("id_item", itemListOrder.get(i).getIdItem()));
                        newOrderItemParams.add(new BasicNameValuePair("quantity", String.valueOf(itemListOrder.get(i).getQuantity())));

                        JSONObject jsonObject = jsonParser.makeHttpRequest(URLConstants.URL_INSERT_ORDER_ITEM, "POST", newOrderItemParams);

                        if(jsonObject.getInt(TAG_SUCCESS) != 1){
                            flagCreateOrder = false;
                        }
                    }

                    //get current count from table - client
                    List<NameValuePair> clientParams= new ArrayList<NameValuePair>();
                    clientParams.add(new BasicNameValuePair("id_client", getIdClient()));

                    JSONObject jsonClientCount = jsonParser.makeHttpRequest(URLConstants.URL_SELECT_CLIENT_WHERE_ID, "POST", clientParams);

                    if(jsonClientCount.getInt(TAG_SUCCESS) == 1){

                        int currentCount = Integer.parseInt(getTotalPrice());

                        currentCount += Integer.parseInt(jsonClientCount.getString("count"));

                        List<NameValuePair> clientUpdateParams= new ArrayList<NameValuePair>();
                        clientUpdateParams.add(new BasicNameValuePair("id_client", getIdClient()));
                        clientUpdateParams.add(new BasicNameValuePair("count", String.valueOf(currentCount)));

                        JSONObject jsonClientUpdateCount = jsonParser.makeHttpRequest(URLConstants.URL_UPDATE_CLIENT, "POST", clientUpdateParams);

                        if(jsonClientCount.getInt(TAG_SUCCESS) != 1) flagCreateOrder = false;

                    }

                } else flagCreateOrder=false;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (flagCreateOrder) {

                itemListOrder.clear();

                Intent intentResult = new Intent();
                intentResult.putExtra("itemList", itemListOrder);
                getActivity().setResult(Activity.RESULT_OK, intentResult);
                getActivity().finish();

                Toast.makeText(getContext(), "Order created", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }


    private String getTotalPrice(){
        int totalPrice=0;

        for(int i=0; i<itemListOrder.size(); i++){
            totalPrice+= Integer.parseInt(itemListOrder.get(i).getPrice())*itemListOrder.get(i).getQuantity();
        }

        return String.valueOf(totalPrice);
    }

    private String getIdClient(){
        String idClient = sharedPreferences.getString(SAVED_ID, "");
        return idClient;
    }
}

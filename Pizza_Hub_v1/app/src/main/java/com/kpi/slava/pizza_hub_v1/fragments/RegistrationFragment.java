package com.kpi.slava.pizza_hub_v1.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.kpi.slava.pizza_hub_v1.JSON.JSONParser;
import com.kpi.slava.pizza_hub_v1.R;
import com.kpi.slava.pizza_hub_v1.URLConstants;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrationFragment extends DialogFragment {

    private static final String TAG_SUCCESS = "success";

    JSONParser jsonParser;

    private ProgressDialog pDialog;

    public final static String TAG = "Account";

    private RadioButton radioLogin, radioRegistration;

    private TextInputLayout tilName, tilNumber;

    private EditText edtName, edtNumber;

    private Button btnAccept;

    SharedPreferences sharedPreferences;
    final String SAVED_NAME = "Name Saved";
    final String SAVED_NUMBER = "Number Saved";

    private String name;
    private String number;

    boolean flag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(TAG);
        setCancelable(false);
        View v = inflater.inflate(R.layout.fragment_registration, null);

        flag = false;

        jsonParser = new JSONParser();

        radioLogin = (RadioButton) v.findViewById(R.id.radio_log_in);
        radioLogin.setChecked(true);
        radioRegistration = (RadioButton) v.findViewById(R.id.radio_registration);

        tilName = (TextInputLayout) v.findViewById(R.id.til_registration_name);
        edtName = (EditText) tilName.findViewById(R.id.edt_registration_name);
        tilName.setHint("Enter your name");

        tilNumber = (TextInputLayout) v.findViewById(R.id.til_registration_number);
        edtNumber = (EditText) tilNumber.findViewById(R.id.edt_registration_number);
        tilNumber.setHint("Enter your number");

        btnAccept = (Button) v.findViewById(R.id.btn_registration_accept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkFields()) {
                    name = edtName.getText().toString();
                    number = edtNumber.getText().toString();

                    if (radioLogin.isChecked()) {
                        new logIn().execute();
                    } else {
                        new Registration().execute();
                    }
                }
            }
        });
        return v;
    }

    // log in
    class logIn extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Searching...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String[] args) {
            //fill parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("number", number));

            JSONObject json = jsonParser.makeHttpRequest(URLConstants.URL_CLIENT_LOG_IN, "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) flag = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (flag) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                saveDataSP();
                dismiss();
            } else Toast.makeText(getContext(), "No client found", Toast.LENGTH_SHORT).show();
        }
    }

    //registration
    class Registration extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Creating...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String[] args) {
            //fill parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("number", number));

            JSONObject json = jsonParser.makeHttpRequest(URLConstants.URL_CLIENT_REGISTRATION, "POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) flag = true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (flag) {
                Toast.makeText(getContext(), "Account created", Toast.LENGTH_SHORT).show();
                saveDataSP();
                dismiss();
            } else
                Toast.makeText(getContext(), "This number is used", Toast.LENGTH_SHORT).show();
        }
    }

    //save account data in sharedPreferences
    void saveDataSP() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.putString(SAVED_NAME, name);
        spEditor.putString(SAVED_NUMBER, number);
        spEditor.commit();
    }

    private boolean checkFields() {
        if ((edtName.getText().toString().equals("")) || (edtNumber.getText().toString().equals(""))) {
            Toast.makeText(getContext(), "Wrong input", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }
}

package com.kpi.slava.pizza_hub_v1.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.kpi.slava.pizza_hub_v1.R;

public class RegistrationFragment extends DialogFragment {

    public final static String TAG = "Log in";

    private RadioButton radioLogin, radioRegistration;

    private TextInputLayout tilName, tilNumber;

    private EditText edtName, edtNumber;

    private Button btnAccept;

    SharedPreferences sharedPreferences;
    final String SAVED_NAME = "Name Saved";
    final String SAVED_NUMBER = "Number Saved";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setTitle(TAG);
        setCancelable(false);
        View v = inflater.inflate(R.layout.log_in, null);

        radioLogin = (RadioButton) v.findViewById(R.id.radio_log_in);
        radioRegistration = (RadioButton) v.findViewById(R.id.radio_registration);

        tilName = (TextInputLayout) v.findViewById(R.id.til_name);
        edtName = (EditText) tilName.findViewById(R.id.edt_name);
        tilName.setHint("Enter your name");

        tilNumber = (TextInputLayout) v.findViewById(R.id.til_number);
        edtNumber = (EditText) tilNumber.findViewById(R.id.edt_number);
        tilNumber.setHint("Enter your number");

        btnAccept = (Button) v.findViewById(R.id.btn_registration_accept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFields()){

                    sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = sharedPreferences.edit();
                    spEditor.putString(SAVED_NAME, edtName.getText().toString());
                    spEditor.putString(SAVED_NUMBER, edtNumber.getText().toString());
                    spEditor.commit();

                    dismiss();
                }
            }
        });
        return v;
    }

    private boolean checkFields() {
        if((edtName.getText().toString().equals("")) || (edtNumber.getText().toString().equals("")) ||
                (!radioLogin.isChecked() && !radioRegistration.isChecked())){
            Toast.makeText(getContext(), "Wrong input", Toast.LENGTH_SHORT).show();
            return false;
        }
        else    return true;
    }
}

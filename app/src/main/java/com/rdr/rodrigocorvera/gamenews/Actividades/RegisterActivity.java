package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdr.rodrigocorvera.gamenews.R;

public class RegisterActivity extends AppCompatActivity {

    Button buttonCreateAccount;
    EditText textFieldName;
    EditText textFieldPassword;
    EditText textFieldPasswordCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getViews();
        setConfiguration();
    }

    public void getViews () {
        buttonCreateAccount = findViewById(R.id.button_sign_in);
        textFieldName = findViewById(R.id.textfield_name);
        textFieldPassword = findViewById(R.id.textfield_password);
        textFieldPasswordCheck = findViewById(R.id.textfield_password_check);
    }

    public void setConfiguration () {
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !textFieldName.getText().equals("") && !textFieldPassword.getText().equals("")  && !textFieldPasswordCheck.getText().equals("")) {

                    if ( textFieldPassword.getText().equals(textFieldPasswordCheck.getText()) ) {

                    } else {
                        Toast.makeText(RegisterActivity.this, R.string.password_unmatch, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

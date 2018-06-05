package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.Usuario;
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

                        String user = textFieldName.getText().toString();
                        String url = "";
                        String password = textFieldPassword.getText().toString();

                        Call<Usuario> usuarioResponse = ApiAdapter.getApiHandler().insert_user(user, url, password, "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1YjBmNDE1NjdkMjZmZDAwMjBmNjMyN2IiLCJpYXQiOjE1Mjc4OTk3MDIsImV4cCI6MTUyOTEwOTMwMn0.R7ieieRpNkRUT-YhwQccDecuohilo12hN0i2AaafS2Q");

                        usuarioResponse.enqueue(new Callback<Usuario>() {
                            @Override
                            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                if (response.isSuccessful() ) {
                                    Usuario usuario1 = response.body();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Usuario> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                            }
                        });
                } else {
                    Toast.makeText(RegisterActivity.this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

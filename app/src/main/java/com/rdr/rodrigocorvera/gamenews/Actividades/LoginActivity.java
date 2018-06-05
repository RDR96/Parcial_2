package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.Usuario;
import com.rdr.rodrigocorvera.gamenews.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView textButtonToRegister;
    Button buttonSignIn;
    EditText textFieldName;
    EditText textFieldPassword;
    public static String tokenAccess = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViews();
        setConfiguration();

    }

    public void getViews () {
        textButtonToRegister = findViewById(R.id.text_to_register);
        buttonSignIn = findViewById(R.id.button_sign_in);
        textFieldName = findViewById(R.id.textfield_name);
        textFieldPassword = findViewById(R.id.textfield_password);
    }

    public void setConfiguration () {
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !textFieldName.getText().toString().equals("") && !textFieldPassword.getText().toString().equals("") ) {
                    checkLogIn(textFieldName.getText().toString(), textFieldPassword.getText().toString());
                } else {
                    Toast.makeText(LoginActivity.this, R.string.fill_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });

        textButtonToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void checkLogIn (String user, String password) {

        Call<Usuario> logInResponse = ApiAdapter.getApiHandler().get_token(user, password);

        logInResponse.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if ( response.isSuccessful() ) {
                    try{

                        String json = response.body().toString();
                        JSONObject data = null;
                        data = new JSONObject(json);
                        tokenAccess = data.getString("token");
                        Log.d("token", tokenAccess);
                        if ( !tokenAccess.equals("null")) {

                            Toast.makeText(LoginActivity.this, R.string.successful, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(LoginActivity.this, R.string.wrong_data_type, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.d("Error: ", e.getMessage());
                    }
                } else {
                    Toast.makeText(LoginActivity.this, R.string.wrong_data_type, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(LoginActivity.this, R.string.log_in_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

}

package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.AppDatabase;
import com.rdr.rodrigocorvera.gamenews.BaseDeDatos.BaseDeDatos.Entidades.User;
import com.rdr.rodrigocorvera.gamenews.Clases.CurrentUser;
import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.Token;
import com.rdr.rodrigocorvera.gamenews.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button buttonSignIn;
    EditText textFieldName;
    EditText textFieldPassword;
    ProgressBar progressBar;
    public static String tokenAccess = null;
    public static String currentUser = null;
    public static AppDatabase appDatabase;
    Thread thread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDataBase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getViews();
        setConfiguration();

    }

    public void getDataBase () {
        appDatabase = AppDatabase.getDatabaseInstance(getApplicationContext());

        final User[] user = new User[1];
        thread = new Thread(){
            public void run() {
                user[0] = appDatabase.userDao().checkIfLog();
                if (user[0] != null) {
                    tokenAccess = user[0].getToken();
                    currentUser = user[0].getId();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();


    }

    public void getViews () {
        buttonSignIn = findViewById(R.id.button_sign_in);
        textFieldName = findViewById(R.id.textfield_name);
        textFieldPassword = findViewById(R.id.textfield_password);
        progressBar = findViewById(R.id.progress_bar);
    }

    public void setConfiguration () {
        appDatabase = AppDatabase.getDatabaseInstance(getApplicationContext());
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



    }

    public void checkLogIn (String user, String password) {

        Call<Token> logInResponse = ApiAdapter.getApiHandler().get_token(user, password);
        prepareForLogin();
        logInResponse.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {

                if ( response.isSuccessful() ) {
                    //try{
                        Token token = response.body();
                        tokenAccess = token.getToken();
                        Log.d("token", tokenAccess);
                        if ( !tokenAccess.equals("null")) {
                            getCurrentUser(tokenAccess);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, R.string.successful, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(LoginActivity.this, R.string.wrong_data_type, Toast.LENGTH_SHORT).show();
                        }
                  //  } catch (JSONException e) {
                        //Log.d("Error: ", e.getMessage());
                    //}
                } else {
                    Toast.makeText(LoginActivity.this, R.string.wrong_data_type, Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, R.string.log_in_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getCurrentUser (final String token) {
        final Call<CurrentUser> logInResponse = ApiAdapter.getApiHandler().getCurrentUser("Bearer "+ tokenAccess);
        logInResponse.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call ,final Response<CurrentUser> response) {
                if ( response.isSuccessful() ) {

                    thread = new Thread(){
                        public void run() {
                            User user  = appDatabase.userDao().getCurrentUser(response.body().get_id());
                            if (user != null) {
                                user.setLog(1);
                                user.setToken(token);
                                appDatabase.userDao().updateUser(user);
                            } else {
                                user = new User(response.body().get_id(),
                                        response.body().getUser(),
                                        response.body().getPassword(),
                                        token,1);
                                appDatabase.userDao().addUser(user);
                            }
                            user = appDatabase.userDao().checkIfLog();
                            String value = user.getId();
                            Log.d("Usuario", appDatabase.userDao().getCurrentUser(value).getName());
                            int i;

                        }
                    };
                    thread.start();

                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {

            }
        });
    }

    public void prepareForLogin() {
        View view  = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

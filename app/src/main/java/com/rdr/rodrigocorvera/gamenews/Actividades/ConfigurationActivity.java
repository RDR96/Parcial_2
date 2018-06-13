package com.rdr.rodrigocorvera.gamenews.Actividades;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rdr.rodrigocorvera.gamenews.Clases.ApiAdapter;
import com.rdr.rodrigocorvera.gamenews.Clases.CurrentUser;
import com.rdr.rodrigocorvera.gamenews.Clases.UserInfo;
import com.rdr.rodrigocorvera.gamenews.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity.currentUser;
import static com.rdr.rodrigocorvera.gamenews.Actividades.LoginActivity.tokenAccess;

public class ConfigurationActivity extends AppCompatActivity {

    LinearLayout accountOptionButton;
    LinearLayout layoutOptionButton;
    TextView currentUserName;
    EditText newNameField;
    EditText newPasswordField;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        getViews();
        setConfiguration();
    }

    public void getViews () {
        accountOptionButton = findViewById(R.id.account_option_container);
        layoutOptionButton = findViewById(R.id.layout_option_container);
    }

    public void setConfiguration() {
        counter = 0;
        accountOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSettingsModal();
            }
        });

        layoutOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void userSettingsModal() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ConfigurationActivity.this);
        View dialogElements = getLayoutInflater().inflate(R.layout.dialog_user_settings, null);

        newNameField = dialogElements.findViewById(R.id.new_name_field);
        newPasswordField = dialogElements.findViewById(R.id.new_password_field);
        currentUserName  = dialogElements.findViewById(R.id.current_user_name);
        Button updateButton = dialogElements.findViewById(R.id.update_button);
        Button cancelButton = dialogElements.findViewById(R.id.cancel_button);
        final AlertDialog alert;
        mBuilder.setView(dialogElements);
        alert = mBuilder.create();
        alert.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Nombre", newNameField.getText().toString());
                Log.d("Contraseña", newPasswordField.getText().toString());
                String newName = newNameField.getText().toString();
                String newPassword = newPasswordField.getText().toString();
                if ( !newName.equals("") || !newPassword.equals("") ) {

                    counter++;
                    if (counter == 2) {
                        if ( !newName.equals("") && newPassword.equals("")) {
                            changeUserConfig(0, newName, newPassword);
                        } else if (newName.equals("") && !newPassword.equals("")) {
                            changeUserConfig(2, newName, newPassword);
                        } else if (!newName.equals("") && !newPassword.equals("")) {
                            changeUserConfig(3, newName, newPassword);
                        }
                        counter = 0;
                    } else {
                        Toast.makeText(ConfigurationActivity.this, R.string.tap_again, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ConfigurationActivity.this, R.string.fill_some_field, Toast.LENGTH_SHORT).show();
                }


            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = 0;
                alert.hide();
                alert.dismiss();
            }
        });
    }

    public void changeUserConfig (int option, String name, String password) {
        Call<UserInfo> updateResponse;
        if (option == 0) {
            updateResponse = ApiAdapter.getApiHandler().updateName(
                    currentUser.get_id(),
                    name,
                    "Bearer "+ tokenAccess);
        } else if (option == 1) {
            updateResponse = ApiAdapter.getApiHandler().updatePassword(
                    currentUser.get_id(),
                    password,
                    "Bearer "+ tokenAccess);

        } else {
            updateResponse = ApiAdapter.getApiHandler().updateAllInfo(
                    currentUser.get_id(),
                    name,
                    password,
                    "Bearer "+ tokenAccess);
        }

        updateResponse.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    MainActivity.userInfo = response.body();
                    getCurrentUser();
                    Toast.makeText(ConfigurationActivity.this, R.string.change_successful, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

    }

    public void getCurrentUser () {
        final Call<CurrentUser> logInResponse = ApiAdapter.getApiHandler().getCurrentUser("Bearer "+ tokenAccess);
        logInResponse.enqueue(new Callback<CurrentUser>() {
            @Override
            public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                if ( response.isSuccessful() ) {
                    currentUser = response.body();
                    currentUserName.setText(currentUser.getUser());
                    newNameField.setText("");
                    newPasswordField.setText("");
                    Toast.makeText(ConfigurationActivity.this, R.string.change_successful, Toast.LENGTH_SHORT).show();
                    int i;
                }
            }

            @Override
            public void onFailure(Call<CurrentUser> call, Throwable t) {

            }
        });
    }
}

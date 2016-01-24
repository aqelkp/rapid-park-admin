package in.aqel.rapidpark_adminstrator.Activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import in.aqel.rapidpark_adminstrator.R;
import in.aqel.rapidpark_adminstrator.Utils.AppConstants;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button bLogin;
    private static String LOG_TAG = "LoginActivity";
    Firebase ref;
    Firebase.AuthResultHandler authResultHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);

        ref = new Firebase(AppConstants.SERVER);



        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Clicked Here");

                ref.createUser("aqel123@gmail.com", "password", new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        Log.d(LOG_TAG, "User created");
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Log.d(LOG_TAG, firebaseError.getMessage());
                    }
                });
                // Create a handler to handle the result of the authentication
                authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authenticated successfully with payload authData
                        Log.d(LOG_TAG, "Authnticated");
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Authenticated failed with error firebaseError
                        Log.d(LOG_TAG, firebaseError.getMessage());
                        Snackbar
                                .make(etEmail, firebaseError.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                    }
                };

                String email = etEmail.getText().toString();
                String password  = etPassword.getText().toString();
                ref.authWithPassword( email, password, authResultHandler);

            }
        });


    }

}


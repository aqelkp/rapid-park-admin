package in.aqel.rapidpark_adminstrator.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.Firebase.ResultHandler;
import com.firebase.client.FirebaseError;

import in.aqel.quickparksdk.Objects.User;
import in.aqel.quickparksdk.Utils.PrefUtils;
import in.aqel.rapidpark_adminstrator.R;
import in.aqel.rapidpark_adminstrator.Utils.AppConstants;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail, etPassword;
    Button bLogin, bNew;
    private static String LOG_TAG = "LoginActivity";
    Context context = LoginActivity.this;
    Firebase ref;
    Firebase.AuthResultHandler authResultHandler;
    TextView tvForgot;
    ProgressBar spinner;
    boolean newUser = false;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Firebase.setAndroidContext(this);

        ref = new Firebase(AppConstants.SERVER);



        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bLogin = (Button) findViewById(R.id.bLogin);
        bNew = (Button) findViewById(R.id.bNew);
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        spinner = (ProgressBar) findViewById(R.id.spinner);

        bLogin.setOnClickListener(this);
        bNew.setOnClickListener(this);
        tvForgot.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        email = etEmail.getText().toString();
        password  = etPassword.getText().toString();


        switch (v.getId()){
            case R.id.bLogin:

                rotateSpineer();
                // Create a handler to handle the result of the authentication
                authResultHandler = new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        // Authenticated successfully with payload authData
                        Log.d(LOG_TAG, "Authenticated");

                        stopSpinner();
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        // Authenticated failed with error firebaseError
                        Log.d(LOG_TAG, firebaseError.getMessage());
                        Snackbar
                                .make(etEmail, firebaseError.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                        stopSpinner();
                    }
                };

                ref.authWithPassword( email, password, authResultHandler);


                break;

            case R.id.tvForgot:

                rotateSpineer();
                ref.resetPassword(etEmail.getText().toString(), new ResultHandler() {
                    @Override
                    public void onSuccess() {

                        Snackbar
                                .make(tvForgot, "You will recieve an email shortly with a new password", Snackbar.LENGTH_SHORT)
                                .show();
                        stopSpinner();

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Snackbar
                                .make(tvForgot, firebaseError.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                        stopSpinner();

                    }
                });
                break;

            case R.id.bNew:

                rotateSpineer();
                ref.createUser(email, password, new ResultHandler() {
                    @Override
                    public void onSuccess() {
                        Log.d(LOG_TAG, "New user " + email + "created");
                        // Create a handler to handle the result of the authentication
                        authResultHandler = new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                // Authenticated successfully with payload authData
                                Log.d(LOG_TAG, "Authenticated");

                                PrefUtils.setEmail(context, email);

                                User user = new User();
                                user.setEmail(email);
                                user.setRole("vendor");
                                user.setId(authData.getUid());
                                ref.child("users").push().setValue(user, new Firebase.CompletionListener() {
                                    @Override
                                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                                        stopSpinner();
                                        Intent intent = new Intent(context, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                // Authenticated failed with error firebaseError
                                Log.d(LOG_TAG, firebaseError.getMessage());
                                Snackbar
                                        .make(etEmail, firebaseError.getMessage(), Snackbar.LENGTH_SHORT)
                                        .show();
                                stopSpinner();
                            }
                        };

                        ref.authWithPassword( email, password, authResultHandler);

                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Log.d(LOG_TAG, firebaseError.getMessage());
                        Snackbar
                                .make(tvForgot, firebaseError.getMessage(), Snackbar.LENGTH_SHORT)
                                .show();
                        stopSpinner();
                    }
                });
                break;
        }

    }

    private void stopSpinner() {
        spinner.setVisibility(View.GONE);
    }

    private void rotateSpineer() {
        spinner.setVisibility(View.VISIBLE);
    }
}


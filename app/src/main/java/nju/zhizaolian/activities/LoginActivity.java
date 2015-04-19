package nju.zhizaolian.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import nju.zhizaolian.R;


public class LoginActivity extends Activity  {



    // UI references.
    private EditText mUserNameView;
    private EditText mPasswordView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserNameView = (EditText) findViewById(R.id.user_name);


        mPasswordView = (EditText) findViewById(R.id.password);


        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


    }





    public void attemptLogin() {


        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();


        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
            if (TextUtils.isEmpty(username)) {
                mUserNameView.setError(getString(R.string.error_field_required));
                focusView = mUserNameView;
                cancel = true;
            } else if (!isUsernameValid(username)) {
                mUserNameView.setError(getString(R.string.error_invalid_username));
                focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            AsyncHttpClient client=new AsyncHttpClient();
            RequestParams params=new RequestParams();
            params.put("username",username);
            params.put("passwordFake",password);
            client.post("http://192.168.0.13:8888/pms/ ", params,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d("success",responseString);
                }
            });
        }
    }

    private boolean isUsernameValid(String username) {

        return username.length()>0;
    }

    private boolean isPasswordValid(String password) {

        return password.length() > 0;
    }

    private void login(){
        Intent i=new Intent(this,MainActivity.class);

        startActivity(i);
    }



}




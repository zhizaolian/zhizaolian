package nju.zhizaolian.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;


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
        if (TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView=mPasswordView;
            cancel=true;
        }else if (!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView=mPasswordView;
            cancel=true;
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
            params.put("user_name",username);
            params.put("user_password",password);
            client.post(IPAddress.getIP()+"/fmc/mobile_doLogin.do", params,new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("account",response.toString());
                    Log.d("length",String.valueOf(response.length()));
                    try {
                        if(response.getString("isSuccess") == "true"){
                            Account account= null;
                            try {
                                account = Account.fromJson(response.getJSONObject("account"));
                                String jSessionID=response.getString("jsessionId");
                                SharedPreferences common=getSharedPreferences("common",0);
                                SharedPreferences.Editor editor=common.edit();
                                editor.putString("jsessionId",jSessionID);
                                editor.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            login(account);
                        }else{
                            mPasswordView.setError(getString(R.string.error_incorrect_password));
                            mPasswordView.setError(null);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("fail",responseString);
                    mPasswordView.setError(getString(R.string.error_incorrect_password));

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

    private void login(Account account){
        Intent i=new Intent(this,MainActivity.class);

        i.putExtra("account",account);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0&&resultCode == RESULT_OK){
            finish();
        }
    }
}




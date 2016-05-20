package com.studios.betta.whozaround;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.studios.betta.whozaround.fragments.LoginFragment;
import com.studios.betta.whozaround.network.WhozAroundEndpointInterface;
import com.studios.betta.whozaround.network.services.GCMPreferences;
import com.studios.betta.whozaround.network.services.RegistrationIntentService;
import com.studios.betta.whozaround.objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends Activity {
    private static final String LOG_TAG = LoginActivity.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    Context context_;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_login);

        context_ = this;
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new LoginFragment())
                    .commit();
        }
        // Registering BroadcastReceiver
      //  registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Log.d(LOG_TAG, "STARTING REG SERVICE");
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        configureFacebookLoginCallbacks();
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            Log.v(LOG_TAG, "Logged, user name=" + profile.getFirstName() + " " + profile.getLastName());
            Intent intent = new Intent(context_, MyTripsActivity.class);
            startActivity(intent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void configureFacebookLoginCallbacks() {
        FacebookSdk.sdkInitialize(context_);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //Login success
                        Log.d(LOG_TAG, "Login with FB was a success");

                            sendUserToServer();

                            Intent intent = new Intent(context_, MyTripsActivity.class);

                            startActivity(intent);
                        }

                        @Override
                    public void onCancel() {
                        Log.d(LOG_TAG, "Login with FB was canceled");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(LOG_TAG, error.getMessage());
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver();
    }

    @Override
    protected void onPause() {
      //  LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
       // isReceiverRegistered = false;
        super.onPause();
    }

    private void sendUserToServer() {
        Log.d(LOG_TAG, "SENDING USER TO SERVER");
        //Send user to Server
        String BASE_URL = "http://52.38.181.114/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final WhozAroundEndpointInterface apiService = retrofit.create(WhozAroundEndpointInterface.class);


        //Get GCM Token

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String gcm_token = sharedPreferences.getString(GCMPreferences.GCM_TOKEN, "");

        final Profile profile = Profile.getCurrentProfile();

        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        JSONObject data = response.getJSONObject();
                        try {
                            JSONArray friends = data.getJSONArray("data");
                            User user = new User(profile.getId(),
                                    profile.getFirstName(),
                                    profile.getLastName(),
                                    "",
                                    "",
                                    "",
                                    "0",
                                    gcm_token);
                            for (int i = 0; i < friends.length(); ++i) {
                                String id = friends.getJSONObject(i).getString("id");
                                user.addFriend(id);

                            }
                            //Log.d("Friendlist", friends.toString());
                            //Prepare connection


                            Call<User> call = apiService.createUser(user);
                            call.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Log.d(LOG_TAG, "RESPONSE");

                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Log.d(LOG_TAG, "FAILURE");
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();


    }
    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(GCMPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(LOG_TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }



}

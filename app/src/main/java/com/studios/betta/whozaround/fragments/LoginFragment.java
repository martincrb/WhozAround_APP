package com.studios.betta.whozaround.fragments;

/**
 * Created by Martin on 09/04/2016.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.studios.betta.whozaround.R;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    Button fb_login_button;

    CallbackManager callbackManager;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        //Init callbackManager (Should go to the Activity?)
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
            new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Login success
                Log.d(LOG_TAG, "Login with FB was a success");
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

        fb_login_button = (Button) rootView.findViewById(R.id.login_fb_btn);

        fb_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> permissions = new ArrayList<String>();
                permissions.add("public_profile");
                permissions.add("user_friends");
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), permissions);
            }
        });
        return rootView;
    }
}
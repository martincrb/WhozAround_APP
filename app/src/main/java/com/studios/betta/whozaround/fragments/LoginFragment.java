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
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.studios.betta.whozaround.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    @Bind (R.id.login_fb_btn) Button fb_login_button;
    @Bind (R.id.logo_login) ImageView title;
    @Bind (R.id.bg_login) ImageView bg;
    CallbackManager callbackManager;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, rootView);
        Picasso.with(getActivity()).load(R.drawable.login_background).into(bg);
        //Init callbackManager (Should go to the Activity?)
        Log.d("DEV FB HASH", FacebookSdk.getApplicationSignature(getActivity()));
        YoYo.with(Techniques.BounceInDown).duration(1000).playOn(title);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.login_fb_btn)
    public void loginWithFb() {
        ArrayList<String> permissions = new ArrayList<String>();
        permissions.add("public_profile");
        permissions.add("user_friends");
        permissions.add("user_events");
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), permissions);
    }
}
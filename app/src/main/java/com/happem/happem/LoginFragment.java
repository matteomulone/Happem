package com.happem.happem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

public class LoginFragment extends Fragment {

    private CallbackManager  mCallbackManagers;
    private TextView mTextViewDetails;
    private Profile FBprofile;
    private AccessToken FbAccessToken;
    private FacebookCallback<LoginResult> mCallback=new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken3 = loginResult.getAccessToken();
            FbAccessToken=loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            Log.d("matteo matteo", "onSuccess- AccessToken: " + accessToken3 + "   Profile:   " + profile);
            /* SEE CLASS "PROFILE" ON FACEBOOK DEVELOPER SITE TO SEE ALL THE METHODS */
            if (profile != null) {
                Log.i("NOMEUSER2", "nome2:" + profile.getName());
                mTextViewDetails.setText("Welcome:" + profile.getName());
            }else{
                Log.i("error", "error onSuccess");
            }
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException e) {

        }
    };
    private ProfileTracker mProfileTracker;
    private AccessTokenTracker mTokenTracker;


    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity () perche sto usando i fragment
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        mCallbackManagers = CallbackManager.Factory.create();
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken1) {
                Log.d("MATTEO MATTEO acc_tok", "FROM " + accessToken + "    TO: " + accessToken1);
                FbAccessToken=accessToken1;
                //Log.d("MATTEO MATTEO FB_acc_tok", "AppID:  "+FbAccessToken.getApplicationId().toString()+" UserID:  "+FbAccessToken.getUserId()+"");
                requestEvent();
            }
        };
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                mTextViewDetails.setText(constructWelcomeMessage(profile1));
                Log.d("MATTEO MATTEO profile", "Profile Start: " + profile + "  Profile end:  " + profile1);
                FBprofile=profile1;
                Log.d("MATTEO MATTEO", "OnCreate/OnCurrentChanged - Profile: " + FBprofile);
            }
        };
        mProfileTracker.startTracking();
        mTokenTracker.startTracking();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.login_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        //permessi vedi sito FB Developers --> amici in quest caso
        /*TODO CAPIRE COME SETTARE + PERMESSI CONTEMPORANEAMENTE*/
        //loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("user_events");
        //loginButton.setReadPermissions("friends_events");

        // If using in a fragment
        loginButton.setFragment(this);

        mTextViewDetails = (TextView) view.findViewById(com.happem.happem.R.id.text_details);
        loginButton.registerCallback(mCallbackManagers, mCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManagers.onActivityResult(requestCode, resultCode, data);
    }



    private String constructWelcomeMessage(Profile profile){
        StringBuffer stringBuffer = new StringBuffer();
        if (profile!=null){
            stringBuffer.append("Welcome:    "+profile.getName());
        }
        return stringBuffer.toString();
    };


    private void requestEvent(){
        if(FbAccessToken!=null){
            GraphRequest request = GraphRequest.newMeRequest(FbAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                    Log.d("MATTEO MATTEO", "Request UserInfo - Vediamo cosa esce fuori \n \n  " + graphResponse.getJSONObject().toString());
                }

            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link, events");
            request.setParameters(parameters);
            request.executeAsync();
        }
        else{
            Log.e("MATTEO MATTEO", "requyestEvent - Accss Tocken null");
        }
    };


}

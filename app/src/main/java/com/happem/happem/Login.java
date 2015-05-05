package com.happem.happem;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class Login extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.happem.happem.R.layout.login_layout);
    }
}

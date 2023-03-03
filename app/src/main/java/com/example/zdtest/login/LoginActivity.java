package com.example.zdtest.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.zdtest.LoginOrSignupFormState;
import com.example.zdtest.LoginOrSignupResult;
import com.example.zdtest.databinding.ActivityLoginBinding;
import com.example.zdtest.product.ProductListActivity;
import com.example.zdtest.signup.SignupActivity;

/**
 * The login page should display only the username and password fields
 * and the registered users only login the application
 */
public class LoginActivity extends AppCompatActivity implements View.OnTouchListener {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final TextView loginTextView = binding.jumpsignup;
        final ProgressBar loadingProgressBar = binding.loading;


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadingProgressBar.getVisibility() == View.VISIBLE) {
                    return;
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, SignupActivity.class), 100);
            }
        });
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginOrSignupFormState>() {
            @Override
            public void onChanged(LoginOrSignupFormState loginOrSignupFormState) {
                if (loginOrSignupFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginOrSignupFormState.isDataValid());
                if (loginOrSignupFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginOrSignupFormState.getUsernameError()));
                }
                if (loginOrSignupFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginOrSignupFormState.getPasswordError()));
                }
            }
        });
        loginViewModel.getLoginResult().observe(this, new Observer<LoginOrSignupResult>() {
            @Override
            public void onChanged(LoginOrSignupResult loginOrSignupResult) {
                if (loginOrSignupResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginOrSignupResult.getOperateError() != null) {
                    showLoginFailed(loginOrSignupResult.getOperateError());
                }
                if (loginOrSignupResult.isDataValid()) {
                    startActivity(new Intent(LoginActivity.this, ProductListActivity.class));
                    finish();
                }
            }
        });
        binding.container.setOnTouchListener(this);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            String userName = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            binding.username.setText(userName);
            binding.password.setText(password);
        }
    }


    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        hideKeyBoard();
        return false;
    }
}
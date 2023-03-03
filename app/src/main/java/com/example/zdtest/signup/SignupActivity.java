package com.example.zdtest.signup;

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
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.zdtest.LoginOrSignupFormState;
import com.example.zdtest.LoginOrSignupResult;
import com.example.zdtest.R;
import com.example.zdtest.databinding.ActivitySignupBinding;

/**
 * The signUp page should display username, password and email address fields
 */
public class SignupActivity extends AppCompatActivity implements View.OnTouchListener {

    private SignupViewModel signupViewModel;
    private ActivitySignupBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        signupViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(SignupViewModel.class);

        final EditText useremailEditText = binding.useremail;
        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.signup;
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
                signupViewModel.loginDataChanged(useremailEditText.getText().toString(), usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        useremailEditText.addTextChangedListener(afterTextChangedListener);
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadingProgressBar.getVisibility() == View.VISIBLE) {
                    return;
                }
                loadingProgressBar.setVisibility(View.VISIBLE);
                signupViewModel.signup(useremailEditText.getText().toString(), usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        signupViewModel.getLoginFormState().observe(this, new Observer<LoginOrSignupFormState>() {
            @Override
            public void onChanged(LoginOrSignupFormState loginOrSignupFormState) {
                if (loginOrSignupFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginOrSignupFormState.isDataValid());
                if (loginOrSignupFormState.getUseremailError() != null) {
                    useremailEditText.setError(getString(loginOrSignupFormState.getUseremailError()));
                }
                if (loginOrSignupFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginOrSignupFormState.getUsernameError()));
                }
                if (loginOrSignupFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginOrSignupFormState.getPasswordError()));
                }
            }
        });
        signupViewModel.getLoginOrSignUpResult().observe(this, new Observer<LoginOrSignupResult>() {
            @Override
            public void onChanged(LoginOrSignupResult loginOrSignupResult) {
                if (loginOrSignupResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginOrSignupResult.getOperateError() != null) {
                    showSignupFailed(loginOrSignupResult.getOperateError());
                }
                if (loginOrSignupResult.isDataValid()) {
                    Toast.makeText(getApplicationContext(), R.string.signup_success, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.putExtra("username", usernameEditText.getText().toString());
                    intent.putExtra("password", passwordEditText.getText().toString());

                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
        binding.container.setOnTouchListener(this);
    }

    private void showSignupFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
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
package com.example.zdtest;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
public class LoginOrSignupFormState {
    @Nullable
    private Integer useremailError;
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    public LoginOrSignupFormState(@Nullable Integer useremailError, @Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.useremailError = useremailError;
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    public LoginOrSignupFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    public LoginOrSignupFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getUseremailError() {
        return useremailError;
    }

    @Nullable
    public Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
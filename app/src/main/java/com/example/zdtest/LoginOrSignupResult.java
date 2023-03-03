package com.example.zdtest;

import androidx.annotation.Nullable;

public class LoginOrSignupResult {
    @Nullable
    private Integer operateError;
    private boolean isDataValid = false;

    public LoginOrSignupResult(@Nullable Integer operateError) {
        this.operateError = operateError;
        this.isDataValid = false;
    }

    public LoginOrSignupResult(boolean isDataValid) {
        this.isDataValid = isDataValid;
        this.operateError = null;
    }

    @Nullable
    public Integer getOperateError() {
        return operateError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}

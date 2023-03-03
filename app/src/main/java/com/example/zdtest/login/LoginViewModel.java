package com.example.zdtest.login;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.zdtest.DBHelper;
import com.example.zdtest.R;
import com.example.zdtest.LoginOrSignupFormState;
import com.example.zdtest.LoginOrSignupResult;
import com.example.zdtest.util.PatternUtils;

import org.jetbrains.annotations.NotNull;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<LoginOrSignupFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginOrSignupResult> loginResult = new MutableLiveData<>();

    public LoginViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    LiveData<LoginOrSignupFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginOrSignupResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        SQLiteOpenHelper helper = DBHelper.getmInstance(getApplication());
        SQLiteDatabase db = helper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query("user", null, "username = ?", new String[]{username}, null, null, null);
            if (cursor.moveToNext()) {
                String passwordTem = cursor.getString(3);
                if (password.endsWith(passwordTem)) {
                    loginResult.postValue(new LoginOrSignupResult(true));
                    cursor.close();
                    db.close();
                    return;
                }
            }
            cursor.close();
            db.close();
        }
        loginResult.postValue(new LoginOrSignupResult(R.string.login_failed));
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginOrSignupFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginOrSignupFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginOrSignupFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        String validValue = username.trim();
        if (validValue.length() >= 3 && validValue.length() <= 8) {
            return PatternUtils.compileUserName(validValue);
        }
        return false;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
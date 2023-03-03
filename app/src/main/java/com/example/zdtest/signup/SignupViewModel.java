package com.example.zdtest.signup;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Patterns;

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

public class SignupViewModel extends AndroidViewModel {

    private MutableLiveData<LoginOrSignupFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginOrSignupResult> signupResult = new MutableLiveData<>();

    public SignupViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    LiveData<LoginOrSignupFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginOrSignupResult> getLoginOrSignUpResult() {
        return signupResult;
    }

    public void signup(String useremail, String username, String password) {
        SQLiteOpenHelper helper = DBHelper.getmInstance(getApplication());
        SQLiteDatabase db = helper.getWritableDatabase();
        if (db.isOpen()) {
            if (checkUserEmail(db, useremail)){
                signupResult.postValue(new LoginOrSignupResult(R.string.repeat_email));
                db.close();
                return;
            } else if (checkUsername(db, username)){
                signupResult.postValue(new LoginOrSignupResult(R.string.repeat_username));
                db.close();
                return;
            }
            ContentValues values = new ContentValues();
            values.put("useremail", useremail);
            values.put("username", username);
            values.put("password", password);
            db.insert("user", null, values);
            db.close();
            signupResult.postValue(new LoginOrSignupResult(true));
        }
    }

    private boolean checkUserEmail(SQLiteDatabase db, String useremail) {
        try (Cursor cursor = db.query("user", null, "useremail = ?", new String[]{useremail}, null, null, null)) {
            return cursor != null && cursor.moveToNext();
        }
    }

    private boolean checkUsername(SQLiteDatabase db, String username) {
        try (Cursor cursor = db.query("user", null, "username = ?", new String[]{username}, null, null, null)) {
            return cursor != null && cursor.moveToNext();
        }
    }

    public void loginDataChanged(String email, String username, String password) {
        if (!isUserEmailValid(email)) {
            loginFormState.setValue(new LoginOrSignupFormState(R.string.invalid_useremail, null, null));
        } else if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginOrSignupFormState(null, R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginOrSignupFormState(null, null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginOrSignupFormState(true));
        }
    }

    // A placeholder email validation check
    private boolean isUserEmailValid(String useremail) {
        if (useremail == null) {
            return false;
        }
        if (useremail.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(useremail).matches();
        }
        return false;
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
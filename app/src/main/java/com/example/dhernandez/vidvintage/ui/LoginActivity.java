package com.example.dhernandez.vidvintage.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.Utils.Constants;
import com.example.dhernandez.vidvintage.Utils.ScreenUtils;
import com.example.dhernandez.vidvintage.presenter.ILoginPresenter;
import com.example.dhernandez.vidvintage.presenter.LoginPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.example.dhernandez.vidvintage.presenter.SplashPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

import static com.example.dhernandez.vidvintage.Utils.Constants.Screens.MAIN;

/**
 * Created by dhernandez on 30/08/2018.
 */

public class LoginActivity extends AppCompatActivity{

    @Inject
    PresenterFactory presenterFactory;
    ILoginPresenter presenter;

    private static final String GRAPH_PATH = "me/permissions";
    private static final String SUCCESS = "success";

    private FirebaseAuth mAuth;

    private static final int PICK_PERMS_REQUEST = 0;

    //private CallbackManager callbackManager;

    @BindView(R.id.login_button_false)
    Button fbLoginButtonFalse;

    @BindView(R.id.email_sign_in_button)
    Button emailLoginButton;

    @BindView(R.id.email_create_account_button)
    Button createAccountButton;

    @BindView(R.id.field_email)
    EditText mEmailField;

    @BindView(R.id.field_password)
    EditText mPasswordField;

    @BindView(R.id.login_progress)
    ProgressBar progressView;

    @BindView(R.id.login_split)
    ImageView splitView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AndroidInjection.inject(this);

        ButterKnife.bind(this, findViewById(R.id.login_view));

        mAuth = FirebaseAuth.getInstance();


        this.presenter = ViewModelProviders.of(this, presenterFactory).get(LoginPresenter.class);

        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.setEmail(editable.toString());
            }
        });
        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                presenter.setPassword(editable.toString());
            }
        });

        presenter.getShowLoginError().observe(this, this::showLoginError);
        presenter.getShowFormFields().observe(this, this::showEmailLogin);
        presenter.getNavigateTo().observe(this, screen ->{
            if(screen != null){
                switch (screen){
                    case MAIN:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        presenter.getNavigateTo().setValue(null);
                        break;
                }
            }
        });

        checkUserSession();
        //hideSystemUI();

    }

    private void checkUserSession() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){}
            //presenter.getNavigateTo().setValue(MAIN);

    }

    private void showLoginError(Boolean showError) {
        if(showError){
            Toast.makeText(getApplicationContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showEmailLogin(Boolean showLogin) {
        if(showLogin){
            fbLoginButtonFalse.setVisibility(View.GONE);
            mEmailField.setVisibility(View.VISIBLE);
            mPasswordField.setVisibility(View.VISIBLE);
            createAccountButton.setVisibility(View.VISIBLE);
            splitView.setVisibility(View.GONE);
            emailLoginButton.setText(R.string.login_active_text);
        }else{
            fbLoginButtonFalse.setVisibility(View.VISIBLE);
            splitView.setVisibility(View.VISIBLE);
            mEmailField.setVisibility(View.GONE);
            mPasswordField.setVisibility(View.GONE);
            createAccountButton.setVisibility(View.GONE);
            emailLoginButton.setText(R.string.log_in);
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        if(mEmailField.getVisibility() == View.VISIBLE)
            showEmailLogin(false);
        else
            super.onBackPressed();
    }

    @OnClick(R.id.email_sign_in_button)
    public void onClickMailLogin(){
        if(mEmailField.getVisibility()== View.GONE)
            presenter.doEmailLogin();
        else {
            showProgress(true);
            mAuth.signInWithEmailAndPassword(mEmailField.getText().toString(), mPasswordField.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            showProgress(false);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                presenter.getNavigateTo().setValue(MAIN);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    @OnClick(R.id.email_create_account_button)
    public void onClickSignUp(){
        if(formIsValid()) {
            presenter.onSignUp();

            mAuth.createUserWithEmailAndPassword(
                    mEmailField.getText().toString(),
                    mPasswordField.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            showProgress(false);
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                presenter.getNavigateTo().setValue(MAIN);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }

    private boolean formIsValid() {

        if(mEmailField.getVisibility() == View.VISIBLE &&
                !mEmailField.getText().toString().isEmpty() &&
                !mPasswordField.getText().toString().isEmpty()){
            return true;
        }else if(mEmailField.getText().toString().isEmpty()){
            mEmailField.setError("Incorrect mail");
        }else if(mPasswordField.getText().toString().isEmpty()){
            mPasswordField.setError("Incorrect password");
        }
        return false;

    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkUserSession();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            progressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
        //This is important in order to avoid performing multiple call for the same UC before
        //repetitive button touches
        if (show) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            ScreenUtils.hideSoftKeyboard(this);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

    }

}

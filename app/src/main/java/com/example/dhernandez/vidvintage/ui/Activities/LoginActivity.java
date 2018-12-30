package com.example.dhernandez.vidvintage.ui.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.example.dhernandez.vidvintage.presenter.LoginPresenter.ILoginPresenter;
import com.example.dhernandez.vidvintage.presenter.LoginPresenter.LoginPresenter;
import com.example.dhernandez.vidvintage.presenter.PresenterFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
        presenter.getFullScreen().observe(this, this::setFullScreen);
        presenter.getAppTheme().observe(this, this::setAppTheme);

    }

    private void setAppTheme(Constants.Themes theme) {
        Drawable bg;
        switch (theme){
            case DARK:
                bg = getResources().getDrawable(R.drawable.cocktail_menu_item_bg);
                break;
            case LIGHT:
                bg = getResources().getDrawable(R.drawable.light_theme);
                break;
            default:
                bg = getResources().getDrawable(R.drawable.cocktail_menu_item_bg);
        }

        findViewById(R.id.login_view).setBackgroundDrawable(bg);

    }

    private void setFullScreen(Boolean fullScreen) {
        if(fullScreen)
            this.hideSystemUI();
        else
            this.showSystemUI();

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
            //mAuth.signInWithEmailAndPassword("dhg1994@hotmail.com","12341234")
                    //mAuth.signInWithEmailAndPassword(mEmailField.getText().toString(), mPasswordField.getText().toString())
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

    // This snippet hides the system bars.
    private void hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = this.getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

}

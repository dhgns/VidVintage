package com.example.dhernandez.vidvintage.Utils.DialogFragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dhernandez.vidvintage.R;
import com.example.dhernandez.vidvintage.presenter.ProfilePresenter.IProfilePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhernandez on 27/12/2018.
 */

@SuppressLint("ValidFragment")
public class NewCocktailImageURLDialog extends DialogFragment {

    private final IProfilePresenter presenter;

    EditText cocktailURL;
    Button acceptButton;

    public NewCocktailImageURLDialog(IProfilePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_cocktail_url, container, false);

        cocktailURL = view.findViewById(R.id.new_cocktail_url);
        acceptButton = view.findViewById(R.id.new_cocktail_dismiss_dialog);

        acceptButton.setOnClickListener((click) ->{
            presenter.getNewCocktailURL().setValue(cocktailURL.getText().toString());
            dismiss();
        });

        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        cocktailURL.setText(null);
    }
}

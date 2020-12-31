package com.example.simp.editor_components.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.simp.R;

import java.util.Objects;

public class UserHelpDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_user_help, null);
        view.findViewById(R.id.button_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getDialog()).dismiss();
            }
        });
        builder.setView(view);
        Bundle args = getArguments();
        if(args != null)
        {
            TextView tv = view.findViewById(R.id.tv_describtion);
            String description = args.getString("description");
            tv.setText(description);
        }
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
    }

    public static UserHelpDialog newInstance(String description) {
        Bundle args = new Bundle();
        UserHelpDialog fragment = new UserHelpDialog();
        args.putString("description", description);
        fragment.setArguments(args);
        return fragment;
    }
}

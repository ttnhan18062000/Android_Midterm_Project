package com.example.bottomnavigationactivity.editor_components.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bottomnavigationactivity.R;

public class UserHelpDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_user_help, null);
        builder.setView(view)
                .setTitle("Tool Describtion")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        Bundle args = getArguments();
        if(args != null)
        {
            TextView tv = view.findViewById(R.id.tv_describtion);
            String describtion = args.getString("describtion");
            tv.setText(describtion);
        }
        return builder.create();
    }

    public static UserHelpDialog newInstance(String describtion) {
        Bundle args = new Bundle();
        UserHelpDialog fragment = new UserHelpDialog();
        args.putString("describtion", describtion);
        fragment.setArguments(args);
        return fragment;
    }

}

package com.example.bottomnavigationactivity.editor_components.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bottomnavigationactivity.R;

public class SetTextDialog extends DialogFragment {

    private EditText mEditText;
    private SetTextDialogListener listener;

    public static SetTextDialog newInstance() {
        Bundle args = new Bundle();
        SetTextDialog fragment = new SetTextDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_set_text, null);

        builder.setView(view)
                .setTitle("Enter your Text!")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyText(mEditText.getText().toString());
                        dismiss();
                    }
                });
        mEditText = view.findViewById(R.id.layout_dialog_set_text_text_view);
        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (SetTextDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must implements SetRatioDialogListener");
        }
    }

    public interface SetTextDialogListener {
        void applyText(String text);
    }
}

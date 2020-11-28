package com.example.bottomnavigationactivity.process_components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bottomnavigationactivity.R;

public class SetPlainDialog extends DialogFragment {
    //private SetPlainDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_set_plain, null);
        initSpinnerRatioMeasure(view);
        builder.setView(view)
                .setTitle("Enter Length")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    private void initSpinnerRatioMeasure(View view) {
        Spinner spinner = view.findViewById(R.id.spinnter_set_ratio_measure);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.ratio_measure, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public static SetPlainDialog newInstance() {
        Bundle args = new Bundle();
        SetPlainDialog fragment = new SetPlainDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            //listener = (SetPlainDialogListener)getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must implements SetRatioDialogListener");
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        WindowManager.LayoutParams params = Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).getAttributes();
//        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        Objects.requireNonNull(getDialog().getWindow()).setAttributes(params);
//    }

//    public interface SetPlainDialogListener{
//    }
}

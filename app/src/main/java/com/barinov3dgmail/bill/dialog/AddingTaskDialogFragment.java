package com.barinov3dgmail.bill.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Calendar;
import java.util.Date;

import com.barinov3dgmail.bill.MainActivity;
import com.barinov3dgmail.bill.R;
import com.barinov3dgmail.bill.Utils;


public class AddingTaskDialogFragment extends DialogFragment {

    public static int userAddingMoneyValue;
    private AddingTaskListener addingTaskListener;

    public interface AddingTaskListener {
        void onTaskAdded();
        void onTaskAddingCancel();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addingTaskListener = (AddingTaskListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddingTaskListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View container = inflater.inflate(R.layout.dialog_task, null);

        final TextInputLayout tilTitle = (TextInputLayout) container.findViewById(R.id.tilDialogTaskTitle);
        final EditText etTitle = tilTitle.getEditText();

        TextInputLayout tilDate = (TextInputLayout) container.findViewById(R.id.tilDialogTaskDate);
        final EditText etDate = tilDate.getEditText();

        tilTitle.setHint(getResources().getString(R.string.task_title));
        tilDate.setHint(getResources().getString(R.string.task_date));

        builder.setView(container);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etDate.length() == 0) {

                    etDate.setText(" ");
                }

                DialogFragment datePickerFragment = new DatePickerFragment() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //view.updateDate(1994, 6, 12);
                        //view.init (1998, 12, 10, null);
                        view.isShown();
                        Calendar dateCalendar = Calendar.getInstance();
                        dateCalendar.set(year, monthOfYear, dayOfMonth);
                        etDate.setText(Utils.getDate(dateCalendar.getTimeInMillis()));
                        //view.init(year, monthOfYear, dayOfMonth, null);

                    }

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        etDate.setText(null);
                    }
                };
                datePickerFragment.show(getFragmentManager(), "DatePickerFragment");

            }
        });

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addingTaskListener.onTaskAdded();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addingTaskListener.onTaskAddingCancel();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if(etTitle.length() == 0) {
                    positiveButton.setEnabled(false);
                    tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                }

                etTitle.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() == 0) {
                            positiveButton.setEnabled(false);
                            tilTitle.setError(getResources().getString(R.string.dialog_error_empty_title));
                        } else {
                            positiveButton.setEnabled(true);
                            tilTitle.setErrorEnabled(false);
                            userAddingMoneyValue = Integer.valueOf(String.valueOf(etTitle.getText()));
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }


        });

        return alertDialog;
    }


}

package com.barinov3dgmail.bill;

import android.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.barinov3dgmail.bill.dialog.AddingTaskDialogFragment;

public class MainActivity extends AppCompatActivity implements AddingTaskDialogFragment.AddingTaskListener {

    FloatingActionButton fab_plus;
    Animation fabOpen, fabClose, fabRClocwise, fabRAntiClocwise;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRClocwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRAntiClocwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen){
                    fab_plus.startAnimation(fabRAntiClocwise);
                    isOpen = false;

                }
                else
                {
                    fab_plus.startAnimation(fabRClocwise);
                    isOpen = true;
                }

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_plus);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(getFragmentManager(), "AddingTaskDialogFragment");
            }
        });

    }

    @Override
    public void onTaskAdded() {
        Toast.makeText(this, "Сумма добавлена", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Ок, потом так потом...", Toast.LENGTH_LONG).show();

    }
}

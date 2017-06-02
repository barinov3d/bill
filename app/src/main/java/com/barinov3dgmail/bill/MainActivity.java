package com.barinov3dgmail.bill;

import android.app.DialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barinov3dgmail.bill.dialog.AddingTaskDialogFragment;

public class MainActivity extends AppCompatActivity implements AddingTaskDialogFragment.AddingTaskListener {

    FloatingActionButton fab_plus;
    Animation fabRClocwise, fabRAntiClocwise;
    boolean isOpen = false;
/*
    TextView tvSpent = (TextView) findViewById(R.id.tv_spent);
    TextView tvRest = (TextView) findViewById(R.id.tv_rest);*/

    android.app.FragmentManager fragmentManager;
    private int progress = 0;
    private int maxProgress = 0;
    private ProgressBar pbHorizontal;
    private TextView tvProgressHorizontal;
    private TextView tvProgressMaxHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fabRClocwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRAntiClocwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        pbHorizontal = (ProgressBar) findViewById(R.id.pbar_main);
        tvProgressHorizontal = (TextView) findViewById(R.id.tv_spent);
        tvProgressMaxHorizontal = (TextView) findViewById(R.id.tv_rest);
        //maxProgress(maxProgress);
        fragmentManager = getFragmentManager();


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
/*
    public void onClick(View v, int number) {
        if (number>0) progress = progress + number;
        else maxProgress = maxProgress - number;
        //postProgress(progress);
        //maxProgress(maxProgress);
    }
    public void onClick(View v) {

        onClick(findViewById(Ids.ID_ok1), 10);
    }*/

    private void postProgress(int progress) {
        String strProgress = String.valueOf(progress) + " руб.";
        String strNegProgress = String.valueOf(progress-maxProgress) + " руб.";
        pbHorizontal.setProgress(progress);

        if (progress == 0) {
            pbHorizontal.setSecondaryProgress(0);
        } else {
            pbHorizontal.setSecondaryProgress(progress + 5);
        }

        if (progress<=maxProgress)
            tvProgressMaxHorizontal.setText(strProgress);
        else tvProgressMaxHorizontal.setText("уходим в минус "+"-"+strNegProgress);
    }
    private void maxProgress(int maxProgress) {
        String strProgress = String.valueOf(maxProgress) + " руб.";
        tvProgressHorizontal.setText(strProgress);
        pbHorizontal.setMax(maxProgress);
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

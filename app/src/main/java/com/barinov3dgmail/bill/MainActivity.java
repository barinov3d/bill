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

    android.app.FragmentManager fragmentManager;

    private int spent = 0;
    private int rest = 0;
    private int allMoney = 0;

    private ProgressBar pbHorizontal;
    private TextView spentShow;
    private TextView restShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fabRClocwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRAntiClocwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        pbHorizontal = (ProgressBar) findViewById(R.id.pbar_main);
        spentShow = (TextView) findViewById(R.id.tv_spent);
        restShow = (TextView) findViewById(R.id.tv_rest);
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
    private void setUserProgress(int spent) {
        String strProgress = String.valueOf(spent) + " руб.";
        String strNegProgress = String.valueOf(spent-allMoney) + " руб.";
        pbHorizontal.setProgress(spent);

        if (spent == 0) {
            pbHorizontal.setSecondaryProgress(0);
        } else {
            pbHorizontal.setSecondaryProgress(spent + 5);
        }
        if (spent<=allMoney)
            tvProgressMaxHorizontal.setText(strProgress);
        else tvProgressMaxHorizontal.setText("уходим в минус "+"-"+strNegProgress);
    }
    private void maxProgress(int maxProgress) {
        String strProgress = String.valueOf(maxProgress) + " руб.";
        tvProgressHorizontal.setText(strProgress);
        pbHorizontal.setMax(maxProgress);
    }
*/

    private void setAllMoney(int additionRest){
        rest+=additionRest;
        spentShow.setText(rest + " руб.");
        pbHorizontal.setMax(allMoney);
    }

    private void setSpentMoney(int additionSpent){
        spent+=additionSpent;
        spentShow.setText(spent + " руб.");
        pbHorizontal.setProgress(spent);
    }
    private void setRestMoney(){
        rest = allMoney - spent;
        restShow.setText(rest + " руб.");
    }
    private void UpdateMoneyValues(){
        pbHorizontal.setMax(allMoney);
        pbHorizontal.setProgress(spent);
        setRestMoney();
    }
}

    @Override
    public void onTaskAdded() {
        Toast.makeText(this, "Сумма добавлена", Toast.LENGTH_LONG).show();
        // TODO: add logic for radiobuttons 02.06.2017
        /*switch(radioButton.valueOn) {
            case rb_spent :
                setSpentMoney(AddingTaskDialogFragment.userAddingMoneyValue);
                break;
            case rb_rest:
                setAllMoney(AddingTaskDialogFragment.userAddingMoneyValue);
                break;*/
            UpdateMoneyValues();
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Ок, потом так потом...", Toast.LENGTH_LONG).show();

    }
}

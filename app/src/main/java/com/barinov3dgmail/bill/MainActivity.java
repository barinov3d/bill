package com.barinov3dgmail.bill;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.barinov3dgmail.bill.dialog.AddingTaskDialogFragment;

public class MainActivity extends AppCompatActivity implements AddingTaskDialogFragment.AddingTaskListener {

    FloatingActionButton fab_plus;
    Animation fabRClocwise, fabRAntiClocwise;
    boolean isOpen = false;

    View selectedRadio;
    //RadioButton selectedRadio;

    android.app.FragmentManager fragmentManager;

    private int spent;
    private int rest;
    private int allMoney;

    private ProgressBar pbHorizontal;
    private TextView spentShow;
    private TextView restShow;
    private TextView allMoneyShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
        spent = sharedPreferences.getInt(getString(R.string.PREF_SPENT), 0);
        rest = sharedPreferences.getInt(getString(R.string.PREF_REST), 0);
        allMoney = sharedPreferences.getInt(getString(R.string.PREF_ALLMONEY), 0);


        fab_plus = (FloatingActionButton) findViewById(R.id.fab_plus);
        fabRClocwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRAntiClocwise = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        selectedRadio = findViewById(R.id.rb_spent);
        pbHorizontal = (ProgressBar) findViewById(R.id.pbar_main);
        spentShow = (TextView) findViewById(R.id.tv_spent);
        restShow = (TextView) findViewById(R.id.tv_rest);
        allMoneyShow = (TextView) findViewById(R.id.tv_allAddedMoney);

        fragmentManager = getFragmentManager();

        //selectedRadio.setChecked(true);

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
        updateMoneyProgress();
    }

    public void setAllMoney(int additionRest){
        allMoney+=additionRest;
        rest=allMoney-spent;
    }

    public void setSpentMoney(int additionSpent){
        spent+=additionSpent;
        rest=allMoney-spent;
    }
    private void updateMoneyProgress(){
        pbHorizontal.setMax(allMoney);
        pbHorizontal.setProgress(spent);
        restShow.setText(rest + " руб.");
        spentShow.setText(spent + " руб.");
        allMoneyShow.setText("Доход общий: " + allMoney + " руб.");

    }

    public void onRadioButtonClicked(View view) {
        selectedRadio = view;
    }

    @Override
    public void onTaskAdded() {
        Toast.makeText(this, "Сумма добавлена", Toast.LENGTH_LONG).show();
        ChosingRadioButtonCategory();
        updateMoneyProgress();
    }


    public void ChosingRadioButtonCategory(){

        switch(selectedRadio.getId()) {
            case R.id.rb_spent:
                setSpentMoney(AddingTaskDialogFragment.userAddingMoneyValue);
                break;
            case R.id.rb_rest:
                setAllMoney(AddingTaskDialogFragment.userAddingMoneyValue);
                break;
        }
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Ок, потом так потом...", Toast.LENGTH_LONG).show();
    }


    public void saveSettings(){
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.PREF_SPENT),spent);
        editor.putInt(getString(R.string.PREF_REST),rest);
        editor.putInt(getString(R.string.PREF_ALLMONEY),allMoney);
        editor.commit();

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();

        saveSettings();
    }
}

package com.dbtest.ivan.app.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.abstract_toolbar_activity.AbstractToolbarActivity;
import com.dbtest.ivan.app.activity.list_activity.ListActivity;
import com.dbtest.ivan.app.fragment.CategoryDialog;
import com.dbtest.ivan.app.fragment.DateTimePickerDialog;
import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.receiver.CustomReceiver;
import com.dbtest.ivan.app.services.intent.CategoryIntentService;
import com.dbtest.ivan.app.services.intent.FullSyncService;
import com.dbtest.ivan.app.services.intent.ReminderIntentService;
import com.dbtest.ivan.app.utils.WaitingManager;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DetailReminderActivity extends AbstractToolbarActivity implements DateTimePickerDialog.DateTimeDialogListener, CategoryDialog.CategoryDialogListener, WaitingActivity {
    private OrmHelper helper;
    private ArrayAdapter<String> categoriesAdapter;
    private Spinner spinner;
    private Calendar reminderDate;
    private Button pickButton;
    private Button categoryButton;
    private Button insert;
    private EditText textView;
    private TextInputLayout textLayout;
    private ProgressBar bar;
    private BroadcastReceiver receiver;

    @NonNull
    @Override
    protected Integer getBodyResId() {
        return R.layout.activity_detail_reminder;
    }

    @NonNull
    @Override
    public Integer getMenuPosition() {
        return -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bar = (ProgressBar) findViewById(R.id.details_bar);
        bar.setVisibility(View.GONE);
        helper = OpenHelperManager.getHelper(this,OrmHelper.class);
        List<Category> categoryList;
        TextInputLayout layout = (TextInputLayout) findViewById(R.id.details_text_supp);
        layout.setCounterEnabled(true);
        try {
            categoryList = helper.getCategoryDao().queryForAll();
            List<String> categoriesNames = new ArrayList<>(categoryList.size());
            for(Category c : categoryList){
                categoriesNames.add(c.getName());
            }
            categoriesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categoriesNames );

            spinner = (Spinner) findViewById(R.id.details_category_spinner);
            categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(categoriesAdapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        textView = ((EditText) findViewById(R.id.details_text));
        textLayout = ((TextInputLayout) findViewById(R.id.details_text_supp));
        pickButton = (Button) findViewById(R.id.pick_date_button);
        if (pickButton != null) {
            pickButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDateTimeDialog();
                }
            });
        }

        categoryButton = (Button) findViewById(R.id.details_add_category_button);
        if (categoryButton != null) {
            categoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCategoryDialog();
                }
            });
        }
        insert = (Button) findViewById(R.id.details_add);
        if (insert != null) {
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = textView.getText().toString();
                    String categoryName = (String) spinner.getSelectedItem();
                    if((reminderDate != null && categoryName != null) && !text.isEmpty()) {
                        Long time = reminderDate.getTimeInMillis();
                        Bundle bundle = new Bundle();
                        bundle.putLong(ReminderIntentService.TIME, time);
                        bundle.putString(ReminderIntentService.TEXT, text);
                        bundle.putString(ReminderIntentService.CATEGORY, categoryName);
                        bundle.putLong(ReminderIntentService.ID, -1L);
                        Intent intent = new Intent(DetailReminderActivity.this, ReminderIntentService.class);
                        intent.putExtras(bundle);
                        startService(intent);
                        setWaiting(true);
                        IntentFilter filter = new IntentFilter(CustomReceiver.WAITING_ACTION);
                        filter.addCategory(Intent.CATEGORY_DEFAULT);
                        receiver = new CustomReceiver(DetailReminderActivity.this);
                        LocalBroadcastManager.getInstance(DetailReminderActivity.this).registerReceiver(receiver, filter);
                    }
                }
            });

            Button wtf = (Button) findViewById(R.id.details_wtf);
            if (wtf != null) {
                wtf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent syncAll = new Intent(DetailReminderActivity.this, FullSyncService.class);
                        startService(syncAll);
                    }
                });
            }

        }
    }
    public void showDateTimeDialog(){
        DateTimePickerDialog dateTimePickerDialog = new DateTimePickerDialog();
        dateTimePickerDialog.show(getFragmentManager(),"Choose date");
    }
    @Override
    protected void onDestroy() {
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }

    @Override
    public void onDateTimeSelect(Calendar calendar) {
        TextView dateTextView = (TextView) findViewById(R.id.details_picked_date);
        if (dateTextView != null) {
            reminderDate = calendar;
            Date d = new Date(calendar.getTimeInMillis());
            SimpleDateFormat format = new SimpleDateFormat("EEEE dd.MM.yyyy HH:mm");
            dateTextView.setText(format.format(d));
        }
    }

    public void showCategoryDialog(){
        CategoryDialog categoryDialog = new CategoryDialog();
        categoryDialog.show(getFragmentManager(), "Create category");
    }
    @Override
    public void onNewCategory(Category category) {
        categoriesAdapter.add(category.getName());
        spinner.setSelection(categoriesAdapter.getPosition(category.getName()));
        Bundle bundle = new Bundle();
        bundle.putString(CategoryIntentService.CATEGORY_NAME, category.getName());
        bundle.putString(CategoryIntentService.CATEGORY_PICTURE, category.getPicture());
        Intent intent = new Intent(this, CategoryIntentService.class);
        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    public void setWaiting(boolean isWaiting) {
        WaitingManager.makeWaitingButton(this, insert, isWaiting);
        WaitingManager.makeWaitingButton(this, pickButton, isWaiting);
        WaitingManager.makeWaitingButton(this, categoryButton, isWaiting);
        WaitingManager.makeWaitingView(textView, isWaiting);
        WaitingManager.makeWaitingView(spinner, isWaiting);
        WaitingManager.makeWaitingProgressBar(this, bar, isWaiting);
    }

    @Override
    protected void onPause() {
        if(receiver != null) LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void notifyResult(String result) {
        Toast.makeText(DetailReminderActivity.this, result, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }
}

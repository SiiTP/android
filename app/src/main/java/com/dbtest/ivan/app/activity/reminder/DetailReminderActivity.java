package com.dbtest.ivan.app.activity.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.activity.listActivity.ListActivity;
import com.dbtest.ivan.app.fragment.CategoryDialog;
import com.dbtest.ivan.app.logic.db.OrmHelper;
import com.dbtest.ivan.app.logic.db.entities.Category;
import com.dbtest.ivan.app.services.intent.CategoryIntentService;
import com.dbtest.ivan.app.services.intent.ReminderIntentService;
import com.dbtest.ivan.app.utils.WaitingManager;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DetailReminderActivity extends ReminderActivity implements CategoryDialog.CategoryDialogListener {
    private ArrayAdapter<String> categoriesAdapter;
    private Spinner spinner;

    private Button categoryButton;


    private Long id = -1L;


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
        OrmHelper helper = OpenHelperManager.getHelper(this, OrmHelper.class);
        List<Category> categoryList;
        try {
            categoryList = helper.getCategoryDao().queryForAll();
            List<String> categoriesNames = new ArrayList<>(categoryList.size());
            for(Category c : categoryList){
                if(!c.getName().equals(Category.CATEGORY_FRIENDS_NAME)) {
                    categoriesNames.add(c.getName());
                }
            }
            categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesNames);
            spinner = (Spinner) findViewById(R.id.details_category_spinner);
            categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(categoriesAdapter);
            OpenHelperManager.releaseHelper();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        categoryButton = (Button) findViewById(R.id.details_add_category_button);
        if (categoryButton != null) {
            categoryButton.setOnClickListener(v -> showCategoryDialog());
        }

    }

    public Spinner getSpinner() {
        return spinner;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public void initSubmitButton() {
        submit = (Button) findViewById(R.id.details_add);
        if (submit != null) {
            submit.setOnClickListener(v -> {
                String text = this.reminderText.getText().toString();
                String categoryName = (String) spinner.getSelectedItem();
                if ((reminderDate != null && categoryName != null) && !text.isEmpty()) {
                    Long time = reminderDate.getTimeInMillis();
                    Bundle bundle = new Bundle();
                    bundle.putLong(ReminderIntentService.TIME, time);
                    bundle.putString(ReminderIntentService.TEXT, text);
                    bundle.putString(ReminderIntentService.CATEGORY, categoryName);
                    bundle.putLong(ReminderIntentService.ID, id);
                    Intent intent = new Intent(DetailReminderActivity.this, ReminderIntentService.class);
                    intent.putExtras(bundle);
                    startService(intent);
                    startWaitingReceiver();
                }
            });
        }
    }



    public void showCategoryDialog(){
        CategoryDialog categoryDialog = new CategoryDialog();
        categoryDialog.show(getFragmentManager(), "Create category");
    }
    protected void setSpinnerCategory(String categoryName){
        final int OBJECT_NOT_FOUND = -1;
        if(categoryName != null){
            int pos = categoriesAdapter.getPosition(categoryName);
            if(pos != OBJECT_NOT_FOUND) {
                spinner.setSelection(pos);
            }
        }
    }
    @Override
    public void onNewCategory(Category category) {
        categoriesAdapter.add(category.getName());
        setSpinnerCategory(category.getName());
        Bundle bundle = new Bundle();
        bundle.putString(CategoryIntentService.CATEGORY_NAME, category.getName());
        bundle.putString(CategoryIntentService.CATEGORY_PICTURE, category.getPicture());
        Intent intent = new Intent(this, CategoryIntentService.class);
        intent.putExtras(bundle);
        startService(intent);
    }
    @Override
    public void setWaiting(boolean isWaiting) {
        WaitingManager.makeWaitingButton(this, submit, isWaiting);
        WaitingManager.makeWaitingButton(this, pickButton, isWaiting);
        WaitingManager.makeWaitingButton(this, categoryButton, isWaiting);
        WaitingManager.makeWaitingView(reminderText, isWaiting);
        WaitingManager.makeWaitingView(spinner, isWaiting);
        WaitingManager.makeWaitingProgressBar(this, bar, isWaiting);
    }


    @Override
    public void notifyResult(String result) {
        Toast.makeText(DetailReminderActivity.this, result, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }
}

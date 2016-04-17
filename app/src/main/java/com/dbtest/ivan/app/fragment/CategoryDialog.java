package com.dbtest.ivan.app.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.dbtest.ivan.app.R;
import com.dbtest.ivan.app.logic.db.entities.Category;

/**
 * Created by ivan on 18.04.16.
 */
public class CategoryDialog extends DialogFragment {
    private CategoryDialogListener listener;
    private View categoryView;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (CategoryDialogListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        categoryView = View.inflate(getActivity(), R.layout.category_dialog_layout, null);
        categoryView.findViewById(R.id.category_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) categoryView.findViewById(R.id.category_name)).getText().toString();

                Category category = new Category();
                category.setName(name);
                category.setPicture("");//todo add pictures!!1!1!!!

                listener.onNewCategory(category);
                CategoryDialog.this.dismiss();

            }
        });
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.setView(categoryView);

        return dialog;
    }
    public interface CategoryDialogListener{
        void onNewCategory(Category category);
    }
}

package com.example.eaedaid.armd;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by emazimo on 10/22/2015.
 */
public class PermissionViewHolder {
    private CheckBox checkBox;
    private TextView textView;

    public PermissionViewHolder() {
    }

    public PermissionViewHolder(TextView textView, CheckBox checkBox) {
        this.checkBox = checkBox;
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}

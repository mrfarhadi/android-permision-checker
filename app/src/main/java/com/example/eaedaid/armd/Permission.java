package com.example.eaedaid.armd;

/**
 * Created by emazimo on 10/22/2015.
 */
public class Permission {
    private String name;
    private boolean checked;

    public Permission() {
    }

    public Permission(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String checkBoxName) {
        this.name = checkBoxName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void toggleChecked() {
        checked = !checked;
    }
}

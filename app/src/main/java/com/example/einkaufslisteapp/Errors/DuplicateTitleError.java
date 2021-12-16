package com.example.einkaufslisteapp.Errors;

import android.content.Context;
import android.widget.Toast;
import managerlists.ManagerList;

public class DuplicateTitleError implements TitleFieldError {

    private Context context;
    private ManagerList managerList;

    public DuplicateTitleError(Context context, ManagerList managerList) {
        this.context = context;
        this.managerList = managerList;
    }

    @Override
    public boolean isTitleValid(String title) {
        if (managerList.doesListNameExist(title)) {
            return false;
        }

        return true;
    }

    @Override
    public void toastMessage() {
        Toast toastDuplicateTitle = Toast.makeText(context, "Der Titel ist bereits vorhanden, bitte einen neuen Titel schreiben", Toast.LENGTH_SHORT);
        toastDuplicateTitle.show();
    }
}

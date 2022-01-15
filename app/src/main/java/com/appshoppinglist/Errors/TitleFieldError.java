package com.appshoppinglist.Errors;

public interface TitleFieldError {

    boolean isTitleValid(String title);

    void toastMessage();

}

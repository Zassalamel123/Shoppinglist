package com.example.einkaufslisteapp.Errors;

public interface TitleFieldError {

    boolean isTitleValid(String title);

    void toastMessage();

}

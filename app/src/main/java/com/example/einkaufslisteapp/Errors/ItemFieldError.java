package com.example.einkaufslisteapp.Errors;

public interface ItemFieldError {

    boolean isItemFieldValid();

    void toastMessage();

}

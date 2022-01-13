package com.example.einkaufslisteapp.Decorators;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import com.example.einkaufslisteapp.Errors.ItemFieldError;

public class SoundPlayerDecorator implements ItemFieldError {

    private ItemFieldError itemFieldError;
    private Context context;

    public SoundPlayerDecorator(ItemFieldError itemFieldError, Context context) {
        this.itemFieldError = itemFieldError;
        this.context = context;
    }

    @Override
    public boolean isItemFieldValid() {
        return itemFieldError.isItemFieldValid();
    }

    @Override
    public void toastMessage() {
        itemFieldError.isItemFieldValid();
        playSound();
    }

    private void playSound() {
        MediaPlayer mp = MediaPlayer.create(context, RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION));
        mp.start();
    }
}

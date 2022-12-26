package com.circular.circular.utils;

import android.text.Editable;
import android.text.TextWatcher;

public class CustomWatcher implements TextWatcher {


    Object item;

    public CustomWatcher(Object item)
    {
        this.item = item;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void afterTextChanged(Editable editable)
    {

    }

}

package com.forme.agents.Helper;

import android.content.Context;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    public static String getString(TextInputEditText editText)
    {
        return editText.getText().toString().trim();
    }

    public static void showMessage(Context context, String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidEmail(String email)
    {
      return   (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());

    }

}

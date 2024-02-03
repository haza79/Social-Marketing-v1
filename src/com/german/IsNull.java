package com.german;

import javax.swing.*;
import java.util.ArrayList;

public class IsNull {

    public static boolean isTextFieldNull(JTextField textField){
        boolean isNull = textField.getText().equals("") || textField.getText().equals(null);
        return isNull;
    }

    public boolean isMultiPlyTextFieldNull(ArrayList<Boolean> isNull){
        boolean isNull2 = false;
        for (int i = 0; i<isNull.size();i++){
            if (isNull.get(i) == false){
                isNull2 = true;
                break;
            }
        }
        return isNull2;

    }


}

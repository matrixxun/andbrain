/**
 * Copyright (C) 2013-2016 www.andbrain.com
 * Lets Build Awesome Mobile Apps Using The Simple Way
 */
package andbrain.annotations;

import android.support.annotation.IdRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Target(METHOD)
@Retention(CLASS)
@ListenerClass(
        targetType = "android.widget.TextView",
        setter = "addTextChangedListener",
        lisner = "android.text.TextWatcher",
        method = {
                @ListenerMethod(
                        name = "onTextChanged",
                        parameters = {
                                "java.lang.CharSequence",
                                "int",
                                "int",
                                "int"},
                        mainmethod = "yes"
                ),
                @ListenerMethod(
                        name = "beforeTextChanged",
                        parameters = {
                                "java.lang.CharSequence",
                                "int",
                                "int",
                                "int"
                        },
                        mainmethod = "no"
                ),
                @ListenerMethod(
                        name = "afterTextChanged",
                        parameters = "android.text.Editable",
                        mainmethod = "no"
                )


        }

)
public @interface OnTextChanged {
    @IdRes int[] value() default {0};
}

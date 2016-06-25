/**
 * Copyright (C) 2013-2016 www.andbrain.com
 * Lets Build Awesome Mobile Apps Using The Simple Way
 */
package andbrain.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ListenerClass(
        targetType = "android.widget.AdapterView<?>",
        setter = "setOnItemSelectedListener",
        lisner = "android.widget.AdapterView.OnItemSelectedListener",
        method = {
                @ListenerMethod(
                        name = "onItemSelected",
                        parameters = {
                                "android.widget.AdapterView<?>",
                                "android.view.View",
                                "int",
                                "long"
                        },
                        mainmethod = "yes"
                ),
                @ListenerMethod(
                        name = "onNothingSelected",
                        parameters = {
                                "android.widget.AdapterView<?>",

                        },
                        mainmethod = "no"
                ),


        }
)

public @interface OnItemSelected {
    int[] value() default {0};
}

/**
 * Copyright (C) 2013-2016 www.andbrain.com
 * Lets Build Awesome Mobile Apps Using The Simple Way
 */
package andbrain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ListenerClass(
        targetType = "android.widget.SeekBar",
        setter = "setOnSeekBarChangeListener",
        lisner = "android.widget.SeekBar.OnSeekBarChangeListener",
        method = {
                @ListenerMethod(
                        name = "onProgressChanged",
                        parameters = {
                                "android.widget.SeekBar",
                                "int",
                                "boolean"
                        },
                        mainmethod = "yes",
                        returnType = "void"

                ),

                @ListenerMethod(
                        name = "onStartTrackingTouch",
                        parameters = {
                                "android.widget.SeekBar",

                        },
                        mainmethod = "no",
                        returnType = "void"
                ),
                @ListenerMethod(
                        name = "onStopTrackingTouch",
                        parameters = "android.widget.SeekBar",
                        returnType = "void",
                        mainmethod = "no"
                )


        }

)
public @interface OnSeekBarChanged {
    int[] value() default {0};
}

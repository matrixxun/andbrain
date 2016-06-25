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
        setter = "setOnEditorActionListener",
        lisner = "android.widget.TextView.OnEditorActionListener",
        method = @ListenerMethod(
                name = "onEditorAction",
                parameters = {
                        "android.widget.TextView",
                        "int",
                        "android.view.KeyEvent"
                },
                mainmethod = "yes",
                returnType = "boolean",
                defaultReturn = "false"
        )
)
public @interface OnEditorAction {

    @IdRes int[] value() default {0};
}

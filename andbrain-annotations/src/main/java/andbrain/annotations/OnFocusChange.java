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
        targetType = "android.view.View",
        setter = "setOnFocusChangeListener",
        lisner = "android.view.View.OnFocusChangeListener",
        method = @ListenerMethod(
                name = "onFocusChange",
                parameters = {
                        "android.view.View",
                        "boolean"
                },
                mainmethod = "yes"
        )
)
public @interface OnFocusChange {
    @IdRes int[] value() default {0};
}

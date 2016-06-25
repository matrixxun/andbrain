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
        setter = "setOnTouchListener",
        lisner = "android.view.View.OnTouchListener",
        method = @ListenerMethod(
                name = "onTouch",
                parameters = {
                        "android.view.View",
                        "android.view.MotionEvent"
                },
                mainmethod = "yes",
                returnType = "boolean",
                defaultReturn = "false"
        )
)
public @interface OnTouch {
    @IdRes int[] value() default {0};
}

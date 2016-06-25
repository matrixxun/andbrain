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


@Retention(CLASS)
@Target(METHOD)
@ListenerClass(
        targetType = "android.view.View",
        setter = "setOnLongClickListener",
        lisner = "android.view.View.OnLongClickListener",
        method = @ListenerMethod(
                name = "onLongClick",
                parameters = {
                        "android.view.View"
                },
                mainmethod = "yes",
                returnType = "boolean",
                defaultReturn = "false"
        )
)
public @interface OnLongClick {
    @IdRes int[] value() default {0};
}

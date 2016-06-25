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
        setter = "setOnClickListener",
        lisner = "android.view.View.OnClickListener",
        method = @ListenerMethod(
                name = "onClick",
                parameters = {"android.view.View"},
                returnType = "void",
                mainmethod = "yes"
        )
)
public @interface OnClick {
    @IdRes int[] value() default {0};
}

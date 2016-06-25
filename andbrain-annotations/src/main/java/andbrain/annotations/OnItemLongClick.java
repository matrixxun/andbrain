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
        targetType = "android.widget.AdapterView<?>",
        setter = "setOnItemLongClickListener",
        lisner = "android.widget.AdapterView.OnItemLongClickListener",
        method = @ListenerMethod(
                name = "onItemLongClick",
                parameters = {
                        "android.widget.AdapterView<?>",
                        "android.view.View",
                        "int",
                        "long"
                },
                mainmethod = "yes",
                returnType = "boolean",
                defaultReturn = "false"
        )
)
public @interface OnItemLongClick {
    @IdRes int[] value() default {0};
}

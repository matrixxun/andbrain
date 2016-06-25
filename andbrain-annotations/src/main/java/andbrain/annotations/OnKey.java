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
        targetType = "android.view.View",
        setter = "setOnKeyListener",
        lisner = "android.view.View.OnKeyListener",
        method = {
                @ListenerMethod(
                        name = "onKey",
                        parameters = {
                                "android.view.View",
                                "int",
                                "android.view.KeyEvent"
                        },
                        mainmethod = "yes",
                        returnType = "boolean"

                )

        }

)
public @interface OnKey {
    int[] value() default {0};
}

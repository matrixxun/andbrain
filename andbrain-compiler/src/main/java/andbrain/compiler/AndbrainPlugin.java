/**
 * Copyright (C) 2013-2016 www.andbrain.com
 * Lets Build Awesome Mobile Apps Using The Simple Way
 */
package andbrain.compiler;


import java.lang.annotation.Annotation;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;




/**
 * Created by andbrain on 6/18/16.
 */
public interface AndbrainPlugin {
        public List <Class<? extends Annotation>> getSupportedAnnotations();
        public void init(Element en, ClassBuilder mClassBuilder, ProcessingEnvironment processingEnv);
}

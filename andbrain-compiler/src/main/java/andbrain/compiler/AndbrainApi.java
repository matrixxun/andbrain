/**
 * Copyright (C) 2013-2016 www.andbrain.com
 * Lets Build Awesome Mobile Apps Using The Simple Way
 */
package andbrain.compiler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static javax.lang.model.element.ElementKind.CLASS;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;
import static javax.tools.Diagnostic.Kind.ERROR;


public class AndbrainApi {

    public ProcessingEnvironment mProcessingEnv;
    public TypeElement TYPE_FRAGMENT;
    public TypeElement TYPE_SUPPORT_FRAGMENT;
    public final String SOURCE_PATH = "resPath";
    public Types typeUtils;
    public Elements elementUtils;

    /**
     * @param processingEnv
     */
    public AndbrainApi(ProcessingEnvironment processingEnv) {

        mProcessingEnv = processingEnv;
        this.elementUtils = mProcessingEnv.getElementUtils();
        this.typeUtils = mProcessingEnv.getTypeUtils();
        this.TYPE_FRAGMENT = this.elementUtils.getTypeElement("android.app.Fragment");
        this.TYPE_SUPPORT_FRAGMENT = this.elementUtils.getTypeElement("android.support.v4.app.Fragment");

    }

    /**
     * @param en
     * @param classExist
     * @return
     */
    @Nullable
    public ClassBuilder getClassGeneratorByName(Set<ClassBuilder> mClassesBuilder,Element en, boolean classExist) {

        for (ClassBuilder mClass : mClassesBuilder) {
            if (en.getEnclosingElement().getSimpleName().toString().equals(mClass.getActivityName())) {
                classExist = true;
                return mClass;
            }
        }


        ClassBuilder mClass;
        mClass = new ClassBuilder();
        mClass.setPackageName(mProcessingEnv.getElementUtils()
                .getPackageOf(en).toString());
        mClass.setActivityName(en.getEnclosingElement()
                .getSimpleName().toString());
        mClass.setElement(en);

        if (isFragmentClass(en.getEnclosingElement(), TYPE_FRAGMENT, TYPE_SUPPORT_FRAGMENT)) {
            mClass.setIsFragment(true);
        }
        classExist = false;
        return mClass;
    }

    /**
     * @param classElement
     * @param fragmentType
     * @param supportFragmentType
     * @return
     */
    public boolean isFragmentClass(Element classElement, TypeElement fragmentType,
                                   TypeElement supportFragmentType) {
        return (fragmentType != null && typeUtils.isSubtype(classElement.asType(),
                fragmentType.asType())) || (supportFragmentType != null && typeUtils.isSubtype(
                classElement.asType(), supportFragmentType.asType()));
    }

    /**
     * @param mGenClass
     * @return
     */
    @NonNull
    public String addGetActivityIfFragment(ClassBuilder mGenClass) {
        if (mGenClass.isFragment()) {
            return "getActivity().";
        }
        return "";
    }

    /**
     * @param element
     * @param error
     * @param args
     */
    public void printError(Element element, String error, Object... args) {
        if (args.length > 0) {
            error = String.format(error, args);
        }
        mProcessingEnv.getMessager().printMessage(ERROR, error, element);
    }

    /**
     * @param element
     * @param annotation
     * @param e
     */
    public void logParsingError(Element element, Class<? extends Annotation> annotation,
                                Exception e) {
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        printError(element, "Unable to parse @%s .\n\n%s", annotation.getSimpleName(), stackTrace);
    }

    /**
     * @param annotation
     * @param text
     * @param element
     * @return
     */
    public boolean isInaccessible(Class<? extends Annotation> annotation,
                                  String text, Element element) {

        boolean error = false;

        TypeElement classOfElement = (TypeElement) element.getEnclosingElement();


        Set<Modifier> modifiers = element.getModifiers();
        if (modifiers.contains(PRIVATE) || modifiers.contains(STATIC)) {

            printError(element, "@%s %s  must not be private or static. (%s.%s)",
                    annotation.getSimpleName(), text, classOfElement.getQualifiedName(),
                    element.getSimpleName());

            error = true;
        }

        if (classOfElement.getModifiers().contains(PRIVATE)) {

            printError(classOfElement, "@%s %s may only be contained in public classes. (%s.%s)",
                    annotation.getSimpleName(), text, classOfElement.getQualifiedName(),
                    element.getSimpleName());

            error = true;
        }

        if (classOfElement.getKind() != CLASS) {

            printError(classOfElement, "@%s %s may only be contained in classes. (%s.%s)",
                    annotation.getSimpleName(), text, classOfElement.getQualifiedName(),
                    element.getSimpleName());

            error = true;
        }

        return error;
    }
}

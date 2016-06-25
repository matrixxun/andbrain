/**
 * Copyright (C) 2013-2016 www.andbrain.com
 * Lets Build Awesome Mobile Apps Using The Simple Way
 */
package andbrain.compiler;


import com.google.auto.service.AutoService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import andbrain.annotations.Background;
import andbrain.annotations.UIThread;


@SupportedOptions("resPath")
@AutoService(Processor.class)
public final class AndbrainProcessor extends AbstractProcessor {


    private AndbrainCore mCore;
    private AndbrainApi mApi;


    @Override
    public Set<String> getSupportedOptions() {

        Set<String> supportedOptions = new LinkedHashSet<String>();
        supportedOptions.add(mApi.SOURCE_PATH);
        return supportedOptions;
    }


    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        mCore = new AndbrainCore(processingEnv);
        mApi = new AndbrainApi(processingEnv);
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();

        types.add(Background.class.getCanonicalName());
        types.add(UIThread.class.getCanonicalName());

        for (Class<? extends Annotation> events : mCore.EVENTS) {
            types.add(events.getCanonicalName());
        }
        List<String> plug = getListOfPluginsNames();
        for (String plugname : plug) {

            for (Class<? extends Annotation> events : getSupportPluginsAnnotations(plugname)) {
                types.add(events.getCanonicalName());
            }
        }
        return types;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> elements, RoundEnvironment env) {

        List<String> plug = getListOfPluginsNames();

        for (String plugname : plug) {
            initPlugin(plugname, env);
        }


        String sourcePath = processingEnv.getOptions().get(mApi.SOURCE_PATH);
        if (sourcePath != null) {

            mCore.parseAllElement(env);

            for (ClassBuilder mClassBuilder : mCore.mClassesBuilder) {

                try {

                    mClassBuilder.Build(processingEnv);

                } catch (IOException e) {

                    mApi.printError(mClassBuilder.getElement(), "%s ",
                            e.getMessage());

                }

            }

            mCore.mClassesBuilder.clear();

        } else {

            mApi.printError(null, "You Must Add This Line Of Code To build.gardle File: \n " +
                    "       resPath variant.outputs[0]?.processResources?.resDir\n");
        }
        return false;
    }

    /**
     * Get Support Plugins Annotations
     *
     * @param pluginName
     * @return
     */
    public List<Class<? extends Annotation>> getSupportPluginsAnnotations(String pluginName) {
        String sClassName = pluginName + ".Main";
        try {

            Class pluginMainClass = Class.forName(sClassName);
            java.lang.Object obj = pluginMainClass.newInstance();

            Method method = pluginMainClass.getDeclaredMethod("getSupportedAnnotations");
            return (List<Class<? extends Annotation>>) method.invoke(obj);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Start Plugin
     *
     * @param pluginName
     * @param env
     */
    public void initPlugin(String pluginName, RoundEnvironment env) {

        String sClassName = pluginName + ".Main";
        try {
            Class pluginMainClass = Class.forName(sClassName);
            java.lang.Object obj = pluginMainClass.newInstance();
            Class[] cArg = new Class[3];
            cArg[0] = Element.class;
            cArg[1] = ClassBuilder.class;
            cArg[2] = ProcessingEnvironment.class;


            for (Class<? extends Annotation> events : getSupportPluginsAnnotations(pluginName)) {

                for (Element en : env.getElementsAnnotatedWith(events)) {
                    try {
                        boolean classExist = false;
                        ClassBuilder mGenClass = mApi.getClassGeneratorByName(mCore.mClassesBuilder,en, classExist);
                        Method method = pluginMainClass.getDeclaredMethod("init", cArg);
                        method.invoke(obj, en, mGenClass, processingEnv);

                        if (!classExist) {
                            mCore.mClassesBuilder.add(mGenClass);
                        }

                    } catch (Exception e) {

                    }
                }
            }

        } catch (ClassNotFoundException e) {

        } catch (Exception e) {

        }


    }

    /**
     * Get List Of Plugins Names
     *
     * @return
     */
    public List<String> getListOfPluginsNames() {
        List<String> pluginsNames = new ArrayList<String>();

        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("META-INF/PluginsNames.txt");
            if (in != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;

                while ((line = reader.readLine()) != null) {

                    if (!line.trim().equals(""))
                        pluginsNames.add(line);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return pluginsNames;
    }


}

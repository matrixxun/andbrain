/**
 * Copyright (C) 2013-2016 www.andbrain.com
 * Lets Build Awesome Mobile Apps Using The Simple Way
 */
package andbrain.compiler;



import com.google.auto.common.SuperficialValidation;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

import javax.lang.model.element.VariableElement;


import andbrain.annotations.AfterTextChanged;
import andbrain.annotations.Background;
import andbrain.annotations.BeforeTextChanged;
import andbrain.annotations.ListenerClass;
import andbrain.annotations.ListenerMethod;
import andbrain.annotations.OnCheckRadioGroup;
import andbrain.annotations.OnCheckedChanged;
import andbrain.annotations.OnClick;
import andbrain.annotations.OnEditorAction;
import andbrain.annotations.OnFocusChange;
import andbrain.annotations.OnItemClick;
import andbrain.annotations.OnItemLongClick;
import andbrain.annotations.OnItemSelected;
import andbrain.annotations.OnKey;
import andbrain.annotations.OnLongClick;
import andbrain.annotations.OnSeekBarChanged;
import andbrain.annotations.OnStartTrackingTouch;
import andbrain.annotations.OnStopTrackingTouch;
import andbrain.annotations.OnTextChanged;
import andbrain.annotations.OnTouch;
import andbrain.annotations.UIThread;


public class AndbrainCore {

    ProcessingEnvironment mProcessingEnv;
    AndbrainApi mAnbrainApi;

    /**
     *
     * @param processingEnv
     */
    AndbrainCore(ProcessingEnvironment processingEnv) {

        mProcessingEnv = processingEnv;
        mAnbrainApi = new AndbrainApi(processingEnv);

    }

    public static final List<Class<? extends Annotation>> EVENTS = Arrays.asList(
            OnCheckRadioGroup.class,
            OnClick.class,
            OnEditorAction.class,
            OnFocusChange.class,
            OnLongClick.class,
            OnTouch.class,
            OnCheckedChanged.class,
            OnItemClick.class,
            OnItemLongClick.class,
            OnItemSelected.class,
            OnTextChanged.class,
            AfterTextChanged.class,
            BeforeTextChanged.class,
            OnKey.class,
            OnSeekBarChanged.class,
            OnStartTrackingTouch.class,
            OnStopTrackingTouch.class

    );
    public Set<ClassBuilder> mClassesBuilder = new LinkedHashSet<>();


    String parMethod = "", parPass = "";

    /**
     *
     * @param env
     */
    public void parseAllElement(RoundEnvironment env) {

        // Process each @Background element.

        for (Element en : env.getElementsAnnotatedWith(Background.class)) {
            if (!SuperficialValidation.validateElement(en) || en.getKind() != ElementKind.METHOD)
                continue;
            try {
                processBackgroundTask(en);
            } catch (Exception e) {
                mAnbrainApi.logParsingError(en, Background.class, e);
            }

        }

        // Process each @UiThread element.

        for (Element en : env.getElementsAnnotatedWith(UIThread.class)) {
            //   if (!SuperficialValidation.validateElement(en)||en.getKind() != ElementKind.METHOD)continue;

            try {
                processUiThreadTask(en);
            } catch (Exception e) {
                mAnbrainApi.logParsingError(en, UIThread.class, e);
            }
        }

        // Process each @Event element.
        for (Class<? extends Annotation> events : EVENTS) {

            for (Element en : env.getElementsAnnotatedWith(events)) {
                //   if (!SuperficialValidation.validateElement(en)||en.getKind() != ElementKind.METHOD) continue;
                try {
                    processEvents(en, events);
                } catch (Exception e) {
                    mAnbrainApi.logParsingError(en, events, e);
                }
            }
        }


    }

    /**
     *
     * @param en
     */
    public void processBackgroundTask(Element en) {

        if (mAnbrainApi.isInaccessible(Background.class, "methods", en)) return;

        boolean mClassExist = false;
        ClassBuilder mGenClass = mAnbrainApi.getClassGeneratorByName(mClassesBuilder,en, mClassExist);

        int priority = en.getAnnotation(Background.class).value();

        ExecutableElement executableElement = (ExecutableElement) en;
        String name = executableElement.getSimpleName().toString();
        List<? extends VariableElement> parameters = executableElement.getParameters();
        getMethodParameters(parameters);
        mGenClass.addPackage("import andbrain.annotations.MyTask;");

        if (priority == -1) {

            mGenClass.addMethod(
                    "  public void " + name + "(" + parMethod + "){\n" +
                            "   MyTask.doInBackground(new MyTask.OnBackground() {\n" +
                            "     @Override\n" +
                            "     public void doOnBackground() {\n" +
                            "        target2." + name + "(" + parPass + "); \n" +

                            "     }});\n" +
                            " } ");

        } else {

            mGenClass.addPackage("import java.util.concurrent.ExecutorService;");
            mGenClass.addPackage("import java.util.concurrent.Executors;");
            mGenClass.addPackage("import java.util.Map;");
            mGenClass.addPackage("import java.util.TreeMap;");
            mGenClass.addPackage("import java.util.Comparator;");
            mGenClass.addMethod("  public void runTasksSequentially(){\n" +
                    "    ExecutorService executorService = Executors.newSingleThreadExecutor();\n" +
                    "    for (Map.Entry<Integer, MyTask.OnBackground> entry : mTasks.entrySet()) {\n" +
                    "    MyTask.doInBackground(entry.getValue(), executorService);\n" +
                    "" +
                    "}}\n");
            mGenClass.addFields("   Map<Integer, MyTask.OnBackground> mTasks = new TreeMap<Integer, MyTask.OnBackground>(new Comparator<Integer>(){" +
                    "                    \n" +

                    "                        @Override\n" +
                    "                        public int compare(Integer first, Integer second)\n" +
                    "                        {" +
                    "\n" +
                    "                            if (false){\n" +
                    "                                return second.compareTo(first);\n" +
                    "                            } else{\n" +
                    "                             " + "return first.compareTo(second);\n" +
                    "                            }\n" +
                    "                        }\n" +
                    "                    });");

            mGenClass.addCode("  mTasks.put(" + Integer.toString(priority) + ",new MyTask.OnBackground() {\n" +
                    "    @Override\n" +
                    "    public void doOnBackground() {\n" +
                    "     target2." + name + "(); \n" +
                    "     }" +
                    "    });\n");


        }
        if (!mClassExist) mClassesBuilder.add(mGenClass);


    }

    /**
     *
     * @param en
     */
    public void processUiThreadTask(Element en) {
        if (mAnbrainApi.isInaccessible(UIThread.class, "methods", en)) return;
        boolean mClassExist = false;
        ClassBuilder mGenClass = mAnbrainApi.getClassGeneratorByName(mClassesBuilder,en, mClassExist);

        ExecutableElement uiExecutableElement = (ExecutableElement) en;
        String uiName = uiExecutableElement.getSimpleName().toString();
        List<? extends VariableElement> parameters = uiExecutableElement.getParameters();
        getMethodParameters(parameters);

        mGenClass.addPackage("import andbrain.annotations.MyTask;");
        mGenClass.addMethod(
                "  public void " + uiName + "(" + parMethod + "){\n" +
                        "   MyTask.doOnMainThread(new MyTask.OnMainThread() {\n" +
                        "   @Override\n" +
                        "   public void doInUIThread() {\n" +
                        "       target2." + uiName + "(" + parPass + ");\n" +
                        "    }\n" +
                        "  });\n" +
                        " }");


        if (!mClassExist) mClassesBuilder.add(mGenClass);
    }

    int addToName = 0;

    /**
     *
     * @param en
     * @param events
     */
    public void processEvents(Element en, Class<? extends Annotation> events) {

        if (mAnbrainApi.isInaccessible(events, "methods", en)) return;

        String eventCode = "";
        boolean mClassExist = false;
        ClassBuilder mGenClass = mAnbrainApi.getClassGeneratorByName(mClassesBuilder,en, mClassExist);


        ExecutableElement executableElement = (ExecutableElement) en;
        String name = executableElement.getSimpleName().toString();

        Annotation annotation = en.getAnnotation(events);

        Class<? extends Annotation> annotationType = annotation
                .annotationType();
        ListenerClass eventAnnotation = annotationType
                .getAnnotation(ListenerClass.class);
        String eventSetter = eventAnnotation.setter();
        String targetType = eventAnnotation.targetType();
        String lisner = eventAnnotation.lisner();

        ListenerMethod[] methods = eventAnnotation.method();


        try {
            Method annotationValue = annotationType.getDeclaredMethod("value");

            int[] ids = (int[]) annotationValue.invoke(annotation);
            if (ids[0] != 0) {
                for (int viewId : ids) {

                    eventCode = eventCode + targetType + " view" + addToName + viewId + " = (" + targetType + ")target." + mAnbrainApi.addGetActivityIfFragment(mGenClass) + "findViewById(" + viewId + ");\n" +
                            "  view" + addToName + viewId + "." + eventSetter + "(new " + lisner + "() {\n";

                    List<? extends VariableElement> parameters = executableElement.getParameters();

                    for (ListenerMethod method : methods) {

                        String nameMethod = method.name();
                        String[] paramaterMetho = method.parameters();
                        String[] paramatersToadd = new String[paramaterMetho.length];
                        String[] paramatersIsOK = new String[paramaterMetho.length];

                        int t = 0;
                        for (String p : paramatersIsOK) {
                            paramatersIsOK[t] = "no";
                            t++;
                        }
                        String parmater = "";
                        int i5 = -1;
                        int parNum = 0;
                        for (String par : paramaterMetho) {


                            String to[] = par.split("\\.");

                            if (!parmater.equals("")) {
                                parmater = parmater + ",";
                            }

                            parmater = parmater + " " + par + " " + to[to.length - 1].intern().substring(0, 3).toLowerCase() + parNum;
                            i5 = getTypeIndext(parameters, par, paramatersIsOK);
                            if (i5 != -1) {
                                if (!paramatersIsOK[i5].contains("ok")) {
                                    paramatersToadd[i5] = to[to.length - 1].intern().substring(0, 3).toLowerCase() + parNum;
                                    paramatersIsOK[i5] = "ok";
                                }
                            }


                            parNum++;

                        }

                        String passNewPar = "";
                        int i2 = 0;
                        for (String pass : paramatersToadd) {
                            if (pass != null) {
                                if (i2 - 1 < paramatersToadd.length && !passNewPar.equals("")) {
                                    passNewPar = passNewPar + " ,";
                                }
                                passNewPar = passNewPar + pass;
                                i2++;

                            }

                        }

                        String returnMethod = method.returnType();

                        eventCode = eventCode + "       @Override\n";

                        eventCode = eventCode + "       public " + returnMethod + " " + nameMethod + "(" + parmater + ") {\n";

                        if (method.mainmethod().contains("yes")) {

                            eventCode = eventCode + "        target." + name + "(" + passNewPar + ");\n";

                        }

                        if (returnMethod.contains("boolean")) {
                            eventCode = eventCode + "              return true;\n";
                        }
                        eventCode = eventCode + "   }";
                    }
                    eventCode = eventCode + "});\n\n";
                    addToName++;
                }
            } else {
                mAnbrainApi.printError(en, "@%s must specify at least one ID.\n\n", events.getSimpleName());
                return;
            }
        } catch (IllegalAccessException e) {
            mAnbrainApi.printError(en, "@%s .\n\n", e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            mAnbrainApi.printError(en, "@%s .\n\n", e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            mAnbrainApi.printError(en, "@%s .\n\n", e.getMessage());
            e.printStackTrace();
        }

        mGenClass.addEventCode(eventCode);


        if (!mClassExist) mClassesBuilder.add(mGenClass);
    }




    /**
     *
     * @param parameters
     * @param parOriginal
     * @param p
     * @return
     */
    public int getTypeIndext(List<? extends VariableElement> parameters, String parOriginal, String[] p) {
        int i = 0;
        for (VariableElement ve : parameters) {
            if (ve.asType().toString().equals(parOriginal)) {
                if (!p[i].contains("ok")) {
                    return i;
                }

            }
            i++;
        }
        return -1;
    }

    /**
     *
     * @param parameters
     */
    public void getMethodParameters(List<? extends VariableElement> parameters) {

        parMethod = "";
        parPass = "";

        for (VariableElement ve : parameters) {
            if (!parMethod.equals("")) {
                parMethod = parMethod + ",";
                parPass = parPass + ",";
            }
            parMethod = parMethod + "final " + ve.asType() + " " + ve.toString();
            parPass = parPass + ve.toString();

        }


    }


}

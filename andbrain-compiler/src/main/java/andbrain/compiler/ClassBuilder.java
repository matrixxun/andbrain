/**
 * Copyright (C) 2013-2016 www.andbrain.com
 * Lets Build Awesome Mobile Apps Using The Simple Way
 */
package andbrain.compiler;

import java.io.BufferedWriter;
import java.io.IOException;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

public class ClassBuilder {

    private boolean _isFragment = false;
    private String _Comment = "";
    private String _Package = "";
    private String _Fields = "";
    private String _Code = "";
    private String _Method = "";
    private String _packageName = "";
    private String _ActivityName = "";
    private String _EventCode = "";
    private String _ContentView = "";
    private Element _Element;

    /**
     *
     * @param coment
     */
    public void addComment(String coment) {
        _Comment = _Comment + "//"+coment + "\n";
    }

    /**
     *
     * @param pack
     */
    public void addPackage(String pack) {
        if (!_Package.contains(pack)) {
            _Package = _Package + pack + "\n";
        }

    }

    /**
     *
     * @param field
     */
    public void addFields(String field) {
        if (!_Fields.contains(field)) {
            _Fields = _Fields + " " + field + "\n";
        }
    }

    /**
     *
     * @param contentView
     */
    public void addContentView(String contentView) {

        _ContentView = "   target.setContentView(R." + contentView.replace("/", ".") + ");";
    }

    /**
     *
     * @param code
     */
    public void addCode(String code) {
        _Code = _Code + "   " + code + "\n";
    }

    /**
     *
     * @param method
     */
    public void addMethod(String method) {
        if (!_Method.contains(method)) {
            _Method = _Method + method + "\n";
        }
    }

    /**
     *
     * @param packageName
     */
    public void setPackageName(String packageName) {
        _packageName = packageName;
    }

    /**
     *
     * @param element
     */
    public void setElement(Element element) {
        _Element = element;
    }

    /**
     *
     * @return
     */
    public Element getElement() {
        return _Element;
    }

    /**
     *
     * @param activityname
     */
    public void setActivityName(String activityname) {
        _ActivityName = activityname;
    }

    /**
     *
     * @param isFragment
     */
    public void setIsFragment(boolean isFragment) {
        _isFragment = isFragment;
    }

    /**
     *
     * @return
     */
    public boolean isFragment() {
        return _isFragment;
    }

    /**
     *
     * @param eventCod
     */
    public void addEventCode(String eventCod) {
        _EventCode = _EventCode + "  " + eventCod;
    }

    /**
     *
     * @return
     */
    public String getActivityName() {
        return _ActivityName;
    }

    /**
     *
     */
    public void rest() {
        _Comment = "";
        _Package = "";
        _Fields = "";
        _Code = "";
        _Method = "";
        _packageName = "";
        _ActivityName = "";
        _EventCode = "";
    }

    /**
     *
     * @param processingEnv
     * @throws IOException
     */
    public void Build(ProcessingEnvironment processingEnv) throws IOException {


        JavaFileObject jfo = processingEnv.getFiler().createSourceFile(_packageName + "$andbrain."  + _ActivityName+ "$");

        BufferedWriter bw = new BufferedWriter(jfo.openWriter());
        bw.append( _Comment + "\n" +
                "package " + _packageName + ";\n" +
                _Package + "\n" +
                "  public class  "  + _ActivityName+ "$" + "{" +
                "\n" + "\n" +
                _Fields +
                "  private " + _ActivityName + " target2;\n" +
                "\n" +
                "  public "  + _ActivityName+ "$" + "(final " + _ActivityName + " target) {\n" +
                "\n" +
                "   target2=target;\n" +
                _ContentView +
                "\n" +
                _Code +
                "\n" +
                _EventCode +
                "\n" +
                "  }\n" +
               _Method +
                "\n" +

                "\n" +
                "}");
        bw.close();


    }

}

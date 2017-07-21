package net.yuanjin.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.w3c.dom.Text;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 *  Created by wzq on 2017/7/11.
 */

public class UKJSEngine {

    private Class clazz;
    private String allFunctions ="";
    private UKJSEngineListener ukjsEngineListener;

    public UKJSEngine(UKJSEngineListener listener) {
        this.clazz = UKJSEngine.class;
        this.ukjsEngineListener = listener;
        allFunctions = String.format(getAllFunctions(), clazz.getName());//生成js语法
//        allFunctions = "" +
//                " app = new App();\n" +
//                "\n" +
//                " function App(){\n" +
//                "     this.showObject = showObjectValue;\n" +
//                "\n" +
//                "     var ScriptAPI = java.lang.Class.forName(\"net.yuanjin.utils.UKJSEngine\", true, javaLoader);\n" +
//                "     var method_getRowValue = ScriptAPI.getMethod(\"getRowValue\",[java.lang.String,java.lang.Object]);\n" +
//                "     function getRowValue(param0,param1){\n" +
//                "        var retStr = method_getRowValue.invoke(javaContext,param0,param1);\n" +
//                "        var ret = {} ;\n" +
//                "        eval('ret='+retStr);\n" +
//                "        return ret;\n" +
//                "     }\n" +
//                "     var method_getTableHeader = ScriptAPI.getMethod(\"getTableHeader\",[java.lang.String,java.lang.Object]);\n" +
//                "     function getTableHeader(param0,param1){\n" +
//                "        return method_getTableHeader.invoke(javaContext,param0,param1);\n" +
//                "     }\n" +
//                "     var method_getTableRows = ScriptAPI.getMethod(\"getTableRows\",[java.lang.String]);\n" +
//
//
//                "     function getTableRows(param0){\n" +
//                "        return method_getTableRows.invoke(javaContext,param0);\n" +
//                "     }\n" +
//                "     var method_getValue = ScriptAPI.getMethod(\"getValue\",[java.lang.String]);\n" +
//                "     function getValue(param0){\n" +
//                "        return method_getValue.invoke(javaContext,param0);\n" +
//                "     }\n" +
//                "     var method_setRowValue = ScriptAPI.getMethod(\"setRowValue\",[java.lang.String,java.lang.Object,java.lang.Object]);\n" +
//                "     function setRowValue(param0,param1,param2){\n" +
//                "         method_setRowValue.invoke(javaContext,param0,param1,param2);\n" +
//                "     }\n" +
//                "     var method_setValue = ScriptAPI.getMethod(\"setValue\",[java.lang.Object,java.lang.Object]);\n" +
//                "     function setValue(param0,param1){\n" +
//                "         method_setValue.invoke(javaContext,param0,param1);\n" +
//                "     }\n" +
//                "     var method_showObjectValue = ScriptAPI.getMethod(\"showObjectValue\",[java.lang.Object,java.lang.Object]);\n" +
//                "     function showObjectValue(param0,param1){\n" +
//                "         method_showObjectValue.invoke(javaContext,param0,param1);\n" +
//                "     }\n" +
//                "\n" +
//                " }";

    }

    /**
     * 指定可选数据源
     * @param keyStr 字段key
     * @param value
     */
    @UKJSAnnotation
    public void designateDataSource(String keyStr,Object value){
        if (value == null) return;
        ArrayList list = new Gson().fromJson(new Gson().toJson(value),ArrayList.class);
    }

    /**
     * 指定不可选数据源
     * @param keyStr 字段key
     * @param value
     */
    @UKJSAnnotation
    public void designateFilterDataSource(String keyStr,Object value){
        if (value == null) return;
    }

    /**
     * 仅用于调试 - 显示对象的值
     * @param keyStr
     * @param value
     */
    @UKJSAnnotation(returnObject = false)
    public void showObjectValue(Object keyStr,Object value){
        if (value == null) Log.i(this.getClass().getSimpleName(),keyStr.toString() + " : "+ "该值为空");
        //Log.i(this.getClass().getSimpleName(),keyStr.toString() + " ------> " + new Gson().toJson(value));
    }

    /**
     * 获取指定字段的值
     *
     * @param keyStr
     * @return
     */
    @UKJSAnnotation(returnObject = false)
    public String getValue(String keyStr) {

        if (ukjsEngineListener != null){
            return ukjsEngineListener.getValue(keyStr);
        }
        return "";
    }

    /**
     * 给指定字段设置值
     *
     * @param keyStr
     * @param o
     */
    @UKJSAnnotation(returnObject = false)
    public void setValue(Object keyStr, Object o) {
        Log.i(this.getClass().getSimpleName(),keyStr.toString() + " ------> " + o.toString());
        if (ukjsEngineListener != null){
            ukjsEngineListener.setValue(keyStr,o);
        }
    }

    /**
     * 获取表格行数
     *
     * @param sheetName ： 表格名称 ，即 表格字段的 key
     */
    @UKJSAnnotation(returnObject = false)
    public int getTableRows(String sheetName) {
        if (ukjsEngineListener != null){
            return ukjsEngineListener.getTableRows(sheetName);
        }
        return 0;
    }

    /**
     * 获取表格的值
     *
     * @param sheetName ： 表格名称 ，即 表格字段的 key
     * @param rowIndex  : 指定行，从0开始 ； -1 则返回所有值
     */
    @UKJSAnnotation(returnObject = true)
    public String getRowValue(String sheetName, Object rowIndex) {
        if (ukjsEngineListener !=null){
            return ukjsEngineListener.getRowValue(sheetName,rowIndex);
        }
        return "";
    }

    /**
     * 设置表格中某行的值
     *
     * @param sheetName 表格名称
     * @param rowIndex  行index
     * @param rowValue  该行的值
     */
    @UKJSAnnotation(returnObject = false)
    public void setRowValue(String sheetName, Object rowIndex, Object rowValue) {
        if (ukjsEngineListener != null){
            ukjsEngineListener.setRowValue(sheetName,rowIndex,rowValue);
        }
    }

    /**
     * 获取表单表头
     *
     * @param sheetName 表名 - 即表格字段key
     * @param rowIndex  指定项
     * @return
     */
    @UKJSAnnotation(returnObject = false)
    public Object[] getTableHeader(String sheetName, Object rowIndex) {
        if (ukjsEngineListener != null){
            return ukjsEngineListener.getTableHeader(sheetName,rowIndex);
        }
        return null;
    }

    /**
     * 执行JS
     * @param js  js执行代码 eg: "var v1 = getValue('Ta');setValue(‘key’，v1);"
     */
    public void runScript(String js) {
        js = allFunctions + "\r\n" + js;
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);

        try {
            Scriptable scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope, "javaContext", org.mozilla.javascript.Context.javaToJS(this, scope));
            ScriptableObject.putProperty(scope, "javaLoader", org.mozilla.javascript.Context.javaToJS(clazz.getClassLoader(), scope));

            rhino.evaluateString(scope, js, clazz.getSimpleName(), 1, null);
        } finally {
            org.mozilla.javascript.Context.exit();
        }
    }

    /**
     * JS解析引擎回调接口
     */
    public interface UKJSEngineListener{

        Object[] getTableHeader(String sheetName, Object rowIndex);
        void setRowValue(String sheetName, Object rowIndex, Object rowValue);
        String getRowValue(String sheetName, Object rowIndex);
        int getTableRows(String sheetName);
        void setValue(Object keyStr, Object o);
        String getValue(String keyStr);
    }

    /**
     * 引擎注解
     */
    @Target(value = ElementType.METHOD)
    @Retention(value = RetentionPolicy.RUNTIME)
    public @interface UKJSAnnotation {
        boolean returnObject() default false;//是否返回对象
    }

    /**
     * 通过注解自动生成js方法语句
     */
    private String getAllFunctions() {
        String appStr =
                " app = new App();\n" +
                " function App(){\n";
        String funcStr = "\n var ScriptAPI = java.lang.Class.forName(\"%s\", true, javaLoader);\n";
        Class cls = this.getClass();
        for (Method method : cls.getDeclaredMethods()) {
            UKJSAnnotation an = method.getAnnotation(UKJSAnnotation.class);
            if (an == null) continue;
            String functionName = method.getName();
            appStr = appStr + String.format("    this.%s = %s;\n",functionName,functionName);

            String paramsTypeString = "";//获取function的参数类型
            String paramsNameString = "";//获取function的参数名称
            String paramsNameInvokeString = "";
            Class[] parmTypeArray = method.getParameterTypes();
            if (parmTypeArray != null && parmTypeArray.length > 0) {
                String[] parmStrArray = new String[parmTypeArray.length];
                String[] parmNameArray = new String[parmTypeArray.length];
                for (int i = 0; i < parmTypeArray.length; i++) {
                    parmStrArray[i] = parmTypeArray[i].getName();
                    parmNameArray[i] = "param" + i;
                }
                paramsTypeString = String.format(",[%s]", TextUtils.join(",", parmStrArray));
                paramsNameString = TextUtils.join(",", parmNameArray);
                paramsNameInvokeString = "," + paramsNameString;
            }

            Class returnType = method.getReturnType();
            String returnStr = returnType.getSimpleName().equals("void") ? "" : "return";//是否有返回值

            String methodStr = String.format(" var method_%s = ScriptAPI.getMethod(\"%s\"%s);\n", functionName, functionName, paramsTypeString);
            String functionStr = "";
            if (an.returnObject()) {//返回对象
                functionStr = String.format(
                        " function %s(%s){\n" +
                                "    var retStr = method_%s.invoke(javaContext%s);\n" +
                                "    var ret = {} ;\n" +
                                "    eval('ret='+retStr);\n" +
                                "    return ret;\n" +
                                " }\n", functionName, paramsNameString, functionName, paramsNameInvokeString);
            } else {//非返回对象
                functionStr = String.format(
                        " function %s(%s){\n" +
                                "    %s method_%s.invoke(javaContext%s);\n" +
                                " }\n", functionName, paramsNameString, returnStr, functionName, paramsNameInvokeString);
            }
            funcStr = funcStr + methodStr + functionStr;
        }
        appStr = appStr + funcStr + "\n}";
        return appStr;
    }

/**

 app = new App();
 function App(){
    this.showObject = showObjectValue;
 }

 var ScriptAPI = java.lang.Class.forName("%s", true, javaLoader);
 var methodGetValue=  ScriptAPI.getMethod("getValue", [java.lang.String]);
 function getValue(key) {
 return  methodGetValue.invoke(javaContext,key);
 }
 var methodSetValue=ScriptAPI.getMethod("setValue",[java.lang.Object,java.lang.Object]);
 function setValue(key,value) {
 methodSetValue.invoke(javaContext,key,value);
 }

 var method_getTableRows = ScriptAPI.getMethod("getTableRows",[java.lang.String]);
 function getTableRows(sheetName){
 method_getTableRows.invoke(javaContext,sheetName);
 }

 var method_getRowValue = ScriptAPI.getMethod("getRowValue",[java.lang.String,java.lang.Object]);
 function getRowValue(sheetName,rowIndex){
 var retStr = method_getRowValue.invoke(javaContext,sheetName,rowIndex);
 var ret = {} ;
 eval('ret='+retStr);
 return ret;
 }

 var method_setRowValue = ScriptAPI.getMethod("setRowValue",[java.lang.String,java.lang.Object,java.lang.Object]);
 function setRowValue(sheetName,rowIndex,rowValue){
 method_setRowValue.invoke(javaContext,sheetName,rowIndex,rowValue);
 }

 var method_getTableHeader = ScriptAPI.getMethod("getTableHeader",[java.lang.String,java.lang.Object]);
 function getTableHeader(sheetName,rowIndex){
 return method_getTableHeader.invoke(javaContext,sheetName,rowIndex);
 }

 */

/** --- js语句

 var rowValue = getRowValue('tab',0);
 if(rowValue.length > 0){
 var rowObj = rowValue[0];
 rowObj.pric = 1000;

 setRowValue('tab',0,rowObj);
 }

 --求和
 sum = sum + (+rowValue[i].pric);
 sum2 = sum2 + parseFloat(rowValue[i].pric);

 */
}


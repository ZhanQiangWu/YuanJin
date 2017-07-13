package net.yuanjin.utils;

import android.app.Activity;

import net.yuanjin.mytest.ConnectWithJS.JSActivity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 *  JS解析工具
 *  Created by wzq on 2017/7/11.
 */

public class JSUtils {

    private static final String allFunctions =
            "var ScriptAPI = java.lang.Class.forName(\"" + JSActivity.class.getName() + "\", true, javaLoader);"+
            "var methodGetValue=  ScriptAPI.getMethod(\"getValueByKey\", [java.lang.String]);" +
            "function getValue(key) { return  methodGetValue.invoke(javaContext,key);}\r\n"+
            "var methodSetValue=ScriptAPI.getMethod(\"setValueByKey\",[java.lang.String]);\r\n"+
            "function setValue(val) { methodSetValue.invoke(javaContext,val);}\r\n";


    /**
     *  执行JS
     * @param js js代码
     * @param functionName js方法名称
     * @param functionParams js方法参数
     * @return
     */
    public static void runScript(String js, Context context, Class clazz, String functionName, Object[] functionParams){
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);

        try{
            Scriptable scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope, "javaContext", org.mozilla.javascript.Context.javaToJS(context, scope));
            ScriptableObject.putProperty(scope, "javaLoader", org.mozilla.javascript.Context.javaToJS(clazz.getClassLoader(), scope));

            rhino.evaluateString(scope, js, clazz.getSimpleName(), 1, null);


        }finally {
            org.mozilla.javascript.Context.exit();
        }
    }
}

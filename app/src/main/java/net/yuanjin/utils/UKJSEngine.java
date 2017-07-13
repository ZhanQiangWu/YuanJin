package net.yuanjin.utils;

import android.content.Context;
import android.widget.TextView;

import net.yuanjin.R;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.Map;

/**
 *  Created by wzq on 2017/7/11.
 */

public class UKJSEngine {

    private Class clazz;
    private Map map;

    public UKJSEngine(Context context, Class clazz,Map map){
        this.clazz = UKJSEngine.class;
        this.map = map;

        init();
    }

    private void init() {
        allFunctions = String.format(allFunctions,clazz.getName());
    }

    private String allFunctions = "var ScriptAPI = java.lang.Class.forName(\"%s\", true, javaLoader);"+
            "var methodGetValue=  ScriptAPI.getMethod(\"getValueByKey\", [java.lang.String]);" +
            "function getValue(key) { return  methodGetValue.invoke(javaContext,key);}\r\n"+
            "var methodSetValue=ScriptAPI.getMethod(\"setValueByKey\",[java.lang.String]);\r\n"+
            "function setValue(val) { methodSetValue.invoke(javaContext,val);}\r\n";

    public  Object getValueByKey(String keyStr){
        if (map.get(keyStr) != null){
            return map.get(keyStr);
        }else {
            return "";
        }
    }

    public  void setValueByKey(String keyStr){
        //map.put(keyStr,sValue);
    }

//    public  void setValueByKey(String keyStr,String sValue){
//        map.put(keyStr,sValue);
//    }

    /**
     *  执行JS
     * @param js js代码 eg: "var v1 = getValue('Ta');setValue(v1);"
     * @param functionName js方法名称
     * @param functionParams js方法参数
     * @return
     */
    public void runScript(String js, String functionName, Object[] functionParams){
        js = allFunctions +"\r\n"+js;
        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);

        try{
            Scriptable scope = rhino.initStandardObjects();

            ScriptableObject.putProperty(scope, "javaContext", org.mozilla.javascript.Context.javaToJS(this, scope));
            ScriptableObject.putProperty(scope, "javaLoader", org.mozilla.javascript.Context.javaToJS(clazz.getClassLoader(), scope));

            rhino.evaluateString(scope, js, clazz.getSimpleName(), 1, null);
        }finally {
            org.mozilla.javascript.Context.exit();
        }
    }
}

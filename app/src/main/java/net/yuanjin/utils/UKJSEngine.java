package net.yuanjin.utils;

import android.text.TextUtils;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.w3c.dom.Text;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 *  Created by wzq on 2017/7/11.
 */

public class UKJSEngine {

    private Class clazz;
    private Map map;
//    private FormFieldContainer formFieldContainer;

    public UKJSEngine(){
        this.clazz= UKJSEngine.class;
        init();
    }

    private void init() {
        allFunctions = String.format(getAllFunctions(),clazz.getName());
    }

    private String allFunctions =
            " var ScriptAPI = java.lang.Class.forName(\"%s\", true, javaLoader);\n" +
                    " var methodGetValue=  ScriptAPI.getMethod(\"getValueByKey\", [java.lang.String]);\n" +
                    " function getValue(key) {\n" +
                    "    return  methodGetValue.invoke(javaContext,key);\n" +
                    " }\n" +
                    " var methodSetValue=ScriptAPI.getMethod(\"setValueByKey\",[java.lang.Object,java.lang.Object]);\n" +
                    " function setValue(key,value) {\n" +
                    "    methodSetValue.invoke(javaContext,key,value);\n" +
                    " }\n" +
                    "\n" +
                    " var method_getTableRows = ScriptAPI.getMethod(\"getTableRows\",[java.lang.String]);\n" +
                    " function getTableRows(sheetName){\n" +
                    "    method_getTableRows.invoke(javaContext,sheetName);\n" +
                    " }\n" +
                    "\n" +
                    " var method_getRowValue = ScriptAPI.getMethod(\"getRowValue\",[java.lang.String,java.lang.Object]);\n" +
                    " function getRowValue(sheetName,rowIndex){\n" +
                    "    var retStr = method_getRowValue.invoke(javaContext,sheetName,rowIndex);\n" +
                    "    var ret = {} ;\n" +
                    "    eval('ret='+retStr);\n" +
                    "    return ret;\n" +
                    " }\n" +
                    "\n" +
                    " var method_setRowValue = ScriptAPI.getMethod(\"setRowValue\",[java.lang.String,java.lang.Object,java.lang.Object]);\n" +
                    " function setRowValue(sheetName,rowIndex,rowValue){\n" +
                    "    method_setRowValue.invoke(javaContext,sheetName,rowIndex,rowValue);\n" +
                    " }\n" +
                    "\n" +
                    " var method_getTableHeader = ScriptAPI.getMethod(\"getTableHeader\",[java.lang.String,java.lang.Object]);\n" +
                    " function getTableHeader(sheetName,rowIndex){\n" +
                    "    return method_getTableHeader.invoke(javaContext,sheetName,rowIndex);\n" +
                    " }";

    private String getAllFunctions(){
        String funcStr =  " var ScriptAPI = java.lang.Class.forName(\"%s\", true, javaLoader);\n" ;
        Class cls = this.getClass();
        for (Method method: cls.getDeclaredMethods()){
            UKJSAnnotation an = (UKJSAnnotation)method.getAnnotation(UKJSAnnotation.class);
            if (an == null ) continue;
            String functionName = method.getName();

            String paramsTypeString  ="";//获取function的参数类型
            String paramsNameString = "";//获取function的参数名称
            String paramsNameInvokeString = "";
            Class [] parmTypeArray = method.getParameterTypes();
            if (parmTypeArray != null && parmTypeArray.length > 0){
                String[] parmStrArray = new String[parmTypeArray.length];
                String[] parmNameArray = new String[parmTypeArray.length];
                for (int i=0;i < parmTypeArray.length; i++){
                    parmStrArray[i] = parmTypeArray[i].getName();
                    parmNameArray[i] = "param" + i ;
                }
                paramsTypeString = String.format(",[%s]",TextUtils.join(",",parmStrArray));
                paramsNameString = TextUtils.join(",",parmNameArray);
                paramsNameInvokeString = "," + paramsNameString;
            }

            String methodStr = String.format(" var method_%s = ScriptAPI.getMethod(\"%s\"%s);\n",functionName,functionName,paramsTypeString);
            String functionStr = "";
            if (an.returnObject()){//返回对象
                functionStr = String.format(
                        " function %s(%s){\n" +
                        "    var retStr = method_%s.invoke(javaContext%s);\n" +
                        "    var ret = {} ;\n" +
                        "    eval('ret='+retStr);\n" +
                        "    return ret;\n" +
                        " }\n"  ,functionName,paramsNameString,functionName,paramsNameInvokeString );
            }else {//非返回对象
                functionStr = String.format(
                        " function %s(%s){\n" +
                        "    return method_%s.invoke(javaContext%s);\n" +
                        " }\n",functionName,paramsNameString,functionName,paramsNameInvokeString );
            }
            funcStr = funcStr + methodStr + functionStr;
        }
        return funcStr;
    }

    /**
     * 获取指定字段的值
     * @param keyStr
     * @return
     */
    @UKJSAnnotation(returnObject = false)
    public String getValueByKey(String keyStr){
        if (TextUtils.isEmpty(keyStr)) return null;

        if (map !=null && map.get(keyStr) != null){
            return map.get(keyStr).toString();
        }else {
            return "";
        }
    }

    /**
     * 给指定字段设置值
     * @param keyStr
     * @param o
     */
    @UKJSAnnotation(returnObject = false)
    public  void setValueByKey(Object keyStr , Object o){
        if (o != null){
            if (keyStr !=null && map.get(keyStr.toString())!=null){
                map.put(keyStr.toString(),o.toString());
            }
        }
    }

    /**
     * 获取表格行数
     * @param sheetName ： 表格名称 ，即 表格字段的 key
     */
    @UKJSAnnotation(returnObject = false)
    public int getTableRows(String sheetName){
//        if (formFieldContainer == null) return 0;
//        IFormField formField = formFieldContainer.getField(sheetName);
//        if (formField == null) return 0;
//
//        if (formField instanceof FormFieldContentTable){
//            List<CustomizeItem> customizeItemList = ((FormFieldContentTable)formField).getCustomizeItemList();
//            return customizeItemList == null ? 0 : customizeItemList.size();
//        }
        return 0;
    }

    /**
     * 获取一行的值
     * @param sheetName ： 表格名称 ，即 表格字段的 key
     * @param rowIndex : 指定行，从0开始
     */@UKJSAnnotation(returnObject = true)
    public String getRowValue(String sheetName, Object rowIndex){

        double d = (double) rowIndex;
        int index = (int) d;

        Object[] resultArray = new Object[1];
        String returnValue = "[]";//空数组
//        if (formFieldContainer == null) return returnValue;//new Gson().toJson(resultArray);
//        IFormField formField = formFieldContainer.getField(sheetName);
//        if (formField == null) return returnValue; //new Gson().toJson(resultArray);
//
//        if (formField instanceof FormFieldContentTable){
//            List<CustomizeItem> customizeItemList = ((FormFieldContentTable)formField).getCustomizeItemList();
//            if (customizeItemList != null && customizeItemList.size()-1 >= index){
//                resultArray[0] = customizeItemList.get(index).getFieldData();
//                returnValue = new Gson().toJson(resultArray);
//            }
//        }
        return returnValue;
    }

    /**
     * 设置表格中某行的值
     * @param sheetName 表格名称
     * @param rowIndex 行index
     * @param rowValue 该行的值
     */
    @UKJSAnnotation(returnObject = false)
    public void setRowValue(String sheetName, Object rowIndex,Object rowValue){

        double d = (double) rowIndex;
        int index = (int) d;

//        if (formFieldContainer == null) return ;

//        IFormField formField = formFieldContainer.getField(sheetName);
//        if (formField == null) return ;
//        if (rowValue == null ) return;
//
//        if (formField instanceof FormFieldContentTable){
//            List<CustomizeItem> customizeItemList = ((FormFieldContentTable)formField).getCustomizeItemList();
//            if (customizeItemList != null && customizeItemList.size()-1 >= index){
//                CustomizeItem changeItem = customizeItemList.get(index);
//                HashMap value = new Gson().fromJson(new Gson().toJson(rowValue),HashMap.class);
//                //遍历 value，将值类型为 double 转成 String
//                for (Object key : value.keySet()){
//                    if (value.get(key) instanceof Double){
//                        value.put(key,value.get(key).toString());
//                    }
//                }
//                changeItem.setFieldData(value);
//            }
//        }

    }

    /**
     * 获取表单表头
     * @param sheetName 表名 - 即表格字段key
     * @param rowIndex 指定项
     * @return
     */
    @UKJSAnnotation(returnObject = false)
    public Object[] getTableHeader(String sheetName, Object rowIndex){
        double d = (double) rowIndex;
        int index = (int) d;
        ArrayList<Object> list = new ArrayList<>();
//        if (formFieldContainer == null) return list.toArray();

//        IFormField formField = formFieldContainer.getField(sheetName);
//        if (formField == null) return list.toArray();
//
//        if (formField instanceof FormFieldContentTable){
//            List<CustomizeItem> customizeItemList = ((FormFieldContentTable)formField).getCustomizeItemList();
//            if (customizeItemList != null && customizeItemList.size()-1 >= index){
//                CustomizeItem changeItem = customizeItemList.get(index);
//                for (String key:changeItem.getFieldData().keySet()){
//                    list.add(key);
//                }
//            }
//        }
        return list.toArray();
    }

    public void setFieldMap(Map mapp){
        this.map = mapp;
    }

//    public void setFormFieldContainer(FormFieldContainer container){
//        this.formFieldContainer = container;
//    }

    /**
     *  执行JS
     * @param js js代码 eg: "var v1 = getValue('Ta');setValue(v1);"1
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

    public @interface UKJSAnnotation{
        public boolean returnObject() default false;
    }

/**

 var ScriptAPI = java.lang.Class.forName("%s", true, javaLoader);
 var methodGetValue=  ScriptAPI.getMethod("getValueByKey", [java.lang.String]);
 function getValue(key) {
 return  methodGetValue.invoke(javaContext,key);
 }
 var methodSetValue=ScriptAPI.getMethod("setValueByKey",[java.lang.Object,java.lang.Object]);
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
 */
}

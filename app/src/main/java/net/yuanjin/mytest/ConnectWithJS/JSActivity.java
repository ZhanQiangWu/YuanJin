package net.yuanjin.mytest.ConnectWithJS;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;
import net.yuanjin.utils.UKJSEngine;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.util.HashMap;
import java.util.Map;

/**
 *  与js交互
 * Created by wzq on 2017/7/10.
 */

public class JSActivity extends BasicActivity{

    private WebView contentWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_js);

//        //contentWebView = (WebView) findViewById(R.id.webview);
//        contentWebView = new WebView(this);
//        //启用JavaScript
//        contentWebView.getSettings().setJavaScriptEnabled(true);
//        //从assets目录下加载html文件
//        contentWebView.loadUrl("file:///android_asset/web.html");
//        contentWebView.addJavascriptInterface(this,"android");
//
//        //Button按钮 无参调用HTML js方法
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 无参数调用 JS的方法
//                contentWebView.loadUrl("javascript:javacalljs()");
//
//            }
//        });
//
//        //Button按钮 有参调用HTML js方法
//        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 传递参数调用JS的方法
//                contentWebView.loadUrl("javascript:javacalljswith(" + "'http://blog.csdn.net/Leejizhou'" + ")");
//            }
//        });


        TextView text1 = (TextView) findViewById(R.id.text1);
        TextView text2 = (TextView) findViewById(R.id.text2);
        TextView text3 = (TextView) findViewById(R.id.text3);

        map.put("Ta","20");
        map.put("Tb","100");

        UKJSEngine ukjsEngine = new UKJSEngine(this,JSActivity.class,map);

        ukjsEngine.runScript(testjs,"",new String[]{});

        //text1.setText(runScript(JAVA_CALL_JS_FUNCTION,"Test",new String[]{}));
       // text2.setText(runScript(JS_CALL_JAVA_FUNCTION,"Test",new String[]{}));
       // text3.setText(runScript(JS_CALL_JAVA_FUNCTION2,"Test",new String[]{}));
        //runScript(testjs,"",new String[]{});

        // TODO: 2017/7/13  
    }

    private static final String testjs = "var v1 = getValue('Ta');setValue(v1);";



    /** Java执行js的方法 */
    private static final String JAVA_CALL_JS_FUNCTION = "function Test(){ return '农民伯伯 java call js Rhino'; }";

    /** js调用Java中的方法 */
    private static final String JS_CALL_JAVA_FUNCTION = //
                    "var ScriptAPI = java.lang.Class.forName(\"" + JSActivity.class.getName() + "\", true, javaLoader);" +
                    // forName(String className, boolean shouldInitialize,ClassLoader classLoader)
                    // getMethod(String name, Class<?>... parameterTypes)
                    "var methodRead = ScriptAPI.getMethod(\"jsCallJava\", [java.lang.String]);" +
                    "function haha(url) {return methodRead.invoke(null, url);}" + //
                    "function Test(){ return haha(\"haha\"); }";

    private static final String JS_CALL_JAVA_FUNCTION2 =
                    "var ScriptAPI = java.lang.Class.forName(\"" + JSActivity.class.getName() + "\", true, javaLoader);" + //
                    " var methodRead = ScriptAPI.getMethod(\"getValueByKey\", [java.lang.String]);" +
                    "function JS_getValueByKey(key) { return methodRead.invoke(null,key); }" +
                    "function Test(){ var value1 = JS_getValueByKey(\"Ta\"); var value2 = JS_getValueByKey(\"Tb\"); return value1 * value2 ; }";


//    private static final String allFunctions = "var ScriptAPI = java.lang.Class.forName(\"" + JSActivity.class.getName() + "\", true, javaLoader);"+
//            "var methodGetValue=  ScriptAPI.getMethod(\"getValueByKey\", [java.lang.String]);" +
//            "function getValue(key) { return  methodGetValue.invoke(javaContext,key);}\r\n"+
//            "var methodSetValue=ScriptAPI.getMethod(\"setValueByKey\",[java.lang.String]);\r\n"+
//            "function setValue(val) { methodSetValue.invoke(javaContext,val);}\r\n";
//
//    public static String jsCallJava(String url) {
//        return "农民伯伯 js call Java Rhino" + url;
//    }
//
//    public  Object getValueByKey(String keyStr){
//        if (map.get(keyStr) != null){
//            return map.get(keyStr);
//        }else {
//            return "";
//        }
//    }
//
//    public  void setValueByKey(String sValue ){
//        TextView text1 = (TextView) findViewById(R.id.text1);
//        text1.setText(sValue);
//    }
    public static Map<String,Object> map = new HashMap<>();

//    /**
//     *  执行JS
//     * @param js js代码
//     * @param functionName js方法名称
//     * @param functionParams js方法参数
//     * @return
//     */
//    public void runScript(String js,String functionName,Object[] functionParams){
//        org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
//        rhino.setOptimizationLevel(-1);
//
//        try{
//            Scriptable scope = rhino.initStandardObjects();
//
//            ScriptableObject.putProperty(scope, "javaContext", org.mozilla.javascript.Context.javaToJS(JSActivity.this, scope));
//            ScriptableObject.putProperty(scope, "javaLoader", org.mozilla.javascript.Context.javaToJS(JSActivity.class.getClassLoader(), scope));
//
//            rhino.evaluateString(scope, js, "JSActivity", 1, null);
//
//
//        }finally {
//            org.mozilla.javascript.Context.exit();
//        }
//    }




    //由于安全原因 targetSdkVersion>=17需要加 @JavascriptInterface
    //JS调用Android JAVA方法名和HTML中的按钮 onclick后的别名后面的名字对应
    @JavascriptInterface
    public String startFunction(){
        int i = 90;
        return "hahahaha";

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(JSActivity.this,"show",Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    @JavascriptInterface
    public void startFunction(final String text){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(JSActivity.this).setMessage(text).show();

            }
        });
    }

//    public Object getValueByKey(String key){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                return 2;
//            }
//        });
//    }

}

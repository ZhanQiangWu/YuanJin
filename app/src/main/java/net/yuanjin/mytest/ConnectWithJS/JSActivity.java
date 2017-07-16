package net.yuanjin.mytest.ConnectWithJS;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import net.yuanjin.R;
import net.yuanjin.ui.BasicActivity;
import net.yuanjin.utils.UKJSEngine;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
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

        TextView text1 = (TextView) findViewById(R.id.text1);
        TextView text2 = (TextView) findViewById(R.id.text2);
        TextView text3 = (TextView) findViewById(R.id.text3);

        map.put("Ta","20");
        map.put("Tb","100");



        UKJSEngine ukjsEngine = new UKJSEngine();
        ukjsEngine.runScript(testjs,"",new String[]{});
        //ukjsEngine.getAllFunctions();
//        ukjsEngine.runScript(testjs,"",new String[]{});

    }

    public static class ArrayTest extends ScriptableObject {
        @JSFunction
        public double[] getLocation() {
            double[] array = {253.1, 152.0, -32.5};
            return array;
        }

        @Override
        public String getClassName() {
            return getClass().getSimpleName();
        }
    }

    private static final String testjs =
            //" var price = getValue('guypbx'); var num = getValue('fgnpkr');  var result;"
            //+" if(price >100 ){ result = \"price more than 100\"; }else{ result = price* num; }"
            //+"getTableRows('tab');"
            "var rowValue = getRowValue('tab',0);"
            //+"var rowValue = [1,2,3];"
            + "var length = rowValue[0];"
            //+" if(rowValue.length >0 ){ result = \"length have item\"; }else{ result = \"length = 0 \"; }"
            //+ "setValue('ijcldg',price * num); "
            //+ "setValue('ijcldg',result); "
            ;


    public static Map<String,Object> map = new HashMap<>();





}

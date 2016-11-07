package net.yuanjin.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import net.yuanjin.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class HomeActivity extends Activity {

    private GridView tabhost;
    private List<HomeTab> homeTabList;
    private TabHostAdapter tabHostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        tabhost = (GridView) findViewById(R.id.gridview_tabhost);

        initTabHost();

    }


    /**
     * 初始化底部标题栏
     */
    private void initTabHost() {
        homeTabList = new ArrayList<>();
        //tabHostAdapter = new ta
        HomeTab messageTab = new HomeTab(R.mipmap.tab_message,R.mipmap.tab_message_p,"消息");
        HomeTab officeTab = new HomeTab(R.mipmap.tab_office,R.mipmap.tab_office_p,"办公");
        HomeTab crmTab = new HomeTab(R.mipmap.tab_crm,R.mipmap.tab_crm_p,"CRM");
        HomeTab myTab = new HomeTab(R.mipmap.tab_crmmyself,R.mipmap.tab_crmmyself_p,"我");

        homeTabList.add(messageTab);
        homeTabList.add(officeTab);
        homeTabList.add(crmTab);
        homeTabList.add(myTab);





    }


    class HomeTab{

        private int iconResource;
        private int selectedResource;
        private String title;

        HomeTab( int iconResource,int selectedResource,String title){
            this.iconResource = iconResource;
            this.selectedResource = selectedResource;
            this.title = title;
        }
    }

    /**
     * Tab标签Adapter
     */
    class TabHostAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return homeTabList.size();
        }

        @Override
        public Object getItem(int position) {
            return homeTabList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView==null){
//                convertView = getLayoutInflater().inflate();
            }else{

            }
            return null;
        }
    }
}

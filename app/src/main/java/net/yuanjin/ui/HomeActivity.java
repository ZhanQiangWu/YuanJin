package net.yuanjin.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
        tabHostAdapter = new TabHostAdapter();

        homeTabList = initTabHost();
        tabhost.setNumColumns(homeTabList.size());
        tabhost.setAdapter(tabHostAdapter);
    }


    /**
     * 初始化底部标题栏
     */
    private List<HomeTab> initTabHost() {

        List<HomeTab>  list = new ArrayList<>();

        HomeTab messageTab = new HomeTab(R.mipmap.tab_message,R.mipmap.tab_message_p,"消息");
        HomeTab officeTab = new HomeTab(R.mipmap.tab_office,R.mipmap.tab_office_p,"办公");
        HomeTab crmTab = new HomeTab(R.mipmap.tab_crm,R.mipmap.tab_crm_p,"CRM");
        HomeTab myTab = new HomeTab(R.mipmap.tab_crmmyself,R.mipmap.tab_crmmyself_p,"我");
        HomeTab Tab = new HomeTab(R.mipmap.tab_crmmyself,R.mipmap.tab_crmmyself_p,"哈哈");
        HomeTab haha1 = new HomeTab(R.mipmap.tab_crmmyself,R.mipmap.tab_crmmyself_p,"我");
        HomeTab haha2 = new HomeTab(R.mipmap.tab_crmmyself,R.mipmap.tab_crmmyself_p,"哈哈");

        list.add(messageTab);
        list.add(officeTab);
        list.add(crmTab);
        list.add(myTab);
        list.add(Tab);
        list.add(haha1);
        list.add(haha2);
        return list;
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
            TabViewHolder viewHolder ;
            if (convertView==null){
                convertView = getLayoutInflater().inflate(R.layout.item_home_tab,null);
                viewHolder = new TabViewHolder();
                viewHolder.initView(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (TabViewHolder) convertView.getTag();
            }

            viewHolder.setValue(homeTabList.get(position));
            return convertView;
        }

        class TabViewHolder{

            private TextView tabTitle;
            private ImageView tabIcon;
            private ImageView imgPoint;

            /**
             * viewholder 初始化
             * @param convertView
             */
            public void initView(View convertView){
                this.tabTitle  = (TextView) convertView.findViewById(R.id.home_tab_title);
                this.tabIcon = (ImageView)convertView.findViewById(R.id.home_tab_icon);
                this.imgPoint = (ImageView)convertView.findViewById(R.id.home_tab_count_image);
            }

            public void setValue(HomeTab value){
                this.tabTitle.setText(value.title);
                this.tabIcon.setImageResource(value.iconResource);
                this.imgPoint.setVisibility(View.INVISIBLE);
            }

        }
    }
}

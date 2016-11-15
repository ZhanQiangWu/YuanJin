package net.yuanjin.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import net.yuanjin.R;
import net.yuanjin.ui.fragment.CRMFragment;
import net.yuanjin.ui.fragment.MessageFragment;
import net.yuanjin.ui.fragment.MySelfFragment;
import net.yuanjin.ui.fragment.OfficeFragment;
import net.yuanjin.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Home主页
 */
public class HomeActivity extends BasicActivity {

    private GridView tabhost;
    private List<HomeTab> homeTabList;
    private List<Fragment> fragmentList;
    private TabHostAdapter tabHostAdapter;
    private HomeFragmentPagerAdapter pagerAdapter;
    private HomeTab lastTab;
    private CustomViewPager viewPager;

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

        fragmentList = initFragMent();
        pagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        viewPager = (CustomViewPager) findViewById(R.id.viewpager_hometab);
        //viewPager.setDisableShuffle(true);//禁止滑动
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setAdapter(pagerAdapter);

    }

    /**
     * 初始化Fragments
     * @return
     */
    private List<Fragment> initFragMent() {
        List<Fragment> list = new ArrayList<>();
        MessageFragment messageFragment = new MessageFragment();
        OfficeFragment officeFragment = new OfficeFragment();
        CRMFragment crmFragment = new CRMFragment();
        MySelfFragment mySelfFragment = new MySelfFragment();

        list.add(messageFragment);
        list.add(officeFragment);
        list.add(crmFragment);
        list.add(mySelfFragment);
        return list;
    }


    /**
     * 初始化底部标题栏
     */
    private List<HomeTab> initTabHost() {

        List<HomeTab>  list = new ArrayList<>();

        HomeTab messageTab = new HomeTab(R.mipmap.tab_message,R.mipmap.tab_message_p,"消息",true);
        HomeTab officeTab = new HomeTab(R.mipmap.tab_office,R.mipmap.tab_office_p,"办公",false);
        HomeTab crmTab = new HomeTab(R.mipmap.tab_crm,R.mipmap.tab_crm_p,"CRM",false);
        HomeTab myTab = new HomeTab(R.mipmap.tab_crmmyself,R.mipmap.tab_crmmyself_p,"我",false);

        list.add(messageTab);
        list.add(officeTab);
        list.add(crmTab);
        list.add(myTab);
        lastTab = messageTab;
        return list;
    }


    class HomeTab{

        private int iconResource;
        private int selectedResource;
        private String title;
        private boolean isSelected;

        HomeTab( int iconResource,int selectedResource,String title,boolean isSelected){
            this.iconResource = iconResource;
            this.selectedResource = selectedResource;
            this.title = title;
            this.isSelected = isSelected;
        }

        /**
         * 功能描述：tab选中
         */
        public void onSelect(){
            this.isSelected = true;
        }

        /**
         * 功能描述：tab未选中
         */
        public void unSelect(){
            this.isSelected = false;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
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

            //点击事件监听
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeTabList.get(position).onSelect();
                    lastTab.unSelect();
                    lastTab = homeTabList.get(position);
                    tabHostAdapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(position);
                }
            });
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
                this.imgPoint.setVisibility(View.INVISIBLE);

                if (value.isSelected){
                    this.tabIcon.setImageResource(value.selectedResource);
                }else{
                    this.tabIcon.setImageResource(value.iconResource);
                }
            }

        }
    }

    /**
     * Fragment viewpagerAdapter
     */
    class HomeFragmentPagerAdapter extends FragmentPagerAdapter{

        public HomeFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

    }

    /**
     * ViewPager 动作监听
     */
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            homeTabList.get(position).onSelect();
            lastTab.unSelect();
            lastTab = homeTabList.get(position);
            tabHostAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}

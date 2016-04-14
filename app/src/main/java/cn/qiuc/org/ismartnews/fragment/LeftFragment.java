package cn.qiuc.org.ismartnews.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.activity.MainUI;
import cn.qiuc.org.ismartnews.bean.NewsCenterBean;

/**
 * Created by admin on 2016/4/14.
 */
public class LeftFragment extends BaseFragment {

    private ListView listView;
    private int currentItemIndex;
    private List<NewsCenterBean.MenuItem> menudata;
    private MyAdapter adapter;

    @Override
    protected View initView() {
        listView = new ListView(mContext);
        listView.setBackgroundColor(Color.BLACK);
        listView.setCacheColorHint(Color.TRANSPARENT);//removal background when sliding
        listView.setDividerHeight(0);//remove line
        listView.setPadding(0, 40, 0, 0);
        listView.setSelector(android.R.color.transparent);//remove default item selector

        listView.setOnItemClickListener(new MyOnItemClickListener());
        return listView;
    }


    private class MyOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //when click a item. record the location of the click and notify the listView update
            currentItemIndex = position;
            adapter.notifyDataSetChanged();

            //let the menu automatically slide back
            MainUI mainUI = (MainUI) LeftFragment.this.mContext;
            mainUI.getSlidingMenu().toggle();

            //click on the item to find the news center object, change his fragmentLayout
//            NewsCenterPager newsCenterPager = MainUI.getContentFragment().getNewsCenterPager();
//            newsCenterPager.switchPager(position);

        }
    }

    public void setMenuData(List<NewsCenterBean.MenuItem> data) {

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return menudata.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(mContext, R.layout.left_menu_item, null);
            textView.setText(menudata.get(position).title);
            //according to the location of the click
            //to determine whether the current entry is selected
            //by changing the enable state, to achieve state switch
            textView.setEnabled(currentItemIndex==position);
            return textView;
        }
    }
}

package cn.qiuc.org.ismartnews.base;

import android.content.Context;
import android.view.View;

/**
 * base class for four details on the left
 * Created by admin on 2016/4/21.
 */
public abstract class MenuDetailBasePager {

    public View rootView;
    protected Context mContext;

    public MenuDetailBasePager(Context context) {
        mContext = context;
        rootView = initView();
    }

    /**
     * subclass must implement
     * return view
     * @return
     */
    protected abstract View initView();

    /**
     * subclass update their own layout
     */
    public void initData(){}
}

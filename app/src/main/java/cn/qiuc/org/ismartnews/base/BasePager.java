package cn.qiuc.org.ismartnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.activity.MainUI;

/**
 * Created by admin on 2016/4/19.
 * base class for 5 view in the text
 */
public class BasePager implements View.OnClickListener {
    public View rootView;
    protected Context mContext;
    protected TextView tv_basepager_title;
    protected ImageButton ib_basepager_menu;
    protected FrameLayout fl_basepager_content;
    protected ImageButton ib_basepager_phototype;

    public BasePager(Context context){
        mContext = context;
        rootView = initView();
    }

    private View initView() {
        View view = View.inflate(mContext, R.layout.basepager, null);
        tv_basepager_title = (TextView) view.findViewById(R.id.tv_basepager_title);
        ib_basepager_menu = (ImageButton) view.findViewById(R.id.ib_basepager_menu);
        fl_basepager_content = (FrameLayout) view.findViewById(R.id.fl_basepager_content);
        ib_basepager_phototype = (ImageButton) view.findViewById(R.id.ib_basepager_phototype);
        ib_basepager_phototype.setOnClickListener(this);
        return view;

    }

    /**
     * sub class update itself view
     * do not have to be realized
     */
    public void initData(){}

    @Override
    public void onClick(View v) {
        //let the menu automatically back
        MainUI mainUI = (MainUI)mContext;
        mainUI.getSlidingMenu().toggle();
    }
}

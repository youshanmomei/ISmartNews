package cn.qiuc.org.ismartnews.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.qiuc.org.ismartnews.R;
import cn.qiuc.org.ismartnews.base.BaseFragment;
import cn.qiuc.org.ismartnews.base.BasePager;

/**
 * Created by admin on 2016/4/14.
 */
public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.vp_content_pagers)
    private ViewPager vp_content_pagers;
    private List<BasePager> pagers;

    @ViewInject(R.id.rg_content_bottom)
    private RadioGroup rg_content_bottom;


    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.content, null);

        ViewUtils.inject(this, view);
        return view;
    }
}

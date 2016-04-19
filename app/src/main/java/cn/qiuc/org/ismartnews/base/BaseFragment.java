package cn.qiuc.org.ismartnews.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by admin on 2016/4/14.
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        return initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //onCreate after the implementation of the control update
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    /**
     * subclass cover, update their own control, can not cover
     */
    protected void initDate() {

    }

    /**
     * subclass must implement, return to the specific control
     * @return
     */
    protected abstract View initView();

    ;
}

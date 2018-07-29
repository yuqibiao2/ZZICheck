package com.afrid.icheck.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.afrid.icheck.R;
import com.afrid.icheck.ui.fragment.UndoReceiptShowFragment;
import com.yyyu.baselibrary.utils.MyTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能：未发净订单显示
 *
 * @author yyyu
 * @version 1.0
 * @date 2018/7/28
 */
public class UndoReceiptShowActivity extends MyBaseActivity {

    @BindView(R.id.tl_undo)
    TabLayout tlUndo;
    @BindView(R.id.vp_undo)
    ViewPager vpUndo;
    private List<TabBean> tabList;

    @Override
    public void beforeInit() {
        super.beforeInit();
        tabList = new ArrayList<>();
        String today = MyTimeUtils.formatDate("yyMMdd", System.currentTimeMillis());
        Calendar calendar2 = Calendar.getInstance();
        int day2 = calendar2.get(Calendar.DATE);
        calendar2.set(Calendar.DATE, day2+1);
        String  tomorrow= MyTimeUtils.formatDate("yyMMdd", calendar2.getTimeInMillis());
        tabList.add(new TabBean("今天"  , UndoReceiptShowFragment.newInstance(today ,tomorrow)));
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day-1);
        String  yesterday= MyTimeUtils.formatDate("yyMMdd", calendar.getTimeInMillis());
        tabList.add(new TabBean("昨天" ,UndoReceiptShowFragment.newInstance(yesterday , today)));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_undo_receipt_show;
    }

    @Override
    protected void initView() {
        vpUndo.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return tabList.get(position).getFragment();
            }

            @Override
            public int getCount() {
                return tabList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return tabList.get(position).getTitle();
            }
        });
        tlUndo.setupWithViewPager(vpUndo);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        super.initData();
    }

    public void back(View view) {
        finish();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, UndoReceiptShowActivity.class);
        context.startActivity(intent);
    }


    public static class TabBean{
        private String title;
        private Fragment fragment;

        public TabBean(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }
    }

}

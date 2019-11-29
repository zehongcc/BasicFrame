package com.czh.basicframe;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.czh.basicframe.base.BaseActivity;
import com.czh.basicframe.https.base.BasePresenter;
import com.czh.basicframe.ui.Fragment_1;
import com.czh.basicframe.ui.Fragment_2;
import com.czh.basicframe.ui.Fragment_Animation;
import com.czh.basicframe.ui.Fragment_ConstraintLayout;
import com.czh.basicframe.ui.Fragment_Test_DB;
import com.czh.basicframe.ui.Fragment_3;
import com.czh.basicframe.ui.TestFragment;
import com.czh.basicframe.ui.VoiceFragment;
import com.czh.basicframe.utils.EventBean;
import com.czh.basicframe.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.main_tabLayout)
    TabLayout tabLayout;

    private List<FragmentBean> fragments;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        setIsBackAble(true);//开启双击退出程序
        fragments = new ArrayList<>();
        fragments.add(new FragmentBean("图片", false, new Fragment_1()));
        fragments.add(new FragmentBean("音频管理", false, new Fragment_2()));
        fragments.add(new FragmentBean("自定义View", false, new Fragment_3()));
        fragments.add(new FragmentBean("语音对讲", false, new VoiceFragment()));
        fragments.add(new FragmentBean("Test", false, new TestFragment()));
        fragments.add(new FragmentBean("db", false, new Fragment_Test_DB()));
        fragments.add(new FragmentBean("动画", false, new Fragment_Animation()));
        fragments.add(new FragmentBean("ConstraintLayout", false, new Fragment_ConstraintLayout()));
    }

    @Override
    protected void main() {
        for (int i = 0; i < fragments.size(); i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(fragments.get(i).getType());
            tabLayout.addTab(tab);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                changeFragment(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        changeFragment(0);
    }

    private void changeFragment(int index) {
        FragmentBean fragmentBean = fragments.get(index);
        Fragment fragment = fragmentBean.getFragment();
        boolean add = fragmentBean.isAdd();
        LogUtils.e("ChangeFragment === " + index + " ,, " + add);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            Fragment f = fragments.get(i).getFragment();
            transaction.hide(f);
        }
        if (!add) {
            transaction.add(R.id.frameLayout, fragment);
            transaction.show(fragment);
            fragmentBean.setAdd(true);
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
    }

    @Override
    public void onEventBus(EventBean object) {
        super.onEventBus(object);
        if (object == null) return;
        String content = (String) object.getObject();
        int tag = object.getTag();
    }


    private class FragmentBean {
        private boolean isAdd;
        private Fragment fragment;
        private String type;

        public FragmentBean(String type, boolean isAdd, Fragment fragment) {
            this.isAdd = isAdd;
            this.type = type;
            this.fragment = fragment;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isAdd() {
            return isAdd;
        }

        public void setAdd(boolean add) {
            isAdd = add;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }
    }
}
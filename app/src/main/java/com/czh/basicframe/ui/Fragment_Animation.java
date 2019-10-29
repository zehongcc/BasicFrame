package com.czh.basicframe.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author  : czh
 * create Date : 2019/10/26  13:48
 * 详情 :
 */
public class Fragment_Animation extends BaseFragment {
    @BindView(R.id.animation_tv)
    TextView animationTv;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;

    @Override
    protected int setLayout() {
        return R.layout.fragment_animation;
    }

    @Override
    protected void initValue(Bundle bundle) {

    }

    @Override
    protected void main() {

    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,R.id.animation_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.animation_tv:
                animationTv.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.anim_alpha));
                break;
            case R.id.btn1:
                //组合动画
                AnimatorSet animatorSet = new AnimatorSet();
//                ObjectAnimator animator = ObjectAnimator.ofFloat();
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                break;
            case R.id.btn4:
                break;
        }
    }
}

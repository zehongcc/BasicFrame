package com.czh.basicframe.mvp;

/**
 * author  : czh
 * create Date : 2019/10/22  15:47
 * 详情 :
 */
public interface ImpPresenter {

    /**
     * 绑定view
     * @param view
     */
    void acttech(BaseView view);

    /**
     * 解绑
     * @param view
     */
    void detech(BaseView view);
}

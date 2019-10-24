package com.czh.basicframe.widget.dialog;

import com.czh.basicframe.interfaces.DialogClickListener;

/**
 * author  : czh
 * create Date : 2019/10/24  11:24
 * 详情 :  NormDialog可控制参数
 *
 * @link NormDialog
 */
class NormDialogParameter {
    String title;
    String content;
    String ok;
    String cancel;
    boolean isHideCancel;//是否显示取消按钮
    DialogClickListener listener;//点击事件监听

    //字体颜色
    int okTvColor;
    int cancelTvColor;
    int contentTvColor;
    int titleTvColor;

    NormDialogParameter() {

    }
}

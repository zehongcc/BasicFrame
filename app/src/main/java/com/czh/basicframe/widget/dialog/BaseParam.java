package com.czh.basicframe.widget.dialog;

/**
 * create by Chen
 * create date : 2019/11/18
 * desc : dialog的基本参数
 */
public class BaseParam {
    public String titleStr;//标题文字
    public String contentStr;//提示正文文字
    public String okStr;//确定按钮文字
    public String cancelStr;//取消按钮文字

    public boolean isCancelable ;//是否可以取消
    public boolean isCanceledOnTouchOutside;//是否点击外部隐藏

    public BaseDialog.OnDialogClickListener clickListener;//点击监听

}

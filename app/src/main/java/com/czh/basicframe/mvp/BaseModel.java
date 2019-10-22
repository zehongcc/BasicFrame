package com.czh.basicframe.mvp;

import com.czh.basicframe.https.MyService;
import com.czh.basicframe.https.RetrofitUtils;

/**
 * author  : czh
 * create Date : 2019/10/22  14:47
 * 详情 :
 */
public class BaseModel {

    public static MyService service = RetrofitUtils.getInstance().getService();

}

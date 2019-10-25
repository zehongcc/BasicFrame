package com.czh.basicframe.ui;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.czh.basicframe.R;
import com.czh.basicframe.base.BaseFragment;
import com.czh.basicframe.db.SQHelper;
import com.czh.basicframe.utils.Code;
import com.czh.basicframe.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author  : czh
 * create Date : 2019/10/25  15:05
 * 详情 :测试数据库界面
 */
public class Fragment_Test_DB extends BaseFragment {
    @BindView(R.id.db_content_tv)
    TextView dbContentTv;
    @BindView(R.id.db_btn1)
    Button dbBtn1;
    @BindView(R.id.db_btn2)
    Button dbBtn2;
    @BindView(R.id.db_btn3)
    Button dbBtn3;
    @BindView(R.id.db_btn4)
    Button dbBtn4;
    @BindView(R.id.db_name_et)
    EditText dbNameEt;
    @BindView(R.id.db_age_et)
    EditText dbAgeEt;

    private SQLiteDatabase db;

    @Override
    protected int setLayout() {
        return R.layout.fragment_test_db;
    }

    @Override
    protected void initValue(Bundle bundle) {
        db = new SQHelper(mContext).getWritableDatabase();
    }

    @Override
    protected void main() {

    }


    @OnClick({R.id.db_btn0, R.id.db_btn1, R.id.db_btn2, R.id.db_btn3, R.id.db_btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.db_btn0://更新数据库
                if (db != null) {
                    db.close();
                    db = null;
                }
                Code.DB_VERSION = 3;
                db = new SQHelper(mContext).getWritableDatabase();
                break;
            case R.id.db_btn1://增
                insertData();
                break;
            case R.id.db_btn2://删
                deleteData();
                break;
            case R.id.db_btn3://改
                updateData();
                break;
            case R.id.db_btn4://查
                quaryData();
                break;
        }
    }

    /**
     * 修改
     */
    private void updateData() {
        //
        String s = dbNameEt.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put("des", s);
        String whereCause = "name=?";//修改条件
        String[] whereArgs = {"chen"};//?的内容
        //如果 whereCause 和 whereArgs 不传则影响所有行。
        db.update(SQHelper.TABLE_NAME_1, cv, null, null);
        quaryData();
    }

    /**
     * 删除数据
     */
    private void deleteData() {
        String whereClause = "age=?";
        String[] whereArgs = new String[]{"66"};
        int delete = db.delete(SQHelper.TABLE_NAME_1, whereClause, whereArgs);
        LogUtils.e("受影响的行数：" + delete);
        quaryData();
    }

    /**
     * 查询数据
     */
    private void quaryData() {
        String sql = "select *  from " + SQHelper.TABLE_NAME_1;
        Cursor cursor = db.rawQuery(sql, null);
        StringBuffer sb = new StringBuffer();

        while (cursor.moveToNext()) {
            int columnCount = cursor.getColumnCount();
            int id = cursor.getInt(0);//查询第1列
            String name = cursor.getString(1);//查询第2列
            String age = cursor.getString(2);//查询第3列
            String desc = cursor.getString(3);//查询第4列
            String b = cursor.getString(4);//查询第5列
            sb.append("id =" + id + " , name =" + name + " , age = " + age + " ,desc = " + desc + " , b= " + b + "\n");
            LogUtils.e(TAG, "查询结果 >>>>>> id = " + id + " , name = " + name + " , age = " + age + " , desc = " + desc + " , 总共多少列 ： " + columnCount);
        }
        cursor.close();
        dbContentTv.setText(sb.toString());
    }

    /**
     * 添加到数据库
     */
    private void insertData() {
        long result = -1;
        String name = dbNameEt.getText().toString();
        String age = dbAgeEt.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age)) {
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("age", age);
            cv.put("des", "66666666666");
            result = db.insert(SQHelper.TABLE_NAME_1, null, cv);
        }
        if (result == -1) {
            toast.shortToast("错误");
        } else {
            toast.shortToast("插入成功");
            dbNameEt.setText("");
            dbAgeEt.setText("");
        }
        quaryData();
    }
}

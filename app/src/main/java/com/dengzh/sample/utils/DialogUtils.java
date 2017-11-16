package com.dengzh.sample.utils;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.dengzh.core.view.BaseDialog;
import com.dengzh.sample.R;

/**
 * Created by dengzh on 2017/11/3.
 * 通用对话框的工具类
 * 如果是样式单一，如分享弹窗唯一，则不要在此处添加。
 */

public class DialogUtils {


    /**
     * 带一个按钮的提示弹窗
     *
     * @param context      上下文
     * @param title        标题
     * @param content      内容
     * @param dialogSelect 监听接口
     */
    public static void showTipsWithOneButton(Activity context, String title, String content, final IDialogSelect dialogSelect) {
        //1.创建dialog
        final BaseDialog dialog = new BaseDialog(context, R.layout.dialog_one_button, Gravity.CENTER, true);
        //2.配置信息
        dialog.setText(R.id.titleTv, title)
                .setText(R.id.contentTv, content)
                .setOnViewClick(R.id.buttonTv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogSelect != null) {
                            dialogSelect.onSelected(0);
                        }
                        dialog.toggleDialog();
                    }
                });
        //3.显示dialog
        dialog.toggleDialog();
    }

    /**
     * 带两个按钮的提示弹窗
     */
    public static void showTipsWithTwoButton(Activity context, String content, final IDialogSelect dialogSelect) {
        //1.创建dialog
        final BaseDialog dialog = new BaseDialog(context, R.layout.dialog_two_button, Gravity.CENTER, true);
        //2.配置信息
        dialog.setText(R.id.contentTv, content);
        dialog.setOnViewClick(R.id.leftTv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(0);
                }
                dialog.toggleDialog();
            }
        });
        dialog.setOnViewClick(R.id.rightTv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSelect != null) {
                    dialogSelect.onSelected(1);
                }
                dialog.toggleDialog();
            }
        });
        //3.显示dialog
        dialog.toggleDialog();
    }


    /**
     * 点击接口
     */
    public interface IDialogSelect {
        void onSelected(int i);
    }
}

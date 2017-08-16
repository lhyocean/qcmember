package com.beijing.qchealth.qchealth_vip.utils;

import android.text.InputFilter;
import android.text.Spanned;

import org.haitao.common.utils.ToastUtil;

/**
 * Created by lenovo on 2016/9/28.
 */
public class MyFilterUtil {
   public static InputFilter myPassInputFilter=new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                char c = source.charAt(i);
                if (c>=48&&c<=57||c>=65&&c<=90||c>=97&&c<=122){
                }else {
                    return "";
                }
            }
            return null;
        }
    };

    //  表情限制 Filter
   public static InputFilter mInputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (end > start) {
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (!isEmoji(c)) {
                        ToastUtil.shortShowToast("不能输入表情");
                        return "";
                    }
                }
            }
            return null;
        }
    };

    private static boolean isEmoji(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

}

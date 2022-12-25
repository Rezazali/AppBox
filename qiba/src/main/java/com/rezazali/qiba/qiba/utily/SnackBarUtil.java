package com.rezazali.qiba.qiba.utily;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.rezazali.qiba.R;

public class SnackBarUtil {

    public SnackBarUtil() {


    }
    /**
     * Used to give us width of screen
     *
     * @param context  context of activity or fragment
     * @param message  that display in snackbar
     * @param color value 0 red color and 1 green color
     */
    public static void alert(View view, Context context, String message, int color) {

        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snackbar.getView();
        if (color == 0) {
            group.setBackgroundColor(ContextCompat.getColor(context, R.color.red_500));
        } else {
            group.setBackgroundColor(ContextCompat.getColor(context, R.color.green_500));

        }
        snackbar.show();

    }

    public static class NavDrawerItem {
        private boolean showNotify;
        private String title;
        private Integer image;
        private String code;

        public NavDrawerItem() {

        }

        public NavDrawerItem(boolean showNotify, String title,Integer m_image) {
            this.showNotify = showNotify;
            this.title = title;
            image=m_image;
        }



        public NavDrawerItem(boolean showNotify, String title,Integer m_image,String code) {
            this.showNotify = showNotify;
            this.title = title;
            image=m_image;
            this.code=code;
        }




        public boolean isShowNotify() {
            return showNotify;
        }

        public void setShowNotify(boolean showNotify) {
            this.showNotify = showNotify;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }



    }
}

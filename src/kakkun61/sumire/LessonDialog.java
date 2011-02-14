package kakkun61.sumire;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.ViewGroup;

public abstract class LessonDialog {
    protected static abstract class OnClickListener implements DialogInterface.OnClickListener {
        protected int day;
        protected int hour;
        protected Activity activity;
        protected ViewGroup viewGroup;

        public OnClickListener(int day, int hour, Activity activity, ViewGroup viewGroup) {
            super();
            this.day = day;
            this.hour = hour;
            this.activity = activity;
            this.viewGroup = viewGroup;
        }
    }
}

package kakkun61.sumire;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class LessonClickListener implements OnClickListener {
    protected int day;
    protected int hour;
    protected Activity activity;
    protected ViewGroup viewGroup;

    protected AlertDialog.Builder builder;

    public LessonClickListener(int day, int hour, Activity activity, ViewGroup viewGroup) {
        this.day = day;
        this.hour = hour;
        this.activity = activity;
        this.viewGroup = viewGroup;
    }
}

package kakkun61.sumire;

import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class LessonClickListener implements OnClickListener {
    private Lesson lesson;
    private int day;
    private int hour;
    private ViewGroup viewGroup;

    private AlertDialog.Builder builder;

    public LessonClickListener(Lesson lesson, int day, int hour, ViewGroup viewGroup) {
        this.lesson = lesson;
        this.day = day;
        this.hour = hour;
        this.viewGroup = viewGroup;
    }

    public void onClick(final View view) {
        // Toast.makeText(view.getContext(), "test", Toast.LENGTH_SHORT).show();
        if (builder == null)
            builder = LessonDialog.newBuilder(lesson, day, hour, viewGroup);
        builder.show();
    }
}

package kakkun61.sumire;

import android.view.View;
import android.view.View.OnClickListener;

public class LessonClickListener implements OnClickListener {
    private Lesson lesson;
    private int day;
    private int hour;

    public LessonClickListener(Lesson lesson, int day, int hour) {
        this.lesson = lesson;
    }

    public void onClick(final View view) {
        // Toast.makeText(view.getContext(), "test", Toast.LENGTH_SHORT).show();
        LessonDialog.newBuilder(lesson, day, hour, view).show();
    }
}

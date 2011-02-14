package kakkun61.sumire;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

public class EditLessonClickListener extends LessonClickListener {

    public EditLessonClickListener(int day, int hour, Activity activity, ViewGroup viewGroup) {
        super(day, hour, activity, viewGroup);
    }

    public void onClick(View view) {
        if (builder == null)
            builder = EditLessonDialog.newBuilder(day, hour, activity, viewGroup);
        builder.show();
    }

}

package kakkun61.sumire;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

public class LessonDialog {
    private static class OnClickListener implements DialogInterface.OnClickListener {
        private Lesson lesson;
        private int day;
        private int hour;
        private View view;

        public OnClickListener(Lesson lesson, int day, int hour, View view) {
            super();
            this.lesson = lesson;
            this.day = day;
            this.hour = hour;
            this.view = view;
        }

        public void onClick(DialogInterface dialog, int which) {
            // TODO 保存などの処理
            Toast.makeText(view.getContext(), "test" + which, Toast.LENGTH_SHORT).show();
        }
    }

    public static AlertDialog.Builder newBuilder(Lesson lesson, int day, int hour, View view) {
        OnClickListener onClick = new OnClickListener(lesson, hour, hour, view);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        int items;

        if (lesson == null) {
//            builder.setTitle(R.string.lesson_dialog_alart_title_new);
            items = R.array.lesson_dialog_alart_items_new;
        } else {
//            builder.setTitle(lesson.name);
            items = R.array.lesson_dialog_alart_items_edit;
        }
        builder.setItems(items, onClick);
            builder.setItems(new String[]{lesson.name}, onClick);
//        builder.setNegativeButton(R.string.negative, onClick);
        return builder;
    }
}

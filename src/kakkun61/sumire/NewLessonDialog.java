package kakkun61.sumire;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ViewGroup;

public class NewLessonDialog extends LessonDialog {

    private static class NewOnClickListener extends OnClickListener {
        private static final int NEW = 0;

        public NewOnClickListener(int day, int hour, Activity activity, ViewGroup viewGroup) {
            super(day, hour, activity, viewGroup);
        }

        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
            case NEW: // 新規作成
                Intent intent = new Intent();
                intent.setClass(viewGroup.getContext(), EditLessonActivity.class);
                intent.putExtra("hour", hour);
                activity.startActivityForResult(intent, ShowLessonActivity.NEW_LESSON);
                break;
            }
        }
    }


    public static AlertDialog.Builder newBuilder(int day, int hour, Activity activity, ViewGroup viewGroup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());

        builder.setItems(R.array.lesson_dialog_alart_items_new, new NewOnClickListener(day, hour, activity, viewGroup));
        return builder;
    }
}

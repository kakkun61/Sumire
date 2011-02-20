package kakkun61.sumire;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Toast;

public class EditLessonDialog extends LessonDialog {
    private static class EditOnClickListener extends OnClickListener {

        public EditOnClickListener(int day, int hour, Activity activity, ViewGroup viewGroup) {
            super(day, hour, activity, viewGroup);
        }

        public void onClick(DialogInterface dialog, int which) {
            // TODO 保存などの処理
            Toast.makeText(viewGroup.getContext(), "test" + which, Toast.LENGTH_SHORT).show();
            switch (which) {
            case 0: // 編集
                Intent intent = new Intent();
                intent.setClass(viewGroup.getContext(), EditLessonActivity.class);
                intent.putExtra("hour", hour);
                activity.startActivityForResult(intent, ShowLessonActivity.EDIT_LESSON);
                break;
            case 1: // 消去
                viewGroup.removeAllViews();
                GlobalData.putLesson(day, hour, null);
                viewGroup.setOnClickListener(new NewLessonClickListener(which, which, activity, viewGroup));
                break;
            }
        }
    }

    public static AlertDialog.Builder newBuilder(int day, int hour, Activity activity, ViewGroup viewGroup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
        builder.setItems(R.array.lesson_dialog_alart_items_edit, new EditOnClickListener(day, hour, activity, viewGroup));
        return builder;
    }
}

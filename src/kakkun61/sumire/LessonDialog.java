package kakkun61.sumire;

import kakkun61.sumire.util.SumireLog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LessonDialog {
    private static abstract class OnClickListener implements DialogInterface.OnClickListener {
        protected int day;
        protected int hour;
        protected ViewGroup viewGroup;

        public OnClickListener(int day, int hour, ViewGroup viewGroup) {
            super();
            this.day = day;
            this.hour = hour;
            this.viewGroup = viewGroup;
        }
    }

    private static class NewOnClickListener extends OnClickListener {
        public NewOnClickListener(int day, int hour, ViewGroup viewGroup) {
            super(day, hour, viewGroup);
            // TODO 自動生成されたコンストラクター・スタブ
        }

        public void onClick(DialogInterface dialog, int which) {
            // TODO 保存などの処理
            Toast.makeText(viewGroup.getContext(), "test" + which, Toast.LENGTH_SHORT).show();
            switch (which) {
            case 0: // 新規作成
                Lesson lesson = new Lesson("授業", "先生", "部屋", 1);
                GlobalData.setLesson(day, hour, lesson);
                SumireLog.d("view: " + viewGroup);
                TextView name = new TextView(viewGroup.getContext());
                name.setTextSize(20);
                name.setText(lesson.name);
                viewGroup.addView(name);
                name.setBackgroundColor(Color.GREEN);

                TextView teacher = new TextView(viewGroup.getContext());
                teacher.setTextSize(20);
                teacher.setText(lesson.teacher);
                teacher.setBackgroundColor(Color.GREEN);
                viewGroup.addView(teacher);

                TextView room = new TextView(viewGroup.getContext());
                room.setTextSize(20);
                room.setText(lesson.room);
                room.setBackgroundColor(Color.GREEN);
                viewGroup.addView(room);
                break;
            }
        }
    }

    private static class EditOnClickListener extends OnClickListener {
        protected Lesson lesson;

        public EditOnClickListener(Lesson lesson, int day, int hour, ViewGroup viewGroup) {
            super(day, hour, viewGroup);
            this.lesson = lesson;
        }

        public void onClick(DialogInterface dialog, int which) {
            // TODO 保存などの処理
            Toast.makeText(viewGroup.getContext(), "test" + which, Toast.LENGTH_SHORT).show();
            switch (which) {
            }
        }
    }

    public static AlertDialog.Builder newBuilder(Lesson lesson, int day, int hour, ViewGroup viewGroup) {
        AlertDialog.Builder builder = new AlertDialog.Builder(viewGroup.getContext());
        int items;

        if (lesson == null) {
//            builder.setTitle(R.string.lesson_dialog_alart_title_new);
            items = R.array.lesson_dialog_alart_items_new;
            builder.setItems(items, new NewOnClickListener(day, hour, viewGroup));
        } else {
//            builder.setTitle(lesson.name);
            items = R.array.lesson_dialog_alart_items_edit;
            builder.setItems(items, new EditOnClickListener(lesson, day, hour, viewGroup));
        }
//        builder.setNegativeButton(R.string.negative, onClick);
        return builder;
    }
}

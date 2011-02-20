package kakkun61.sumire;

import kakkun61.sumire.util.SumireLog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditLessonActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editlesson);

        Intent request = getIntent();
        int hour = request.getIntExtra("hour", -1);
        Lesson lesson = GlobalData.getLesson(GlobalData.getShowenDay(), hour);
        EditText name = (EditText)findViewById(R.id.edit_lesson_name);
        EditText teacher = (EditText)findViewById(R.id.edit_lesson_teacher);
        EditText room = (EditText)findViewById(R.id.edit_lesson_room);
        EditText series = (EditText)findViewById(R.id.edit_lesson_series);

        findViewById(R.id.edit_lesson_ok).setOnClickListener(
                new OkOnClickListener(name, teacher, room, series));
        if (lesson != null) {
            name.setText(lesson.name);
            teacher.setText(lesson.teacher);
            room.setText(lesson.room);
            series.setText(Integer.toString(lesson.series));
        }

        findViewById(R.id.edit_lesson_cancel).setOnClickListener(new CancelOnClickListener());
    }

    private class OkOnClickListener implements View.OnClickListener {
        EditText name;
        EditText teacher;
        EditText room;
        EditText series;

        public OkOnClickListener(EditText name, EditText teacher, EditText room, EditText series) {
//            SumireLog.d("OkOnClickListener.<init>");
            this.name = name;
            this.teacher = teacher;
            this.room = room;
            this.series = series;
        }

        public void onClick(View v) {
//            SumireLog.d("OkOnClickListener.onClick");
            Intent request = getIntent();
            int hour = request.getIntExtra("hour", -1);
            int day = GlobalData.getShowenDay();
            Lesson lesson = GlobalData.getLesson(day, hour);
            String nameStr    = name.getText().toString();
            String teacherStr = teacher.getText().toString();
            String roomStr    = room.getText().toString();
            String seriesStr  = series.getText().toString();
            if (nameStr.length() == 0 || teacherStr.length() == 0 || roomStr.length() == 0 || seriesStr.length() == 0) {
                finish();
            }
            try {
                if (lesson == null){
                    SumireLog.d("lesson == null");
                    lesson = new Lesson(
                            name.getText().toString(),
                            teacher.getText().toString(),
                            room.getText().toString(),
                            Integer.parseInt(series.getText().toString()));
                    GlobalData.putLesson(day, hour, lesson);
                } else {
                    SumireLog.d("lesson != null");
                    lesson.name = name.getText().toString();
                    lesson.teacher = teacher.getText().toString();
                    lesson.room = room.getText().toString();
                    lesson.series = Integer.parseInt(series.getText().toString());
                }
                Intent result = new Intent();
                result.putExtra("hour", hour);
                setResult(RESULT_OK, result);
                finish();
            } catch (NumberFormatException e) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }

    private class CancelOnClickListener implements View.OnClickListener {

        public CancelOnClickListener() {
        }

        public void onClick(View v) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}

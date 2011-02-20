package kakkun61.sumire;

import kakkun61.sumire.util.SumireLog;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ShowLessonActivity extends Activity {
    public static final int NEW_LESSON = 0;
    public static final int EDIT_LESSON = 1;
    private RightCell[] rightCells;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.createDefaultSharedPreferences(this);
        GlobalData.loadAllData();
        setContentView(R.layout.main);
        int day = GlobalData.getShowenDay();
        TableLayout table = (TableLayout)findViewById(R.id.talbe);
        table.setColumnStretchable(1, true);
        rightCells = new RightCell[GlobalData.getLessonsADayCount()];
        for (int hour=0; hour<GlobalData.getLessonsADayCount(); hour++) {
            TableRow row = new TableRow(this);
//            row.setBackgroundColor(Color.CYAN);
            TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            params.weight = 0;
            params.setMargins(5, 2, 2, 2);
            row.addView(createLeftCell(hour), params);
            RightCell rc = new RightCell(day, hour);
            if (rc != null) {
                params.weight = 1;
                params.setMargins(2, 2, 2, 5);
                row.addView(rc, params);
            }
            rightCells[hour] = rc;
            TableLayout.LayoutParams tlParams = new TableLayout.LayoutParams();
            tlParams.height = LayoutParams.FILL_PARENT;
            tlParams.weight = 1;
            table.addView(row, tlParams);
        }
    }

    private View createLeftCell(int day) {
//        SumireLog.d("call Main.createLeftCell(" + i + ")");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
//        layout.setBackgroundColor(Color.BLUE);
        layout.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        TextView begin = new TextView(this);
        begin.setText("12:55");
        begin.setGravity(Gravity.CENTER);
//        begin.setBackgroundColor(Color.GREEN);
        params.weight = 0;
        layout.addView(begin, params);

        TextView hour = new TextView(this);
        hour.setText(String.valueOf(day+1));
        hour.setTextSize(30);
        hour.setGravity(Gravity.CENTER);
//        hour.setBackgroundColor(Color.GREEN);
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        layout.addView(hour, params);

        TextView end = new TextView(this);
        end.setText("14:25");
        end.setGravity(Gravity.CENTER);
//        end.setBackgroundColor(Color.GREEN);
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = 0;
        layout.addView(end, params);

        return layout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        SumireLog.d("Main.onActivityResult(int, int, Intent)");
        GlobalData.Debug.printLessons();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_LESSON || requestCode == EDIT_LESSON) {
            if (resultCode == RESULT_OK) {
                SumireLog.d("NEW_LESSON RESULT_OK");
                int hour = data.getIntExtra("hour", -1);
                int day = GlobalData.getShowenDay();
                RightCell rc = rightCells[hour];
                rc.update();
                if (requestCode == NEW_LESSON) {
                    rc.setOnClickListener(new EditLessonClickListener(day, hour, this, rc));
                }
            }
        }
    }

    private class RightCell extends LinearLayout {
        private int day;
        private int hour;
        private TextView name;
        private TextView teacher;
        private TextView room;

        public RightCell(int day, int hour) {
            super(ShowLessonActivity.this);
            this.day = day;
            this.hour = hour;
            Lesson lesson = GlobalData.getLesson(day, hour);
            if (lesson == null)
                setOnClickListener(new NewLessonClickListener(day, hour, ShowLessonActivity.this, this));
            else
                setOnClickListener(new EditLessonClickListener(day, hour, ShowLessonActivity.this, this));
//            setBackgroundColor(Color.BLUE);
            setOrientation(LinearLayout.VERTICAL);
            ViewGroup.LayoutParams params;

            name = new TextView(ShowLessonActivity.this);
            name.setTextSize(23);
//            name.setBackgroundColor(Color.GREEN);
            params = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            addView(name, params);

            teacher = new TextView(ShowLessonActivity.this);
            teacher.setTextSize(20);
//            teacher.setBackgroundColor(Color.GREEN);
            addView(teacher, params);

            room = new TextView(ShowLessonActivity.this);
            room.setTextSize(20);
//            room.setBackgroundColor(Color.GREEN);
            addView(room, params);

            if (lesson != null) {
                name.setText(lesson.name);
                teacher.setText(lesson.teacher);
                room.setText(lesson.room);
            }
        }

       public void update() {
            SumireLog.d("Main.update()");
            Lesson lesson = GlobalData.getLesson(day, hour);

            if (lesson != null) {
                SumireLog.d("update: " + lesson);
                name.setText(lesson.name);
                teacher.setText(lesson.teacher);
                room.setText(lesson.room);
            } else {
                removeAllViews();
            }
        }
    }
}
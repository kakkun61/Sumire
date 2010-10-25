package kakkun61.sumire;

import kakkun61.sumire.util.SumireLog;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.createDefaultSharedPreferences(this);
        setContentView(R.layout.main);
        Lesson[][] lessons = GlobalData.getLessons();
        int day = GlobalData.SUNDAY;
        TableLayout table = (TableLayout)findViewById(R.id.talbe);
        for (int i=0; i<GlobalData.getDayLessonsCount(); i++) {
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.CYAN);
            TableRow.LayoutParams params = new TableRow.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
            params.weight = 0;
            params.setMargins(5, 2, 2, 2);
            row.addView(createLeftCell(i), params);
            View v = createRightCell(i, lessons[day][i]);
            if (v != null) {
                params.weight = 1;
                params.setMargins(2, 2, 2, 5);
                row.addView(v, params);
            }
            TableLayout.LayoutParams tlParams = new TableLayout.LayoutParams();
            tlParams.height = LayoutParams.WRAP_CONTENT;
            table.addView(row, tlParams);
        }
    }

    private View createLeftCell(int i) {
//        SumireLog.d("call Main.createLeftCell(" + i + ")");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setBackgroundColor(Color.BLUE);
        layout.setOnClickListener(new LessonClickListener());

        TextView begin = new TextView(this);
        begin.setText("12:55");
        begin.setGravity(Gravity.CENTER);
        begin.setBackgroundColor(Color.GREEN);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = 0;
        layout.addView(begin, params);

        TextView hour = new TextView(this);
        hour.setText(String.valueOf(i));
        hour.setGravity(Gravity.CENTER);
        hour.setBackgroundColor(Color.GREEN);
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        layout.addView(hour, params);

        TextView end = new TextView(this);
        end.setText("14:25");
        end.setGravity(Gravity.CENTER);
        end.setBackgroundColor(Color.GREEN);
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = 0;
        layout.addView(end, params);

        return layout;
    }

    private View createRightCell(int i, Lesson lesson) {
//        SumireLog.d("call Main.createRightCell(" + i + ", " + (lesson==null? "null": lesson.name) +  ")");
        if (lesson != null) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setOnClickListener(new LessonClickListener());
            layout.setBackgroundColor(Color.BLUE);

            TextView name = new TextView(this);
            name.setTextSize(20);
            name.setText(lesson.name);
            layout.addView(name);
            name.setBackgroundColor(Color.GREEN);

            TextView teacher = new TextView(this);
            teacher.setTextSize(20);
            teacher.setText(lesson.teacher);
            teacher.setBackgroundColor(Color.GREEN);
            layout.addView(teacher);

            TextView room = new TextView(this);
            room.setTextSize(20);
            room.setText(lesson.room);
            room.setBackgroundColor(Color.GREEN);
            layout.addView(room);

            return layout;
        }
//        return null;
        View v = new TextView(this);
        v.setBackgroundColor(Color.GREEN);
        return v;
    }

}
package kakkun61.sumire;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
        setContentView(R.layout.main);
        TableLayout table = (TableLayout)findViewById(R.id.talbe);
        for (int i=0; i<GlobalData.getDayLessonsCount(); i++) {
            TableRow row = new TableRow(this);
            row.addView(createLsftCell(i));
            View v = createRightCell(i);
            if (v != null)
                row.addView( v );
            table.addView(row);
        }
    }

    private View createLsftCell(int i) {
        TextView hour = new TextView(this);
        hour.setText(String.valueOf(i));
        return hour;
    }

    private View createRightCell(int i) {
        Lesson lesson = GlobalData.getLessons()[0][i];
        if (lesson != null) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView name = new TextView(this);
            TextView teacher = new TextView(this);
            TextView room = new TextView(this);

            name.setText(lesson.name);
            teacher.setText(lesson.teacher);
            room.setText(lesson.room);

            layout.addView(name);
            layout.addView(teacher);
            layout.addView(room);

            return layout;
        }
        return null;
    }

}
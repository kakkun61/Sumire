package kakkun61.sumire;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LessonClickListener implements OnClickListener {
    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "test", Toast.LENGTH_SHORT).show();
    }
}

package kakkun61.sumire;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class LessonClickListener implements OnClickListener {
    public void onClick(final View view) {
//        Toast.makeText(view.getContext(), "test", Toast.LENGTH_SHORT).show();
    	String[] str_items = {"Red","Green","Blue"};    	
    	new AlertDialog.Builder(view.getContext())
    	.setTitle("Please Select Color")
    	.setItems(str_items, new DialogInterface.OnClickListener(){
    		public void onClick(DialogInterface dialog, int which) {
    			new AlertDialog.Builder(view.getContext())
    			.setTitle("which=" + which)
    			.show();
    		}
    	})
    	.setNegativeButton("Cancel", null/*new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
			}
    	}*/)
    	.show();
    }
}

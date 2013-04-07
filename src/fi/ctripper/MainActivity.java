package fi.ctripper;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public static final String EXTRA_MESSAGE = "fi.ctrip.MESSAGE1";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
//    public void sendMessage(View view){
//    	Intent intent = new Intent(this, DisplayMessageActivity.class);
//    	EditText text = (EditText) findViewById(R.id.editText1);
//    	String message = text.getText().toString();
//    	intent.putExtra(EXTRA_MESSAGE, message);
//    	startActivity(intent);    	
//    }
    
    public void listAlbums(View view){
    	
    }
    
    public void createNewAlbum(View view){
    	Intent intent = new Intent(this, EditAlbum.class);
    	startActivity(intent);
    }
}

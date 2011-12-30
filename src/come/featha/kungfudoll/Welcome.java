package come.featha.kungfudoll;

import android.app.Activity;
import android.os.Bundle;
import android.widget.VideoView;

public class Welcome extends Activity
{
	private VideoView vView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
         vView = (VideoView)findViewById(R.id.videoView1);
    }
}

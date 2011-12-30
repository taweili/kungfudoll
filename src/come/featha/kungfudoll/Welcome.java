package come.featha.kungfudoll;

import android.app.Activity;
import android.net.Uri;
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
         // vView.setVideoURI(Uri.parse("android.resource://come.featha.kungfudoll/raw/hit"));
         vView.setVideoURI(Uri.parse("http://blog.ardublock.com/wp-content/uploads/2011/12/hit.mp4"));
         vView.start();
    }
}

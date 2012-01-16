package come.featha.kungfudoll;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class Welcome extends Activity {
	private VideoView vView;
	private TextView sView; // score view
	private TextView tView; // time view
	private ServerSocket serverSocket;
    private Handler handler = new Handler();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		sView = (TextView) findViewById(R.id.textView2);
		tView = (TextView) findViewById(R.id.TextView01);

		Thread st = new Thread(new ServerThread());
		st.start();

		vView = (VideoView) findViewById(R.id.videoView1);
		MediaController controller = new MediaController(this);
		vView.setMediaController(controller);
		String filePath="android.resource://" + getPackageName() + "/" + R.raw.hit;

		vView.setVideoURI(Uri.parse(filePath));
		//vView.setVideoURI(Uri.parse("http://blog.ardublock.com/wp-content/uploads/2012/01/hit3.mp4"));
		vView.requestFocus();
		vView.start();
	}
	
	@Override
	public void onStop() {
		super.onStop();

		try {
			serverSocket.close();
		} catch (Exception e) {
		  Log.e("socket", e.getMessage());
		}
	}

	@SuppressWarnings("unused")
	private String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("ServerActivity", ex.toString());
		}
		return null;
	}

	public class ServerThread implements Runnable {
		private String line = null;

		public void run() {
			try {
				serverSocket = new ServerSocket(8080);
				handler.post(new Runnable() {
					public void run() {
						sView.setText("9");
					}
				});
				while (true) {
					Socket client = serverSocket.accept();

					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        while ((line = in.readLine()) != null) {
        					handler.post(new Runnable() {
        						public void run() {
        							String str[] = line.split(" ");
        							sView.setText(str[0]);
        							tView.setText(str[1]);
        						}
        					});                        	
                        }
					} catch (Exception e) {
						Log.e("socket", e.getMessage());
					}
					client.close();
				}
			} catch (Exception e) {
				Log.e("socket", "exception: " + e.getMessage());
			}
		}

	}
}

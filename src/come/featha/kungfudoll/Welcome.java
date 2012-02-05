package come.featha.kungfudoll;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Scanner;

public class Welcome extends Activity {

	private ServerSocket serverSocket;

    
    private WebView wView;
    
    private static String score = null;
    private static String time = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//full-screen
    	
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.main);

		Thread st = new Thread(new ServerThread());
		st.start();
		
		
		wView = (WebView)this.findViewById(R.id.webView1);
		
		wView.getSettings().setJavaScriptEnabled(true);
		wView.getSettings().setPluginsEnabled(true);
		wView.getSettings().setPluginState(PluginState.ON);
		
    	
        
		//wView.loadUrl("http://192.168.11.2:8090/release.html");
		wView.loadUrl("http://127.0.0.1:8090/release.html");
		//wView.loadUrl("http://192.168.11.3/flash/release.html");

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

		public void run() {
			try 
			{
				serverSocket = new ServerSocket(8080);
				
				
				while (true)
				{
					try
					{
						if (serverSocket.isClosed())
						{
							break;
						}
						Socket client = serverSocket.accept();
						Log.e("socket", "a client in: " + client.getRemoteSocketAddress() + " :" + client.getPort());
						Scanner sin = new Scanner(client.getInputStream());
						String line = sin.nextLine();
						
						score = line;
						wView.loadUrl("javascript:updateScore(\"" + score + "\");");
						Log.e("socket", "update it");

						sin.close();
						client.close();
					}
					catch (Exception e)
					{
						Log.e("socket", "sth: " + e.getMessage());
					}
				}
			}
			catch (Exception e) 
			{
				Log.e("socket", "exception: " + e.getMessage());
			}
		}

	}
}

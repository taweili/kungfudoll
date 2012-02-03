package come.featha.kungfudoll;

import android.content.Context;

import com.iflytek.speech.SynthesizerPlayer;

public class Tts
{
	public static Context context;
	public static void setContext(Context context)
	{
		Tts.context = context;
	}
	public static void sayTest(String text)
	{
		SynthesizerPlayer player = SynthesizerPlayer.createSynthesizerPlayer(context, "appid=4f2b5960");
		player.setVoiceName("xiaoyan");
		player.playText(text, "ent=vivi21,bft=5", null);
	}
}

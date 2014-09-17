package ca.yuey.noteprime301;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

public class Helper
{
	/*========================================================================
	 * 	Logging
	 */
	public static final boolean DEBUG = true;
	public static final boolean SHOW_WARNINGS = true;

	/**
	 * Logs a message with the tag "301Debug" using Log.d if debugging is enabled.
	 * @param s - The string to log.
	 */
	public static final void logd
	(String s)
	{
		if (Helper.DEBUG) {
			Log.d("301Debug", s);
		}
	}
	
	/**
	 * Logs a message using Log.d if debugging is enabled.
	 * @param msg - The message to log.
	 * @param tag - The tag to use with the message.
	 */
	public static final void logd
	(String msg, String tag)
	{
		if (Helper.DEBUG) {
			Log.d(tag, msg);
		}
	}

	/**
	 * Logs a message with the tag "301Info" using Log.i if debugging is enabled.
	 * @param s - The warning to log.
	 */
	public static final void logi
	(String s)
	{
		if (Helper.DEBUG) {
			Log.i("301Info", s);
		}
	}
	
	/**
	 * Logs a warning with the tag "301Warning" using Log.w if SHOW_WARNINGS is true.
	 * @param s - The error to log.
	 */
	public static final void logw
	(String s)
	{
		if (Helper.SHOW_WARNINGS) {
			Log.w("301Warning", s);
		}
	}

	/**
	 * Logs an error with the tag "301Error".
	 * @param s
	 */
	public static final void loge
	(String s)
	{
		Log.e("301Error", s);
	}
	
	/*========================================================================
	 * 	Fontify
	 */
	/**
	 * Applies a font asset to the given TextView
	 * @param activity - The current activity (used to retrieve the applications assets).
	 * @param tv - The text view to fontify.
	 * @param assetName - The relative path of the font asset.
	 */
	public static final void fontify
	(Activity activity, TextView tv, String assetName)
	{
		Typeface font = Typeface.createFromAsset(activity.getAssets(), assetName);
		tv.setTypeface(font);
	}
}
package ua.hneu.languagetrainer;

import android.app.Application;
import android.util.Log;

public class App extends Application {
    private static String TAG_DEBUG = "App";
	
    public String str = "herwtwrew"; 
    @Override
	public void onCreate() {
		Log.d(TAG_DEBUG, "onCreate");
		super.onCreate();
	}

}

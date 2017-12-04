package com.example.listviewdemo1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class Test extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new Thread(new MyThred()).start();	
		}
	class MyThred implements Runnable{

		@Override
		public void run() {
			int i=1;
			while(i<=2){
				String strJson = "{\"TrafficLightId\":" +2+ "}";
				String result = HttpClients.sendMessage("http://192.168.1.243:8080/transportservice/type/jason/action/GetTrafficLightConfigAction.do", strJson);
				Log.i("-----", result);
				i++;
			}
			
		}
		
	}

}

package com.ForestTeamDesign.FX;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
//import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Select extends Activity {
    private Button button1;
    private Button button2;
    private Button button3;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selectb);
		button1=(Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener()
		{
		  public void onClick(View v) {
				// TODO Auto-generated method stub
			//	String message = "2";
	          //sendMessage(message);
	          //Log.e(TAG, message);
			  Intent intent = new Intent(Select.this, BluetoothChat.class);
				startActivity(intent);
				Select.this.finish();
			}
		}
	 
		);
		button2=(Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener()
		{
		  public void onClick(View v) {
				// TODO Auto-generated method stub
			//	String message = "2";
	          //sendMessage(message);
	          //Log.e(TAG, message);
			  Intent intent = new Intent(Select.this, GPRSChat.class);
				startActivity(intent);
				Select.this.finish();
			}
		}
	 
		);
		button3=(Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener()
		{
		  public void onClick(View v) {
				// TODO Auto-generated method stub
			//	String message = "2";
	          //sendMessage(message);
	          //Log.e(TAG, message);
			  Intent intent = new Intent(Select.this, BluetoothChat.class);
				startActivity(intent);
				Select.this.finish();
			}
		}
	 
		);
		
		
		
		
	}

	
	

}
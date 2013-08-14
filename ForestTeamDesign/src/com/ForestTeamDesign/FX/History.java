package com.ForestTeamDesign.FX;
import com.ForestTeamDesign.FX.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class History extends Activity{
	//private Button button1,button2,button3;
	//设置按钮未设计
	
	//在此activity里面显示历史数据，打开文件，表格显示或者曲线显示
	//表格可以通过简单的textview里面set数据来显示
	//曲线通过surfaceview或者其他方式显示
	public void onCreat(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
	}
	
	
	
	
	
	
	
}

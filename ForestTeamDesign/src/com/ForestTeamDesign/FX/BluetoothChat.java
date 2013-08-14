/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 在此activity里面完成蓝牙相关的所有功能，进行蓝牙打印机&温湿度箱的连接，选择打印文件，
 * 按键1：获取温湿度箱子内部的数据
 * 按键2：打印选择的文件或者数据
 */
/**
 * This is the main Activity that displays the current chat session.
 */
	public class BluetoothChat extends Activity {
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    // 从BluetoothChatService Handler发送的消息类型
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // 从BluetoothChatService Handler接收消息时使用的键名(键-值模型)
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent请求代码（请求链接，请求可见）
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Layout Views
    private TextView mTitle;
//	private ListView mConversationView;


    // 链接的设备的名称
    // Name of the connected device
	private String mConnectedDeviceName = null;
    // 本地蓝牙适配器
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // 聊天服务的对象
    // Member object for the chat services
    private BluetoothChatService mChatService = null;
    // 标志位
    // Array adapter for the conversation thread
 //	private ArrayAdapter<String> mConversationArrayAdapter;
		// String buffer for outgoing messages
//	private StringBuffer mOutStringBuffer;
	
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private TextView tv6;
	
	private int mFlagDataType = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");

        // 设置窗口布局
        // Set up the window layout 设置Activity的标题栏格式Window.
        //FEATURE_CUSTOM_TITLE普通、Window.FEATURE_NO_TITLE无标题栏、Window.FEATURE_INDETERMINATE_PROGRESS进度条
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        //设置标题栏的布局资源
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        // 设置自定义title布局
        // Set up the custom title
        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.app_name);
        mTitle = (TextView) findViewById(R.id.title_right_text);
         
        // 得到一个本地蓝牙适配器
        // Get local Bluetooth adapter
        //从根本上讲，这是你的出发点，所有的蓝牙行动。一旦你的本地适配器，getBondedDevices（）所有配对设备对象设置;  
        //与startDiscovery（）启动设备可被发现，或建立一个BluetoothServerSocket监听与listenUsingRfcommWithServiceRecord（字符串，UUID的）传入的连接请求。
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 如果适配器为null，则不支持蓝牙
        ////If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {  
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();  
            finish();  
            return;  
        }
    }  
    
    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // 如果蓝牙没有打开，则请求打开
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {  
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);  
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);  
            
        // 否则，设置聊天会话
        // Otherwise, setup the chat session
        } else {
            if (mChatService == null) 
            	setupChat();
            
        		// 启动DeviceListActivity查看设备并扫描
            	// Launch the DeviceListActivity to see devices and do scan
            	Intent serverIntent = new Intent(this, DeviceListActivity.class);
            	startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
        	// 如果当前状态为STATE_NONE，则需要开启蓝牙聊天服务
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
            	//STATE_NONE及刚创建为启动状态，如果是从后台切换到前台应为STATE_LISTEN,无需再次启动  
            	// 开始一个蓝牙聊天服务
              // Start the Bluetooth chat services
              mChatService.start();
            }
        }
    }   
    
    private void setupChat() {
        Log.d(TAG, "setupChat()");
/***/        
    tv1 = (TextView)findViewById(R.id.tv1);
	tv2 = (TextView)findViewById(R.id.tv2);    
	tv3 = (TextView)findViewById(R.id.tv3);
	tv4 = (TextView)findViewById(R.id.tv4);
	tv5 = (TextView)findViewById(R.id.tv5);
	tv6 = (TextView)findViewById(R.id.tv6);
	
	ImageButton btn1 = (ImageButton)this.findViewById(R.id.imageBtn1);   
	btn1.setOnClickListener(new OnClickListener()
	{
	  public void onClick(View v) {
			// TODO Auto-generated method stub
			String message = "1";
          sendMessage(message);
          Log.e(TAG, message);
          
		}
	}
 
	);
	
	ImageButton btn2 = (ImageButton)this.findViewById(R.id.imageBtn2);   
	btn2.setOnClickListener(new OnClickListener()
	{
	  public void onClick(View v) {
			// TODO Auto-generated method stub
			String message = "2";
          sendMessage(message);
          Log.e(TAG, message);
          
		}
	}
 
	);
	
	ImageButton btn3 = (ImageButton)this.findViewById(R.id.imageBtn3);   
	btn3.setOnClickListener(new OnClickListener()
	{
	  public void onClick(View v) {
			// TODO Auto-generated method stub
			String message = "3";
          sendMessage(message);
          Log.e(TAG, message);
          
		}
	}
 
	);  
        // 初始化BluetoothChatService并执行蓝牙连接
        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(this, mHandler);
    }
   
    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        
        // 判断扫描模式是否为既可被发现又可以被连接
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
        	// 请求可见状态
        	Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            // 添加附加属性，可见状态的时间
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    private void sendMessage(String message) {
    	// 检查是否处于连接状态
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // 如果输入的消息不为空才发送，否则不发送
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);
        }
    }


    public static String intToString(int value)
    {
    	Integer integer = new Integer(value);
    	return integer.toString();
    }
    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
    	
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                	// 设置状态为已经链接
                    mTitle.setText(R.string.title_connected_to);
                    // 添加设备名称
                    mTitle.append(mConnectedDeviceName);
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                	// 设置正在链接 
                    mTitle.setText(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                	// 处于监听状态或者没有准备状态，则显示没有链接
                    mTitle.setText(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
            	Log.d(TAG, "<-----MESSAGE_WRITE----->");
                break;
            case MESSAGE_READ:
            	Log.d(TAG, "<-----MESSAGE_READ----->");
            	int bytes = msg.arg1;
            	byte[] buffer = new byte[10];
            	buffer = (byte[])msg.obj;
            	Log.d(TAG, bytes + "<-----MESSAGE_READ----->" + buffer[0] + "~" + buffer[1]+ "~" + buffer[2]
            			+ "~" + buffer[3]+ "~" + buffer[4]+ "~" + buffer[5]);
            	switch(buffer[0])
            	{
            	case 'F':           		
            		mFlagDataType = 1;
            		for(int i=0;i<bytes;i++){
            			buffer[i]=0;
            		}
            		break;
            	case 'E':
            		mFlagDataType = 2;
            		for(int i=0;i<bytes;i++){
            			buffer[i]=0;
            		}            		
            		break;
            	case 'D':
            		mFlagDataType = 3;
            		for(int i=0;i<bytes;i++){
            			buffer[i]=0;
            		}
            		break;
            	}
            	
            	switch(mFlagDataType)
            	{
            	case 1:
            		tv1.setText(String.valueOf(buffer[0]) + String.valueOf(buffer[1]) + "." + String.valueOf(buffer[2]) + "℃");
            		tv2.setText(String.valueOf(buffer[3]) + String.valueOf(buffer[4]) + "%");            		
            		break;
            	case 2:
            		tv3.setText(String.valueOf(buffer[0]) + "." + String.valueOf(buffer[1]) + String.valueOf(buffer[2]) + "K pcs");
            		switch(buffer[3])
            		{
            		case 'Y':
            			tv4.setText("优秀");
            			break;
            		case 'L':
            			tv4.setText("良好");
            			break;
            		case 'Q':
            			tv4.setText("轻度污染，注意开窗通气");
            			break;
            		case 'W':
            			tv4.setText("中度污染，开窗通气、减少运动");
            			break;
            		case 'K':
            			tv4.setText("重度污染，通气，建议佩戴口罩，多吃润肺水果");
            			break;
            		default:
            			break;
            		}
            		break;
            	case 3:
            		tv5.setText(String.valueOf(buffer[0]) + String.valueOf(buffer[1]) + String.valueOf(buffer[2]) + "*10^（-3）ppm" );
            		switch(buffer[3])
            		{
            		case 'Z':
            			tv6.setText("正常");
            			break;
            		case 'C':
            			tv6.setText("超标");
            			break;
            		case 'G':
            			tv6.setText("严重超标");
            			break;
            		default :
            			break;
            		}
            		break;
            	}
            	break;
            case MESSAGE_DEVICE_NAME:
            	// 保存链接的设备名称，并显示一个toast提示
                // save the connected device's name
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "Connected to "
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
            	// 处理链接(发送)失败的消息
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };


     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
        	// 当DeviceListActivity返回设备连接
            // When DeviceListActivity returns with a device to connect
            if (resultCode == Activity.RESULT_OK) {
            	// 从Intent中得到设备的MAC地址
                // Get the device MAC address
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // 得到蓝牙设备对象
                // Get the BLuetoothDevice object
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // 尝试连接这个设备 
                // Attempt to connect to the device
                mChatService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
        	// 在请求打开蓝牙时返回的代码
            // When the request to enable Bluetooth returns
            if (resultCode == Activity.RESULT_OK) {
            	// 蓝牙已经打开，所以设置一个聊天会话
                // Bluetooth is now enabled, so set up a chat session
                setupChat();
                
                
        		// 启动DeviceListActivity查看设备并扫描
            	// Launch the DeviceListActivity to see devices and do scan
            	Intent serverIntent = new Intent(this, DeviceListActivity.class);
            	startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            } else {
            	// 请求打开蓝牙出错
                // User did not enable Bluetooth or an error occured
                Log.d(TAG, "BT not enabled");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

     
    
     
     
    // 创建一个菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    // 处理菜单事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.scan:
        	// 启动DeviceListActivity查看设备并扫描
            // Launch the DeviceListActivity to see devices and do scan
            Intent serverIntent = new Intent(this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            return true;
        case R.id.discoverable:
        	// 确保设备处于可见状态
            // Ensure this device is discoverable by others
            ensureDiscoverable();
            return true;
        }
        return false;
    }    

public  void test(){
	Resources res = this.getResources();
	String strings[] = res.getStringArray(R.array.test_array);
	for (String s:strings)
	{
		Log.d("example",s);
	}	
}
}	


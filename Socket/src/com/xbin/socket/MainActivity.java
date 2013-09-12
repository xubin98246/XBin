package com.xbin.socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import android.R.array;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.xbin.socket.MachInfo;

public class MainActivity extends Activity implements Runnable {
    private TextView tv_msg = null;
    private EditText ed_msg = null;
    private Button btn_send = null;
    private Button btn_send2=null;
//    private Button btn_login = null;
    private static final String HOST ="210.83.80.208";
    private static final int PORT = 3700;
    private Socket socket = null;
    private BufferedInputStream in = null;
    private OutputStream out=null;
    private String content="";
    private byte COMMAND_HEAD=(byte)0xBC;
    private byte COMMAND_DEVICE=0x12;
    private byte COMMAND_HISDATA=0x13;
    private ArrayList<Byte> arrdata=new ArrayList<Byte>();
    private byte[] cont=new byte[6];
    private byte[] arrdata_byte;
	private byte[] result;
	private static final int TITLE_MAX_SINGLE_END=41;

    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv_msg = (TextView) findViewById(R.id.TextView);
        ed_msg = (EditText) findViewById(R.id.EditText01);
//        btn_login = (Button) findViewById(R.id.Button01);
        btn_send = (Button) findViewById(R.id.Button02);
        btn_send2=(Button)findViewById(R.id.Button03);
        ed_msg.setText("7DC303611");
      

        // 启动线程执行socket连接任务
        new Thread(socketinit).start();       
        
        arrdata.add(COMMAND_HEAD);

        btn_send.setOnClickListener(new Button.OnClickListener() {       
            @Override
            	public void onClick(View v) {
            	
                arrdata.add(COMMAND_DEVICE);
            	 String msg = ed_msg.getText().toString();
            	 //ed_msg.setText("7DC303611");
            	 arrdata.add((byte)(msg.length()+1));
                 
            	 for(int  i=0;i<msg.length();i++){
            		 try {
						arrdata.add(msg.getBytes("ASCII")[i]);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	 }
            	 arrdata.add((byte)0);
            	 arrdata_byte=new byte[arrdata.size()];
             	for (int i=0;i<arrdata.size();i++){
           	 	    arrdata_byte[i]=arrdata.get(i);
       				}      
    
 
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
     
                    	try {
							out.write(arrdata_byte);
							out.flush();							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}                                                  	
                    }
                }                                                 
            }
        });
        
        btn_send2.setOnClickListener(new Button.OnClickListener() {       
            @Override
            	public void onClick(View v) {
           
                arrdata.add(COMMAND_HISDATA);
                arrdata.add((byte)0x17);
            	 String msg = ed_msg.getText().toString();            	
            	 arrdata.add((byte)(msg.length()+1));                 
            	 for(int  i=0;i<msg.length();i++){
            		 try {
						arrdata.add(msg.getBytes("ASCII")[i]);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	 }
            	 //[BC 13 17 0A]7DC303611[00 7D D8 10 0D 32 00 7D D8 10 0E 14 00]
            	 arrdata.add((byte)0);
//            	 arrdata.Add(time.GetYear() >> 4);
//            	 arrdata.Add(((time.GetYear() & 0x0f) << 4) + time.GetMonth());
//            	 arrdata.Add(time.GetDay());
//            	 arrdata.Add(time.GetHour());
//            	 arrdata.Add(time.GetMinute());
            	 
            	 arrdata.add((byte)0x7D);
            	 arrdata.add((byte)0xD8);
            	 arrdata.add((byte)0x10);
            	 arrdata.add((byte)0x0D);
            	 arrdata.add((byte)0x32);
            	 arrdata.add((byte)0x00);
            	 
              	 arrdata.add((byte)0x7D);
            	 arrdata.add((byte)0xD8);
            	 arrdata.add((byte)0x10);
            	 arrdata.add((byte)0x0E);
            	 arrdata.add((byte)0x14);
            	 arrdata.add((byte)0x00);
            	 
            	 
            	 
            	 arrdata_byte=new byte[arrdata.size()];
             	for (int i=0;i<arrdata.size();i++){
           	 	    arrdata_byte[i]=arrdata.get(i);
       				}      
    
 
                if (socket.isConnected()) {
                    if (!socket.isOutputShutdown()) {
     
                    	try {
							out.write(arrdata_byte);
							out.flush();							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}                                                  	
                    }
                }
            }
        });
        
        new Thread(MainActivity.this).start();
    }
    
    Runnable socketinit = new Runnable(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			socketinit();           
	}
    };
    
    public void socketinit() {
    	
    	  try {     		   
              socket = new Socket(HOST, PORT);
              in =new BufferedInputStream( new DataInputStream(socket.getInputStream()));
              out = socket.getOutputStream();
              
          } catch (IOException ex) {        	  
              ex.printStackTrace();
              ShowDialog("login exception" + ex.getMessage());
          }    
	}

    public void ShowDialog(String msg) {
        new AlertDialog.Builder(this).setTitle("notification").setMessage(msg)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                }).show();
    }

    @Override
    public void run() {   
    	// TODO Auto-generated method stub
    	while (true) {
        try {
                //tv_msg.setText("");
            	if (socket.isConnected()) {
                   if (!socket.isInputShutdown()) {
                     if(in.read(cont, 0, 6)!=-1){
                      if((cont[0]==(byte)0xBC)&&(cont[1]==COMMAND_DEVICE||cont[1]==COMMAND_HISDATA)){ 
                    	  int len1=cont[3]&0xff;
                    	      len1*=256*256;
                    	  int len2=cont[4]&0xff;
                    	      len2*=256;
                    	  int len3=cont[5]&0xff;
                    	 int length=len1+len2+len3;
                    	 // System.out.println("###"+length+"###"+len3+"###"+len2+"###"+len1);
                         result=new byte[length];
                         if (in.read(result) !=-1){
                            for (byte ch : result) {
                        	String hex = Integer.toHexString(ch & 0xFF );  
                            
                            if (hex.length() == 1) { 
                            hex='0' + hex;
                            }else{
                            }
                            content+=hex;
                            
                            }
                            						     //Looper.prepare();
                             mHandler.sendMessage(mHandler.obtainMessage());
                            // Looper.loop();
                        }
                        }
                      }
                      else{
                    	  
                      }
                    
                     }
                    }
                }catch (Exception e) {
                   e.printStackTrace();
                }
     }
    }

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_msg.setText(content);
            int temp=0,index=0;            
            String strTemp="";
            
            MachInfo machInfo=new MachInfo();
            temp=result[index++]&0xff;
            temp*=256;
            machInfo.nMach=result[index++]&0xff;  //仪器个数
            System.out.println(machInfo.nMach);
        	int nCount = machInfo.nMach;
            int nTotal = index;
            
        	machInfo.nLenPerMach=new int[nCount];
        	machInfo.nStr=new int[nCount];
        	machInfo.Str=new String[nCount];
        	machInfo.nUnit=new int[nCount];
        	
            for(int i=0;i<nCount;i++){
            	temp=result[index++]&0xff;
                temp*=256;
                machInfo.nLenPerMach[i]=temp+result[index++]&0xff;
                //System.out.println(Arrays.toString(machInfo.nLenPerMach));
              //device id
    			index += 2;
    			//corp id
    			index += 2;
    			//warehouse id
    			index += 2;
    			 System.out.println(strTemp); 
    			//标题(11byte->41byte)
    			for(int j=index;j<index+TITLE_MAX_SINGLE_END;j++){
    				strTemp+=Integer.toHexString(result[j]&0xff);
    			}
 			     System.out.println(strTemp);  
    			index+=TITLE_MAX_SINGLE_END;
    			
    			machInfo.Str[i]="";    			
    			machInfo.nStr[i]=result[index++];
    		    while(result[index]!=0){
    		    	machInfo.Str[i]+=(char)result[index++];
    		    }
    		    index++;
    		  //地点id(1byte) + 楼层(1byte)
    			index += 2;
    			//属性长度p(1byte)
    			temp = result[index++];
    			//属性(pbyte)
    			index += temp;
    			
    			//通道个数c(1byte)
    			machInfo.nUnit[i]=result[index++];

    			machInfo.arrChanID=new int[machInfo.nUnit[i]];      //通道id
    			machInfo.arrUnitName=new String[machInfo.nUnit[i]];	//单位名
    			machInfo.arrUnit=new String[machInfo.nUnit[i]];       //单位
    			machInfo.arrDecimal=new int[machInfo.nUnit[i]];    //小数位数
    			machInfo.arrColTop=new float[machInfo.nUnit[i]];       //报警上限
    			machInfo.arrColFloor=new float[machInfo.nUnit[i]];      //报警下限
    			machInfo.arrLimitTop=new float[machInfo.nUnit[i]];      //值上限
    			machInfo.arrLimitFloor=new float[machInfo.nUnit[i]];    //值下限
    			machInfo.arrUserMax=new float[machInfo.nUnit[i]];       //用户峰值
    			machInfo.arrIsGps=new boolean[machInfo.nUnit[i]];			//gps flag
    			machInfo.arrFormat=new String[machInfo.nUnit[i]];		//format
    			//通道循环
    			for(int a=0;a<machInfo.nUnit[i];a++){
    				machInfo.arrChanID[a]=result[index++];
    				
    				machInfo.arrUnitName[a]="";
    				for(int j=index;j<index+11;j++){
    					machInfo.arrUnitName[a]+=Integer.toHexString(result[j]&0xff);
    				}
    				System.out.println(machInfo.arrUnitName[a]);

    			}
            }
         System.out.println("####"+machInfo.nMach+"###"+Arrays.toString(machInfo.nLenPerMach));   
        }
    };

}




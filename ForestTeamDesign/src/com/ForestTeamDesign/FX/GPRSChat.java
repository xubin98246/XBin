package com.ForestTeamDesign.FX;

//import cn.limc.androidcharts.view.LineChart;
import java.util.ArrayList;
import java.util.List;

//import cn.limc.androidcharts.entity.OHLCEntity;

//import cn.limc.androidcharts.entity.OHLCEntity;
//import cn.limc.androidcharts.entity.StickEntity;

//import cn.limc.androidcharts.R;
//import cn.limc.androidcharts.entity.LineEntity;
//import cn.limc.androidcharts.view.LineChart;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
//import android.view.Menu;

public class GPRSChat extends Activity {
	LineChart machart;
	List<OHLCEntity> ohlc;
	//List<StickEntity> vol;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gprs);
		initOHLC();
		initMAChart();//此函数泽大公司项目需要用到
	}
	private void initMAChart()
	{
        this.machart = (LineChart)findViewById(R.id.machart);
        List<LineEntity> lines = new ArrayList<LineEntity>();
        
        //计算5日均线
        LineEntity MA5 = new LineEntity();
        MA5.setTitle("MA5");//用来显示最近两个小时、一天等等
        /*
         * 示例程序中此值未看到显示
         */
        MA5.setLineColor(Color.WHITE);//曲线颜色
        MA5.setLineData(initMA(5));//曲线上面的数据来自这里
        lines.add(MA5);//数据加到lines里面
        
        //计算10日均线
        LineEntity MA10 = new LineEntity();
        MA10.setTitle("MA10");
        MA10.setLineColor(Color.RED);
        MA10.setLineData(initMA(10));
        lines.add(MA10);
        
        //计算25日均线
        LineEntity MA25 = new LineEntity();
        MA25.setTitle("MA25");
        MA25.setLineColor(Color.GREEN);
        MA25.setLineData(initMA(25));
        lines.add(MA25);
        
		List<String> ytitle=new ArrayList<String>();
		ytitle.add("240");
		ytitle.add("250");
		ytitle.add("260");
		ytitle.add("270");
		ytitle.add("280");
		/*
		 * ytitle.add用于给machart图表的y轴添加纵坐标，可以用到
		 */
		List<String> xtitle=new ArrayList<String>();
		xtitle.add("9:00");
		xtitle.add("10:00");
		xtitle.add("11:00");
		xtitle.add("13:00");
		xtitle.add("14:00");
		xtitle.add("15:00");
		xtitle.add(" ");
		/*
		 * xtitle.add用于给machart图表的y轴添加纵坐标，可以用到
		 */
        machart.setAxisXColor(Color.LTGRAY);//x坐标颜色
		machart.setAxisYColor(Color.LTGRAY);//y坐标颜色
		machart.setBorderColor(Color.LTGRAY);//界线颜色
		machart.setAxisMarginTop(10);//坐标上边距
		machart.setAxisMarginLeft(20);//坐标做编剧
		machart.setAxisYTitles(ytitle);//将设定的ytitle赋值给machart
		machart.setAxisXTitles(xtitle);//
		machart.setLongitudeFontSize(10);//设置经线格子宽度
		machart.setLongitudeFontColor(Color.WHITE);//经线格子颜色
		machart.setLatitudeColor(Color.GRAY);//纬线+的颜色
		machart.setLatitudeFontColor(Color.WHITE);//纬线格子颜色
		machart.setLongitudeColor(Color.GRAY);//经线+的颜色
		machart.setMaxValue(280);//最大值
		machart.setMinValue(240);//最小值
		machart.setMaxPointNum(36);//
		machart.setDisplayAxisXTitle(true);//是否显示………………
		machart.setDisplayAxisYTitle(true);//
		machart.setDisplayLatitude(true);//
		machart.setDisplayLongitude(true);//
        
        //为chart1增加均线
        machart.setLineData(lines);//添加数据，泽大仪器公司用此函数进行数据显示
	}
	private void initOHLC(){
        List<OHLCEntity> ohlc = new ArrayList<OHLCEntity>();
        
        this.ohlc = new ArrayList<OHLCEntity>();
        ohlc.add(new OHLCEntity(246 ,248 ,235 ,235 ,20110825));
        ohlc.add(new OHLCEntity(240 ,242 ,236 ,242 ,20110824));
        ohlc.add(new OHLCEntity(236 ,240 ,235 ,240 ,20110823));
        ohlc.add(new OHLCEntity(232 ,236 ,231 ,236 ,20110822));
        ohlc.add(new OHLCEntity(240 ,240 ,235 ,235 ,20110819));
        ohlc.add(new OHLCEntity(240 ,241 ,239 ,240 ,20110818));
        ohlc.add(new OHLCEntity(242 ,243 ,240 ,240 ,20110817));
        ohlc.add(new OHLCEntity(239 ,242 ,238 ,242 ,20110816));
        ohlc.add(new OHLCEntity(239 ,240 ,238 ,239 ,20110815));
        ohlc.add(new OHLCEntity(230 ,238 ,230 ,238 ,20110812));
        ohlc.add(new OHLCEntity(236 ,237 ,234 ,234 ,20110811));
        ohlc.add(new OHLCEntity(226 ,233 ,223 ,232 ,20110810));
        ohlc.add(new OHLCEntity(239 ,241 ,229 ,232 ,20110809));
        ohlc.add(new OHLCEntity(242 ,244 ,240 ,242 ,20110808));
        ohlc.add(new OHLCEntity(248 ,249 ,247 ,248 ,20110805));
        ohlc.add(new OHLCEntity(245 ,248 ,245 ,247 ,20110804));
        ohlc.add(new OHLCEntity(249 ,249 ,245 ,247 ,20110803));
        ohlc.add(new OHLCEntity(249 ,251 ,248 ,250 ,20110802));
        ohlc.add(new OHLCEntity(250 ,252 ,248 ,250 ,20110801));
        ohlc.add(new OHLCEntity(250 ,251 ,248 ,250 ,20110729));
        ohlc.add(new OHLCEntity(249 ,252 ,248 ,252 ,20110728));
        ohlc.add(new OHLCEntity(248 ,250 ,247 ,250 ,20110727));
        ohlc.add(new OHLCEntity(256 ,256 ,248 ,248 ,20110726));
        ohlc.add(new OHLCEntity(257 ,258 ,256 ,257 ,20110725));
        ohlc.add(new OHLCEntity(259 ,260 ,256 ,256 ,20110722));
        ohlc.add(new OHLCEntity(261 ,261 ,257 ,259 ,20110721));
        ohlc.add(new OHLCEntity(260 ,260 ,259 ,259 ,20110720));
        ohlc.add(new OHLCEntity(262 ,262 ,260 ,261 ,20110719));
        ohlc.add(new OHLCEntity(260 ,262 ,259 ,262 ,20110718));
        ohlc.add(new OHLCEntity(259 ,261 ,258 ,261 ,20110715));
        ohlc.add(new OHLCEntity(255 ,259 ,255 ,259 ,20110714));
        ohlc.add(new OHLCEntity(258 ,258 ,255 ,255 ,20110713));
        ohlc.add(new OHLCEntity(258 ,260 ,258 ,260 ,20110712));
        ohlc.add(new OHLCEntity(259 ,260 ,258 ,259 ,20110711));
        ohlc.add(new OHLCEntity(261 ,262 ,259 ,259 ,20110708));
        ohlc.add(new OHLCEntity(261 ,261 ,258 ,261 ,20110707));
        ohlc.add(new OHLCEntity(261 ,261 ,259 ,261 ,20110706));
        ohlc.add(new OHLCEntity(257 ,261 ,257 ,261 ,20110705));
        ohlc.add(new OHLCEntity(256 ,257 ,255 ,255 ,20110704));
        ohlc.add(new OHLCEntity(253 ,257 ,253 ,256 ,20110701));
        ohlc.add(new OHLCEntity(255 ,255 ,252 ,252 ,20110630));
        ohlc.add(new OHLCEntity(256 ,256 ,253 ,255 ,20110629));
        ohlc.add(new OHLCEntity(254 ,256 ,254 ,255 ,20110628));
        ohlc.add(new OHLCEntity(247 ,256 ,247 ,254 ,20110627));
        ohlc.add(new OHLCEntity(244 ,249 ,243 ,248 ,20110624));
        ohlc.add(new OHLCEntity(244 ,245 ,243 ,244 ,20110623));
        ohlc.add(new OHLCEntity(242 ,244 ,241 ,244 ,20110622));
        ohlc.add(new OHLCEntity(243 ,243 ,241 ,242 ,20110621));
        ohlc.add(new OHLCEntity(246 ,247 ,244 ,244 ,20110620));
        ohlc.add(new OHLCEntity(248 ,249 ,246 ,246 ,20110617));
        ohlc.add(new OHLCEntity(251 ,253 ,250 ,250 ,20110616));
        ohlc.add(new OHLCEntity(249 ,253 ,249 ,253 ,20110615));
        ohlc.add(new OHLCEntity(248 ,250 ,246 ,250 ,20110614));
        ohlc.add(new OHLCEntity(249 ,250 ,247 ,250 ,20110613));
        ohlc.add(new OHLCEntity(254 ,254 ,250 ,250 ,20110610));
        ohlc.add(new OHLCEntity(254 ,255 ,251 ,255 ,20110609));
        ohlc.add(new OHLCEntity(252 ,254 ,251 ,254 ,20110608));
        ohlc.add(new OHLCEntity(250 ,253 ,250 ,252 ,20110607));
        ohlc.add(new OHLCEntity(251 ,252 ,247 ,250 ,20110603));
        ohlc.add(new OHLCEntity(253 ,254 ,252 ,254 ,20110602));
        ohlc.add(new OHLCEntity(250 ,254 ,250 ,254 ,20110601));
        ohlc.add(new OHLCEntity(250 ,252 ,248 ,250 ,20110531));
        ohlc.add(new OHLCEntity(253 ,254 ,250 ,251 ,20110530));
        ohlc.add(new OHLCEntity(255 ,256 ,253 ,253 ,20110527));
        ohlc.add(new OHLCEntity(256 ,257 ,253 ,254 ,20110526));
        ohlc.add(new OHLCEntity(256 ,257 ,254 ,256 ,20110525));
        ohlc.add(new OHLCEntity(265 ,265 ,257 ,257 ,20110524));
        ohlc.add(new OHLCEntity(265 ,266 ,265 ,265 ,20110523));
        ohlc.add(new OHLCEntity(267 ,268 ,265 ,266 ,20110520));
        ohlc.add(new OHLCEntity(264 ,267 ,264 ,267 ,20110519));
        ohlc.add(new OHLCEntity(264 ,266 ,262 ,265 ,20110518));
        ohlc.add(new OHLCEntity(266 ,267 ,264 ,264 ,20110517));
        ohlc.add(new OHLCEntity(264 ,267 ,263 ,267 ,20110516));
        ohlc.add(new OHLCEntity(266 ,267 ,264 ,264 ,20110513));
        ohlc.add(new OHLCEntity(269 ,269 ,266 ,268 ,20110512));
        ohlc.add(new OHLCEntity(267 ,269 ,266 ,269 ,20110511));
        ohlc.add(new OHLCEntity(266 ,268 ,266 ,267 ,20110510));
        ohlc.add(new OHLCEntity(264 ,268 ,263 ,266 ,20110509));
        ohlc.add(new OHLCEntity(265 ,268 ,265 ,267 ,20110506));
        ohlc.add(new OHLCEntity(271 ,271 ,266 ,266 ,20110505));
        ohlc.add(new OHLCEntity(271 ,273 ,269 ,273 ,20110504));
        ohlc.add(new OHLCEntity(268 ,271 ,267 ,271 ,20110503));
        for(int i= ohlc.size(); i > 0 ; i--){
        	this.ohlc.add(ohlc.get(i -1));
        }
	}
	
	
	
private List<Float> initMA(int days){
		
		if (days < 2){
			return null;
		}
		
        List<Float> MA5Values = new ArrayList<Float>();
        
    	float sum = 0;
    	float avg = 0;
        for(int i = 0 ; i < this.ohlc.size(); i++){
        	float close =(float)ohlc.get(i).getClose();
        	if(i< days){
        		sum = sum + close;
        		avg = sum / (i + 1f);
        	}else{
        		sum = sum + close - (float)ohlc.get(i-days).getClose();
        		avg = sum / days;
        	}
        	MA5Values.add(avg);
        }
        
        return MA5Values;
	}
	
	
	

}
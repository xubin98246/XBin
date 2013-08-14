/*
 * StickEntity.java
 * Android-Charts
 *
 * Created by limc on 2011/05/29.
 *
 * Copyright 2011 limc.cn All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ForestTeamDesign.FX;

/**
 * <p>
 * Entity data which is use for display a stick in CCSStickChart
 * </p>
 * <p>
 * StickChartのスティック表示用データです、高安�?を格納用オブジェクトです�?
 * </p>
 * <p>
 * CCSStickChart保存柱条表示用的高低值的实体对象
 * </p>
 * 
 * @author limc
 * @version v1.0 2011/05/29 12:24:49
 */
public class StickEntity {

	/**
	 * <p>
	 * High Value
	 * </p>
	 * <p>
	 * 高�?
	 * </p>
	 * <p>
	 * �?���?
	 * </p>
	 * 
	 */
	private double high;

	/**
	 * <p>
	 * Low Value
	 * </p>
	 * <p>
	 * 低�?
	 * </p>
	 * <p>
	 * �?���?
	 * </p>
	 * 
	 */
	private double low;

	/**
	 * <p>
	 * Date
	 * </p>
	 * <p>
	 * 日付
	 * </p>
	 * <p>
	 * 日期
	 * </p>
	 * 
	 */
	private int date;

	/**
	 * 
	 * <p>
	 * Constructor of StickEntity
	 * </p>
	 * <p>
	 * StickEntity类对象的构�?函数
	 * </p>
	 * <p>
	 * StickEntityのコンストラクタ�?
	 * </p>
	 * 
	 * @param high
	 *            <p>
	 *            High Value
	 *            </p>
	 *            <p>
	 *            高�?
	 *            </p>
	 *            <p>
	 *            �?���?
	 *            </p>
	 * @param low
	 *            <p>
	 *            Low Value
	 *            </p>
	 *            <p>
	 *            低�?
	 *            </p>
	 *            <p>
	 *            �?���?
	 *            </p>
	 * @param date
	 *            <p>
	 *            Date
	 *            </p>
	 *            <p>
	 *            日付
	 *            </p>
	 *            <p>
	 *            日期
	 *            </p>
	 */
	public StickEntity(double high, double low, int date) {
		super();
		this.high = high;
		this.low = low;
		this.date = date;
	}

	/**
	 * 
	 * <p>
	 * Constructor of StickEntity
	 * </p>
	 * <p>
	 * StickEntity类对象的构�?函数
	 * </p>
	 * <p>
	 * StickEntityのコンストラクタ�?
	 * </p>
	 * 
	 */
	public StickEntity() {
		super();
	}

	/**
	 * @return the high
	 */
	public double getHigh() {
		return high;
	}

	/**
	 * @param high
	 *            the high to set
	 */
	public void setHigh(double high) {
		this.high = high;
	}

	/**
	 * @return the low
	 */
	public double getLow() {
		return low;
	}

	/**
	 * @param low
	 *            the low to set
	 */
	public void setLow(double low) {
		this.low = low;
	}

	/**
	 * @return the date
	 */
	public int getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(int date) {
		this.date = date;
	}
}

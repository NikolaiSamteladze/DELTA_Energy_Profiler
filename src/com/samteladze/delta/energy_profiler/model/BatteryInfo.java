package com.samteladze.delta.energy_profiler.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

public class BatteryInfo {
	public Date timeStamp;
	public double batteryLevel;
	public int batteryVoltage;
	
	public BatteryInfo (Date timeStamp, double batteryLevel, int batteryVoltage) {
		this.timeStamp = timeStamp;
		this.batteryLevel = batteryLevel;
		this.batteryVoltage = batteryVoltage;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.US, "%.2f,%d", batteryLevel, batteryVoltage);
	}
	
	// Returns the current battery info with a time stamp
	public static BatteryInfo current(Context contex) {
		// Get battery information
		Intent batteryIntent = contex.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int rawlevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		double scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
		int voltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
		double level = rawlevel / scale;
		
		// Get current time stamp
		Calendar calendar = Calendar.getInstance();
		
		return new BatteryInfo(calendar.getTime(), level, voltage);
	}
}
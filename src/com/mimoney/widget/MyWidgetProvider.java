package com.mimoney.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

	private static String LOG_TAG = "MyWidgetProvider";
	public static String ACTION_WIDGET_PREVIOUS = "ActionReceiverPrevious";
	public static String ACTION_WIDGET_NEXT = "ActionReceiverNext";

	private List<AccountDetails> accDetails = null;
	private static int i = 0;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		Log.i(LOG_TAG, "onUpdate" + "i == "+i);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);

		Intent active = new Intent(context, MyWidgetProvider.class);
		active.setAction(ACTION_WIDGET_PREVIOUS);
		PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context,
				0, active, 0);
		remoteViews.setOnClickPendingIntent(R.id.btn_widget_previous,
				actionPendingIntent);

		active.setAction(ACTION_WIDGET_NEXT);
		actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
		remoteViews.setOnClickPendingIntent(R.id.btn_widget_next,
				actionPendingIntent);
		remoteViews.setTextViewText(R.id.txt_widget_bankName, accDetails.get(i)
				.getBankName());
		Log.i(LOG_TAG+"onUpdate", accDetails.get(i).getBankName());
		remoteViews.setTextViewText(R.id.txt_widget_savings, "£"
				+ Float.toString(accDetails.get(i).getSavingsAccountBalance()));
		Log.i(LOG_TAG+"onUpdate", Float.toString(accDetails.get(i).getSavingsAccountBalance()));
		remoteViews.setTextViewText(R.id.txt_widget_current, "£"
				+ Float.toString(accDetails.get(i).getCurrentAccountBalance()));
		Log.i(LOG_TAG+"onUpdate",Float.toString(accDetails.get(i).getCurrentAccountBalance()));
		
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

	}

	@Override
	public void onReceive(Context context, Intent intent) {
		updateValues();

		if (intent.getAction().equals(ACTION_WIDGET_PREVIOUS)) {
			i--;
			if (i < 0) {
				i = accDetails.size() - 1;
			}
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			ComponentName thisAppWidget = new ComponentName(context
					.getPackageName(), MyWidgetProvider.class.getName());
			int[] appWidgetIds = appWidgetManager
					.getAppWidgetIds(thisAppWidget);
			onUpdate(context, appWidgetManager, appWidgetIds);
			Log.i(LOG_TAG, accDetails.get(i).getBankName());
		} else if (intent.getAction().equals(ACTION_WIDGET_NEXT)) {
			i++;
			if (i >= accDetails.size()) {
				i = 0;
			}
			AppWidgetManager appWidgetManager = AppWidgetManager
					.getInstance(context);
			ComponentName thisAppWidget = new ComponentName(context
					.getPackageName(), MyWidgetProvider.class.getName());
			int[] appWidgetIds = appWidgetManager
					.getAppWidgetIds(thisAppWidget);
			onUpdate(context, appWidgetManager, appWidgetIds);
			Log.i(LOG_TAG, accDetails.get(i).getBankName());
		}
		

		super.onReceive(context, intent);

	}
	
	

	private void updateValues() {
		if (accDetails != null) {
			accDetails.clear();
		} else {
			accDetails = new ArrayList<AccountDetails>();
		}
		AccountDetails accOne = new AccountDetails();
		accOne.setBankName("HDFC");
		accOne.setCurrentAccountBalance(1000);
		accOne.setSavingsAccountBalance(500);
		AccountDetails accTwo = new AccountDetails();
		accTwo.setBankName("HSBC");
		accTwo.setCurrentAccountBalance(100);
		accTwo.setSavingsAccountBalance(50);
		AccountDetails accThree = new AccountDetails();
		accThree.setBankName("AXIS");
		accThree.setCurrentAccountBalance(1);
		accThree.setSavingsAccountBalance(5);
		accDetails.add(accOne);
		accDetails.add(accTwo);
		accDetails.add(accThree);

	}
}

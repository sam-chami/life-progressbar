package samuel.liveprogessbar

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.preference.PreferenceManager
import samuel.liveprogessbar.helpers.AgeUpdater


/**
 * Implementation of App Widget functionality.
 */
class ProgressbarWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)

            progressSet(context, appWidgetManager, appWidgetId)

        }
    }

    private fun progressSet(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int){
        R.integer.progress
        val pref = PreferenceManager.getDefaultSharedPreferences(context.applicationContext);
        var age = (pref.getInt("age", 0))+1
        var lifeTm = (pref.getInt("lifetm", 83))

        val updateAge = AgeUpdater()
        updateAge.sendData(context)

        val percentage = age * 100 / lifeTm

        val views = RemoteViews(context.packageName, R.layout.progressbar_widget)

        views.setProgressBar(R.id.widgetBar, lifeTm, age, false)
        views.setTextViewText(R.id.progresscount, "$percentage%")
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled

    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
}
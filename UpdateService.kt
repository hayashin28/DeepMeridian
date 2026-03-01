// UpdateService.kt
class UpdateService : IntentService("GearClockUpdate") {
    override fun onHandleIntent(intent: Intent?) {
        val widgetId = intent?.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID, 0
        ) ?: return

        val views = RemoteViews(packageName, R.layout.widget_gear_clock)
        views.setTextViewText(R.id.time_stub, "") // ダミー

        AppWidgetManager.getInstance(this)
            .updateAppWidget(widgetId, views)
    }
}
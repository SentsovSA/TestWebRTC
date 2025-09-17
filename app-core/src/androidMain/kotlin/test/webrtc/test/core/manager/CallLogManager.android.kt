package test.webrtc.test.core.manager

import android.content.ContentResolver
import android.database.ContentObserver
import android.os.Build
import android.provider.CallLog
import java.util.Date

internal class CallLogManager(
    private val contentResolver: ContentResolver
) {

    private val callLogUri = CallLog.Calls.CONTENT_URI
        .buildUpon()
        .appendQueryParameter(CallLog.Calls.LIMIT_PARAM_KEY, "3")
        .build()

    private val observer = object : ContentObserver(null) {
        override fun onChange(selfChange: Boolean) {

        }
    }

    init {
        contentResolver.registerContentObserver(callLogUri, false, observer)
        //contentResolver.unregisterContentObserver(observer)
    }

    private fun displayLog() {
        val columns = arrayOf(
            CallLog.Calls._ID,
            CallLog.Calls.NUMBER,
            CallLog.Calls.TYPE,
            CallLog.Calls.DURATION,
            CallLog.Calls.DATE
        )
        var selection = "${CallLog.Calls.TYPE}=? OR ${CallLog.Calls.TYPE}=?"
        val args = mutableListOf(
            CallLog.Calls.INCOMING_TYPE.toString(),
            CallLog.Calls.MISSED_TYPE.toString()
        )
        var sort: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sort = "${CallLog.Calls._ID} DESC"
            selection += " OR ${CallLog.Calls.TYPE}=?"
            args.add(CallLog.Calls.REJECTED_TYPE.toString())
        }

        contentResolver.query(callLogUri, columns, selection, args.toTypedArray(), sort)?.let {

            val idIndex = it.getColumnIndex(CallLog.Calls._ID)
            val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)
            val typeIndex = it.getColumnIndex(CallLog.Calls.TYPE)
            val durationIndex = it.getColumnIndex(CallLog.Calls.DURATION)
            val dateIndex = it.getColumnIndex(CallLog.Calls.DATE)

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                val number = it.getString(numberIndex)
                val type = it.getInt(typeIndex)
                val date = Date(it.getLong(dateIndex))
                val duration = it.getInt(durationIndex)
                println("\nPhone Number:--- $id) $number \nCall Type:--- $type \nCall Date:--- $date \nCall duration in sec :--- $duration")
            }
            it.close()
        }
    }

}
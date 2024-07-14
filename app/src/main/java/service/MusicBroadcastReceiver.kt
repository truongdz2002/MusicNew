package service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MusicReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            "ACTION_PREV" -> {
                // Xử lý hành động Previous
                Toast.makeText(context, "Previous", Toast.LENGTH_SHORT).show()
                // Gọi hàm để chuyển bài hát về trước
            }
            "ACTION_PAUSE" -> {
                // Xử lý hành động Pause
                Toast.makeText(context, "Pause", Toast.LENGTH_SHORT).show()
                // Gọi hàm để tạm dừng phát nhạc
            }
            "ACTION_NEXT" -> {
                // Xử lý hành động Next
                Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show()
                // Gọi hàm để chuyển bài hát về sau
            }
        }
    }
}


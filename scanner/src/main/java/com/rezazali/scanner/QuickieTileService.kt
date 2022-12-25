package com.rezazali.scanner

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class QuickieTileService : TileService() {

    override fun onBind(intent: Intent?): IBinder? {
        requestListeningState(this, ComponentName(this, QuickieTileService::class.java))
        return super.onBind(intent)
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile?.run {
            state = Tile.STATE_INACTIVE
            updateTile()
        }
    }

    override fun onClick() {
        super.onClick()
        startActivityAndCollapse(
            Intent(this, ScannerActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                putExtra(ScannerActivity.OPEN_SCANNER, true)
            }
        )
    }
}
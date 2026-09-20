package com.example.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NetworkService(private val context: Context) {

    private val _isSimulatingOffline = MutableStateFlow(false)
    val isSimulatingOffline: StateFlow<Boolean> = _isSimulatingOffline.asStateFlow()

    private val _isOnline = MutableStateFlow(checkActualOnline())
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    fun setSimulateOffline(simulate: Boolean) {
        _isSimulatingOffline.value = simulate
        updateStatus()
    }

    fun refreshNetworkState() {
        updateStatus()
    }

    private fun updateStatus() {
        if (_isSimulatingOffline.value) {
            _isOnline.value = false
        } else {
            _isOnline.value = checkActualOnline()
        }
    }

    private fun checkActualOnline(): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            val network = cm?.activeNetwork ?: return false
            val capabilities = cm.getNetworkCapabilities(network) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: Exception) {
            false
        }
    }
}

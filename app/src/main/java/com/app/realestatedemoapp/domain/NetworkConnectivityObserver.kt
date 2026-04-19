package com.app.realestatedemoapp.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.app.realestatedemoapp.domain.model.NetworkStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class NetworkConnectivityObserver(
    private val context: Context
) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    fun observe(): Flow<NetworkStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(NetworkStatus.Connected)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(NetworkStatus.Disconnected)
                }
            }

            val currentNetwork = connectivityManager.activeNetwork
            if (currentNetwork == null) {
                trySend(NetworkStatus.Disconnected)
            } else {
                val capabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
                val hasInternet =
                    capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
                val isValidated =
                    capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true
                if (hasInternet && isValidated) {
                    trySend(NetworkStatus.Connected)
                } else {
                    trySend(NetworkStatus.Disconnected)
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}
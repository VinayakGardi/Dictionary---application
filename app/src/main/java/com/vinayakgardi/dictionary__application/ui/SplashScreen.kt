package com.vinayakgardi.dictionary__application.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.vinayakgardi.dictionary__application.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        checkAndHandleNetworkConnection(this)

    }

    fun callTheApplication(){
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)    }

    private fun checkInternet(context: Context): Boolean {
        // check if connected to wifi or cellular data
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    fun checkAndHandleNetworkConnection(context: Context) {
        if (!checkInternet(context)) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Internet connection is unavailable. Please enable internet and try again.")
                .setPositiveButton("Enable") { _, _ ->
                    // Open system settings for internet connection
                    val intent =
                        context.packageManager.getLaunchIntentForPackage("com.android.settings")
                    if (intent != null) {
                        context.startActivity(intent)
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create().show()
        } else {
            callTheApplication()
        }
    }
}
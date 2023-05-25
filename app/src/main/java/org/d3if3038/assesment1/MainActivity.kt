package org.d3if3038.assesment1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.d3if3038.assesment1.data.SettingDataStore
import org.d3if3038.assesment1.data.dataStore

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private val settingDataStore: SettingDataStore by lazy {
        SettingDataStore(applicationContext.dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!settingDataStore.getBoolean("is_boarding", false)) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }

        navController = findNavController(R.id.main_container)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}
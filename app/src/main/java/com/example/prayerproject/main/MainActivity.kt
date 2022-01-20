package com.example.prayerproject.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.prayerproject.R
import com.example.prayerproject.Service.AlarmService
import com.example.prayerproject.databinding.ActivityMainBinding
import com.example.prayerproject.view.*
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

//        val duaaIntent = intent.getBooleanExtra("Dua'a",false)

//        if (duaaIntent){
//            navController.navigate(R.id.action_homeFragment_to_myFavoriteAthkarFragment)
//            stopService(intent)


       // to hide the notification after its pressed by the user
        //(Stopping the Service )
            val intent = Intent(this, AlarmService::class.java)

            this.stopService(intent)

        window.navigationBarColor =
            this.resources.getColor(R.color.black) // this is for the navigation bar color of the android system

        binding.menuButton.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, binding.menuButton)
            popupMenu.menuInflater.inflate(R.menu.main_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.logout_item -> {
                        FirebaseAuth.getInstance().signOut()


                        sharedPref =
                            this.getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
                        sharedPrefEditor = sharedPref.edit()
                        sharedPrefEditor.putBoolean("is Logged", false)
                        sharedPrefEditor.commit()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()

                    }

                    R.id.about_item -> {
                        val fragment =
                            navHostFragment.childFragmentManager.primaryNavigationFragment
                        when (fragment) {
                            is HomeFragment -> navController.navigate(R.id.action_homeFragment_to_aboutFragment)
                            is PrayersTimeFragment -> navController.navigate(R.id.action_prayersTimeFragment_to_aboutFragment)
                            is MenuFragment -> navController.navigate(R.id.action_menuFragment_to_aboutFragment)
                            is QiblaFragment -> navController.navigate(R.id.action_qiblaFragment_to_aboutFragment)
                            is MyFavoriteAthkarFragment -> navController.navigate(R.id.action_myFavoriteAthkarFragment_to_aboutFragment)
                            is AthkarFragment -> navController.navigate(R.id.action_athkarFragment_to_aboutFragment)

                        }

                    }

                }
                true
            })
            popupMenu.show()
        }
    }

}

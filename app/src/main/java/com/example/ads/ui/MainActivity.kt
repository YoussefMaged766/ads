package com.example.ads.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.ads.R
import com.example.ads.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var  binding: ActivityMainBinding

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navController:NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(binding.appBarHome.toolbar)
//        binding.appBarHome.toolbar.getNavigationIcon()
//            ?.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
         navController = findNavController(R.id.nav_host_fragment_content_home)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout,
            R.string.nav_open,
            R.string.nav_close

        )



//        navView.setupWithNavController(navController)
        binding.navViewBot.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener(this)

    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null){

            binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
            NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
            Log.e( "onCreate: ", "login")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.nav_logout->{
                FirebaseAuth.getInstance().signOut()
                finish()
                startActivity(intent)
                Toast.makeText(applicationContext, "logout", Toast.LENGTH_SHORT).show()
                true
            }
            else->  false
        }
    }


}
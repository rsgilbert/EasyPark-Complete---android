package com.gilboot.easypark

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.gilboot.easypark.model.User
import com.gilboot.easypark.model.UserType
import com.gilboot.easypark.databinding.ActivityMainBinding
import com.gilboot.easypark.util.getUserFromPrefs
import com.gilboot.easypark.util.removeUserFromPrefs
import com.gilboot.easypark.util.repository
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout

    // appBarConfiguration contains top level destinations
    lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var navController: NavController

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout

        // set fragments that will be treated as home fragments, ie will have hamburger icons
        // fragments that are not part of the appBarConfiguration will have a back icon
        appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.chooseFragment,
                    R.id.driverloginFragment,
                    R.id.driversignupFragment,
                    R.id.parksignupFragment,
                    R.id.parkloginFragment,
                    R.id.dashboardFragment,
                    R.id.parksFragment
                ),
                drawerLayout
            )



        navController = findNavController(R.id.myNavHostFragment)




        setupActionBar()

        connectDrawerToController(binding.navView)

//        checkLogin()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//           R.id.tollfree -> withInfo { openDialerApp(it.tollfree) }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    // Allow navigation to previous fragments using up arrow in actionbar
    // AppbarConfiguration provides top level destinations
    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration)

}

// Have NavigationUI decide what label to show in the action bar
// It will also determine whether to show up arrow or drawer menu icon
private fun MainActivity.setupActionBar() =
    setupActionBarWithNavController(navController, appBarConfiguration)

// connect navigation drawer to navigation controller
private fun MainActivity.connectDrawerToController(navView: NavigationView) =
    NavigationUI.setupWithNavController(navView, navController)


// check if a user is logged in and set appropriate drawer menu
fun MainActivity.setCorrectDrawerMenu() {
    lifecycleScope.launchWhenCreated {
        when (repository.getUserType()) {
            UserType.Driver -> setupDriverUI()
            UserType.Park -> setupParkUI()
            UserType.None -> setupAuthUI()
        }
    }
}

fun Fragment.setCorrectDrawerMenu() {
    (activity as MainActivity).setCorrectDrawerMenu()
}


private fun MainActivity.setupDriverUI() {
    binding.navView.apply {
        menu.clear()
        inflateMenu(R.menu.driver_drawer_menu)
    }
}

private fun MainActivity.setupParkUI() {
    binding.navView.apply {
        menu.clear()
        inflateMenu(R.menu.park_drawer_menu)
    }
}

private fun MainActivity.setupAuthUI() {
    binding.navView.apply {
        menu.clear()
        inflateMenu(R.menu.auth_drawer_menu)
    }
}

fun Context.signout() {
    removeUserFromPrefs()
}
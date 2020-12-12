package com.gilboot.easypark.park.registration


import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.*
import com.gilboot.easypark.util.saveUserToPrefs
import org.jetbrains.anko.support.v4.toast


// Set the location of the Park
class LocationFragment : MapFragment() {
    val parkViewModel: ParkViewModel by activityViewModels()


    override fun moreOnCreateView(view: View) {
        super.moreOnCreateView(view)


    }

    override fun whenMapIsReady() {
        super.whenMapIsReady()
        // Move camera to current location
        moveCameraToCurrentLocation()

        // Set green marker to current location
        withCurrentLocation {
            addGreenMarker(
                it,
                title = "Current Location",
                snippet = "You will use this location as parking location"
            )
        }

        // Set green marker to current location
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.park_reg_loc_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.finish -> {
                toast("Finish")
                withCurrentLocation {
                    parkViewModel.setParkLoc(it.latitude, it.longitude)
                }
                parkViewModel.registerPark {
                    toast("Successfully registered park")

                    // save user to shared prefs
                    requireContext().saveUserToPrefs(
                        parkViewModel!!.parkLiveData.value!!.user
                    )
                    navigateToDashboard()
                }
            }

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

}

// navigate to DashboardFragment
fun LocationFragment.navigateToDashboard() {
    val action = LocationFragmentDirections.actionLocationFragmentToDashboardFragment()
    findNavController().navigate(action)
}
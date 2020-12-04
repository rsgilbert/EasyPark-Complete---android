package com.gilboot.easypark.park.registration


import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.gilboot.easypark.*
import org.jetbrains.anko.support.v4.toast


// Set the location of the Park
class LocationFragment : MapFragment() {
    val parkViewModel: ParkViewModel by viewModels()

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
                parkViewModel.registerPark()
                toast("Completed registration")
            }

            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }


//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        setHasOptionsMenu(true)
//        val binding =
//            DataBindingUtil.inflate<FragLocationRegBinding>(
//                inflater,
//                R.layout.frag_location_reg,
//                container,
//                false
//            )
//
//
//        return binding.root
//    }
}
package com.gilboot.easypark.park.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilboot.easypark.data.Park
import com.gilboot.easypark.util.uploadPicture
import java.io.InputStream

class ParkViewModel : ViewModel() {

    val parkLiveData = MutableLiveData<Park>().apply { value = Park() }


}

fun ParkViewModel.setParkInfo(name: String, tel: String) {
    parkLiveData.value = parkLiveData.value!!.copy(name = name, tel = tel)
}

fun ParkViewModel.setParkLoc(lat: Double, lng: Double) {
    parkLiveData.value = parkLiveData.value!!.copy(lat = lat, lng = lng)
}

fun ParkViewModel.setParkDP(picture: String) {
    parkLiveData.value = parkLiveData.value!!.copy(displayPicture = picture)
}

fun ParkViewModel.setParkPics(pictures: List<String>) {
    parkLiveData.value = parkLiveData.value!!.copy(pictures = pictures)
}


fun ParkViewModel.addPicture(stream: InputStream, onError: (err: String) -> Unit) {
    uploadPicture(stream, { pictureUrl ->
        parkLiveData.value = parkLiveData.value?.let {
            it.copy(pictures = it.pictures + pictureUrl)
        }
    }, onError)
}

// register park
fun ParkViewModel.registerPark() {

}





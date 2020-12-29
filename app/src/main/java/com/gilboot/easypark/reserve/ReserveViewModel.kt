package com.gilboot.easypark.reserve

import androidx.lifecycle.ViewModel
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.util.QrSize
import com.gilboot.easypark.util.generateQrBitmap

class ReserveViewModel(val reserve: Visit) : ViewModel() {

    val bitMap = generateQrBitmap(reserve, QrSize.LARGE)


}
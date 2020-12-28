package com.gilboot.easypark.dialogs

/**
 * A scan can occur either when a driver is arriving or when they are departing
 */
interface ScanListener {
    fun scanArrival()
    fun scanDeparture()
}
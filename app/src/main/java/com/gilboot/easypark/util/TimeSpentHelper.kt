package com.gilboot.easypark.util

import java.util.concurrent.TimeUnit
/*
* < 10 minutes: Now
* < 1 hour: A few minutes ago
* < 1.5 hours: 1 hours ago
* < 6 hours: A few hours ago
* < 1 day: Today
* < 2 days: Yesterday
* < 1 week: A few days ago
* < 1 month: A few weeks ago
* < 6 months: A few months ago
* < 1 year: A few months ago
* Otherwise: Years ago
* */
fun timeSpent(start:Long, end:Long): String{
    val sub: Long = end - start
    val message: String
    val minutes = TimeUnit.MILLISECONDS.toMinutes(sub)
    if(minutes < 10){
        message = "Now"
    }else{
        val hours = TimeUnit.MILLISECONDS.toHours(sub)
        if(hours < 1){
            message = "A few minutes ago"
        }else if(hours in 2..5){
            message = "A few hours ago"
        }else{
            val day = TimeUnit.MILLISECONDS.toDays(sub)
            if(day < 1){
                message = "Today"
            }else{
                if( day < 2){
                    message = "Yesterday"
                }else{
                    message = if (day < 7){
                        "A few days ago"
                    }else{
                        // Getting the number of months
                        val months = day / 30
                        if(months == 1L){
                            "A few weeks ago"
                        }else{
                            if(months < 12){
                                "A few months ago"
                            }else{
                                "Years ago"
                            }
                        }
                    }
                }
            }
        }
    }
    return message
}

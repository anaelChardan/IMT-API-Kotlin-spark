package com.imt.api.Entity

open class PartialRestaurant(open val name: String?, open val city: String?) {
    companion object {
        fun validate(restaurant: PartialRestaurant): Boolean {
            return  (restaurant.name !== null && restaurant.name!!.isNotEmpty()) &&
                    (restaurant.city !== null && restaurant.city!!.isNotEmpty())
        }
    }
}

data class Restaurant(val id: Int, override val name: String, override val city: String) : PartialRestaurant(name, city)

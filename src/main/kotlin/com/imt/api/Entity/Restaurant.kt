package com.imt.api.Entity

data class Restaurant(val id: Int, val name: String, val city: String)
data class PartialRestaurant(val name: String, val city: String)
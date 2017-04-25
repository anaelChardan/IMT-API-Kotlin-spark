package com.imt.api

import com.imt.api.Entity.Restaurant
import java.util.concurrent.atomic.AtomicInteger

class RestaurantDao {
    val restaurants = hashMapOf(
        0 to Restaurant(id = 0, name = "Le grosse bouf", city = "Nantes")
    )

    var lastId: AtomicInteger = AtomicInteger(restaurants.size - 1)

    fun save(name: String, city: String): Restaurant? {
        val id = lastId.incrementAndGet()
        restaurants.put(id, Restaurant(id, name = name, city = city))
        return restaurants[id]
    }

    fun findById(id: Int): Restaurant? {
        return restaurants[id]
    }

    fun findByName(name: String): Restaurant? {
        return restaurants.values.find { it.name == name }
    }

    fun update(id: Int, name: String, city: String) {
        restaurants.put(id, Restaurant(id, name, city))
    }
}
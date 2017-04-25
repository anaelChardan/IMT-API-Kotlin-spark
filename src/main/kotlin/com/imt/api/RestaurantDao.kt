package com.imt.api

import com.imt.api.Entity.Restaurant
import java.util.concurrent.atomic.AtomicInteger

class RestaurantDao {
    val restaurants = hashMapOf(
        0 to Restaurant(id = 0, name = "Le grosse bouf")
    )

    var lastId: AtomicInteger = AtomicInteger(restaurants.size - 1)

    fun save(name: String) {
        val id = lastId.incrementAndGet()
        restaurants.put(id, Restaurant(id, name = name))
    }

    fun findById(id: Int): Restaurant? {
        return restaurants[id]
    }

    fun findByName(name: String): Restaurant? {
        return restaurants.values.find { it.name == name }
    }

    fun update(id: Int, name: String) {
        restaurants.put(id, Restaurant(id, name))
    }

    fun delete(id: Int) {
        restaurants.remove(id)
    }
}
package com.imt.api

import com.imt.api.Entity.Restaurant
import java.util.concurrent.atomic.AtomicInteger

class RestaurantDao {
    val restaurants = hashMapOf(
        1 to Restaurant(id = 1, name = "Petit Tonneau", city = "Nantes"),
        2 to Restaurant(id = 2, name = "Machin Machine", city = "Nantes"),
        3 to Restaurant(id = 3, name = "Le fou du roi", city = "Nantes"),
        4 to Restaurant(id = 4, name = "Inde et vous", city = "Nantes"),
        5 to Restaurant(id = 5, name = "La régate", city = "Nantes"),
        6 to Restaurant(id = 6, name = "Le Shelter", city = "Nantes"),
        7 to Restaurant(id = 7, name = "Big Fernand", city = "Nantes"),
        8 to Restaurant(id = 8, name = "Dubrown", city = "Nantes"),
        9 to Restaurant(id = 9, name = "Le cedre", city = "Nantes"),
        10 to Restaurant(id = 10, name = "Au vieux quimper", city = "Nantes"),
        11 to Restaurant(id = 11, name = "Berlin 1989", city = "Nantes"),
        12 to Restaurant(id = 12, name = "Meltingpot", city = "Nantes")
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

    fun getPage(page: Int): Map<Int, Restaurant>
    {
        return restaurants.filterKeys { e -> e >= (page - 1) * 10 && e < page * 10 }
    }
}
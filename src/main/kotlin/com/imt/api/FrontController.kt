package com.imt.api

import com.imt.api.Entity.PartialRestaurant
import com.imt.api.Entity.Restaurant
import com.imt.api.UtilsService.Companion.dataToJson
import com.imt.api.UtilsService.Companion.isNumeric
import spark.Filter
import spark.Spark.*

val restaurantDao = RestaurantDao()

fun main(args: Array<String>) {
    port(3000)

    after(Filter({ req, res ->
        res.type("application/json")
    }))

    path("restaurants") {
//        before("/*") { req, res ->
//            res.type("application/json")
//
//        }
//        after("/*") { req, res ->
//            res.type("application/json")
//
//        }

        get("") { req, res ->
            dataToJson(restaurantDao.restaurants.values)
        }

        post("")  { req, res ->
            val body = req.body()

            val partialRestaurant = UtilsService.jsonToData<PartialRestaurant>(body)

            dataToJson(restaurantDao.save(partialRestaurant.name, partialRestaurant.city))
        }

        get("/:id") { req, res ->
            val id = req.params("id")
            if (id != null && isNumeric(id)) {
                val restaurant = restaurantDao.findById(id.toInt())
                if (restaurant !== null) {
                    return@get dataToJson(restaurant)
                }
            }

            res.status(404)
            ""
        }
    }
}


package com.imt.api

import com.imt.api.MapperService.Companion.dataToJson
import com.imt.api.MapperService.Companion.isNumeric
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


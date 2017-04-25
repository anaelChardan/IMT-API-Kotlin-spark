package com.imt.api

import com.imt.api.MapperService.Companion.dataToJson
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
            val restaurant = restaurantDao.findById(req.attribute("id"))

            if (restaurant !== null) {
                dataToJson(restaurant)
            } else {
                res.status(404)
            }
        }
    }
}


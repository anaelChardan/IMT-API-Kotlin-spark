package com.imt.api

import com.imt.api.MapperService.Companion.dataToJson
import com.imt.api.MapperService.Companion.isNumeric
import spark.Filter
import spark.Spark.*

val restaurantDao = RestaurantDao()
val magicKey = "THIS_IS_A_SUPER_BADASS_MAGIC_KEY"

var ipsCall: HashMap<String, Int> = HashMap()
val limitation: Int = 4


fun main(args: Array<String>) {
    port(3000)

    after(Filter({ req, res ->
        res.type("application/json")
    }))

    path("restaurants") {
        get("") { req, res ->
            if (req.headers("MySuperID") == null) {
                res.status(400)
                return@get ""
            }

            val id = req.headers("MySuperID")

            if (ipsCall.containsKey(id)) {
                if (ipsCall.getValue(id) == limitation) {
                    res.status(429)
                    return@get ""
                }
            }

            ipsCall[id] = ipsCall.getOrElse(id, { 0 }) + 1
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

    path("magic-key") {
        get("") { req, res ->
            dataToJson(magicKey)
        }
    }
}


package com.imt.api

import com.imt.api.Entity.PartialRestaurant
import com.imt.api.UtilsService.Companion.dataToJson
import com.imt.api.UtilsService.Companion.isNumeric
import spark.Filter
import spark.Request
import spark.Spark.*

val restaurantDao = RestaurantDao()
val magicKey = "THIS_IS_A_SUPER_BADASS_MAGIC_KEY"

var ipsCall: HashMap<String, Int> = HashMap()
val limitation: Int = 4

fun checkSecurity(req: Request): Boolean {
    val authentication = req.headers("Authentication")

    return !(authentication == null && magicKey != authentication)
}

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

            if (!checkSecurity(req)) {
                if (ipsCall.containsKey(id)) {
                    if (ipsCall.getValue(id) == limitation) {
                        res.status(429)
                        return@get ""
                    }
                }

                ipsCall[id] = ipsCall.getOrElse(id, { 0 }) + 1
            }

            val page = if (req.queryParams("page") == null)  1 else req.queryParams("page").toInt()
            dataToJson(restaurantDao.getPage(page).values)
        }

        post("")  { req, res ->
            val body = req.body()

            if (!checkSecurity(req)) {
                res.status(401)
                ""
            }

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

    path("magic-key") {
        get("") { req, res ->
            dataToJson(magicKey)
        }
    }
}


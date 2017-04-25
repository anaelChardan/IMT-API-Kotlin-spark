package com.imt.api

import com.imt.api.Entity.ErrorResponse
import com.imt.api.Entity.PartialRestaurant
import com.imt.api.Entity.PartialRestaurant.Companion.validate
import com.imt.api.Entity.Restaurant
import com.imt.api.UtilsService.Companion.dataToJson
import com.imt.api.UtilsService.Companion.isNumeric
import spark.Filter
import spark.Spark.*
import kotlin.collections.*

val restaurantDao = RestaurantDao()
val magicKey = "THIS_IS_A_SUPER_BADASS_MAGIC_KEY"

var ipsCall: HashMap<String, Int> = HashMap()
val limitation: Int = 4


fun main(args: Array<String>) {
    port(3000)

    after(Filter({ _, res ->
        res.type("application/json")
    }))

    path("restaurants") {
        get("") { req, res ->
            if (req.headers("MySuperID") == null) {
                return@get ErrorResponse.badRequest(res)
            }

            val id = req.headers("MySuperID")

            if (ipsCall.containsKey(id)) {
                if (ipsCall.getValue(id) == limitation) {
                    return@get ErrorResponse.tooManyRequests(res)
                }
            }

            ipsCall[id] = ipsCall.getOrElse(id, { 0 }) + 1
            dataToJson(restaurantDao.restaurants.values)
        }

        post("")  { req, res ->
            val body = req.body()

            val partialRestaurant = UtilsService.jsonToData<PartialRestaurant>(body)

            if (validate(partialRestaurant)) {
                val restaurant = restaurantDao.save(partialRestaurant.name!!, partialRestaurant.city!!)
                res.status(201)
                dataToJson(restaurant)
            } else {
                ErrorResponse.notFound(res)
            }
        }

        get("/:id") { req, res ->
            val id = req.params("id")
            if (id != null && isNumeric(id)) {
                val restaurant = restaurantDao.findById(id.toInt())
                if (restaurant !== null) {
                    return@get dataToJson(restaurant)
                }
            }

            ErrorResponse.notFound(res)
        }

        patch("/:id") { req, res ->
            val id = req.params("id")
            if (id != null && isNumeric(id)) {
                val restaurant = restaurantDao.findById(id.toInt())
                val body = req.body()
                val partialRestaurant = UtilsService.jsonToData<PartialRestaurant>(body)
                if (restaurant !== null) {
                    var name = restaurant.name
                    if (partialRestaurant.name !== null)
                    {
                        name = partialRestaurant.name!!
                    }
                    var city = restaurant.city
                    if (partialRestaurant.city !== null)
                    {
                        city = partialRestaurant.city!!
                    }
                    restaurantDao.update(id.toInt(), name, city)
                    return@patch dataToJson(restaurant)
                }
            }

            ErrorResponse.notFound(res)
        }

        put("/:id") { req, res ->
            val id = req.params("id")
            if (id != null && isNumeric(id)) {
                val restaurant = restaurantDao.findById(id.toInt())
                val body = req.body()
                val partialRestaurant = UtilsService.jsonToData<PartialRestaurant>(body)
                if (restaurant !== null) {
                    restaurantDao.update(id.toInt(), partialRestaurant.name!!, partialRestaurant.city!!)
                    return@put dataToJson(restaurant)
                }
            }

            res.status(404)
            ""
        }
    }

    path("magic-key") {
        get("") { _, _ ->
            dataToJson(magicKey)
        }
    }
}


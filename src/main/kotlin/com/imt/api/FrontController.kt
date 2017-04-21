package com.imt.api

import spark.Spark.*

fun main(args: Array<String>) {
    path("restaurants") {
        get("") { req, res ->
            "Bit World"
        }

        get("/:id") { req, res ->
            "Super Id : " + req.params("id")
        }

        delete("") { req, res ->

        }
    }

}


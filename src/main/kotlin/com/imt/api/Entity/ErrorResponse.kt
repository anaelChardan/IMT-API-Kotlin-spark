package com.imt.api.Entity

import com.imt.api.UtilsService.Companion.dataToJson
import spark.Response

data class Error(val message: String)

class ErrorResponse {
    companion object {
        fun badRequest(res: Response): String {
            res.status(400)
            return dataToJson(Error("Missing parameters"))
        }

        fun tooManyRequests(res: Response): String {
            res.status(429)
            return dataToJson(Error("Too many requests"))
        }

        fun notFound(res: Response): String {
            res.status(404)
            return dataToJson(Error("Not Found"))
        }
    }
}

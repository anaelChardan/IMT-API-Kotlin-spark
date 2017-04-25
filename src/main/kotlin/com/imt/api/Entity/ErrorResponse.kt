package com.imt.api.Entity

import com.imt.api.UtilsService.Companion.dataToJson
import spark.Response

data class Error(val message: String)

class ErrorResponse {
    companion object {
        fun notFound(res: Response): String {
            res.status(404)
            return dataToJson(Error("Not Found"))
        }
    }
}

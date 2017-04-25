package com.imt.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.*

/**
 * @author Clément Garbay
 */
class UtilsService {
    companion object {
        val mapper = ObjectMapper().registerKotlinModule()

        fun <T> dataToJson(data: T): String {
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
            return mapper.writeValueAsString(data)
        }

        inline fun <reified T : Any> jsonToData(json: String): T {
            return mapper.readValue<T>(json)
        }

        fun isNumeric(input: String): Boolean =
            try {
                input.toInt()
                true
            } catch (e: NumberFormatException) {
                false
            }
    }
}
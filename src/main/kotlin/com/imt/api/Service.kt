package com.imt.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

/**
 * @author Clément Garbay
 */
class MapperService {
    companion object {
        fun <T> dataToJson(data: T): String {
            val mapper = ObjectMapper()
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
            return mapper.writeValueAsString(data)
        }

        inline fun <reified T> jsonToData(json: String): T {
            val mapper = ObjectMapper()
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
            return mapper.readValue(json, T::class.java)
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
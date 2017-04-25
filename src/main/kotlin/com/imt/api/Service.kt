package com.imt.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import java.io.IOException
import java.io.StringWriter

/**
 * @author Clément Garbay
 */
class MapperService {
    companion object {
        fun dataToJson(data: Any): String {
            try {
                val mapper = ObjectMapper()
                mapper.enable(SerializationFeature.INDENT_OUTPUT)
                val sw = StringWriter()
                mapper.writeValue(sw, data)
                return sw.toString()
            } catch (e: IOException) {
                throw RuntimeException("IOException from a StringWriter?")
            }
        }
    }
}
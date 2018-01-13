package com.beust.klaxon

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

class Annotations {
    companion object {
        /**
         * Attempts to find a @Json annotation on the given property by looking through the fields
         * and then the properties of the class.
         */
        fun findJsonAnnotation(kc: KClass<*>, propertyName: String) : Json ? {
            val r1 = kc.java.getDeclaredField(propertyName).getDeclaredAnnotation(Json::class.java)
            val result =
                    if (r1 == null) {
                        val r2 = kc.declaredMemberProperties
                                .filter { it.name == propertyName }
                                .mapNotNull { it.findAnnotation<Json>() }
                        if (r2.isEmpty()) null else r2[0]
                    } else {
                        r1
                    }
            return result
        }
    }
}
package com.vioside.foundation.network.services

import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.qualifier.named

/**
 * Used to contain the base url and a function that will return an endpoint path
 * based on a path of type [ApiPath]
 */
class ApiEndpointService: KoinComponent {

    private val baseUrl: String = get(qualifier = named("apiUrl"))

    fun endpointUrl(apiName: ApiPath): String {
        val path = apiName.path()
        return "$baseUrl$path"
    }

}

/**
 * An interface that contains a path function which returns a path string
 */
interface ApiPath {
    fun path(): String
}

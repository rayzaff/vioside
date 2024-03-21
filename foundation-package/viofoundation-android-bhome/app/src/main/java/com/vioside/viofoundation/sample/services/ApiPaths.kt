package com.vioside.viofoundation.sample.services

import com.vioside.foundation.network.services.ApiPath

/**
 * Endpoint constants declared here
 */
enum class ApiPaths: ApiPath {

    LOGIN { override fun path(): String { return "login" } },
    VALIDATE { override fun path(): String { return "validate" } },
    LOGOUT { override fun path(): String { return "logout" } },
    REFRESH_TOKEN { override fun path(): String { return "token" } },
    SAMPLE { override fun path(): String { return "sample" } },

}

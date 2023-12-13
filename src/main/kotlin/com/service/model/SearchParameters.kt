package com.service.model

data class SearchParameters(
    var continuationToken: ContinuationToken? = null,
    val limit: Int? = null
)

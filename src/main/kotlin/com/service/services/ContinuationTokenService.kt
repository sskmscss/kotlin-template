package com.service.services

import com.service.error.customexception.InvalidInputException
import com.service.model.ContinuationToken
import com.service.model.SearchParameters
import com.service.model.TemplateModel
import org.springframework.stereotype.Service
import org.springframework.util.Base64Utils
import java.nio.charset.Charset

@Service
class ContinuationTokenService {
    fun decodeContinuationToken(continuationToken: String): ContinuationToken {
        try {
            val decodedCT = Base64Utils.decodeFromString(continuationToken).toString(Charset.defaultCharset())
            require(decodedCT.isNotBlank())
            return ContinuationToken(decodedCT)
        } catch (ex: Exception) {
            throw InvalidInputException("Incorrect Continuation token: $continuationToken")
        }
    }

    fun encodeContinuationToken(continuationToken: ContinuationToken): String {
        return Base64Utils.encodeToString(
            continuationToken.startFromId!!.toByteArray(Charset.defaultCharset())
        )
    }

    fun updateContinuationToken(
        result: MutableList<TemplateModel>,
        searchParameters: SearchParameters
    ) {
        searchParameters.continuationToken = if (result.size == searchParameters.limit) ContinuationToken(
            startFromId = result.last().id
        ) else null
    }
}
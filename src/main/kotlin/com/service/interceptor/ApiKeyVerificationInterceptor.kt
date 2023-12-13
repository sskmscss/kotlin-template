package com.service.interceptor

import com.service.constants.ExceptionMessage.API_KEY_NOT_PROVIDED
import com.service.constants.ExceptionMessage.API_KEY_NOT_VALID
import com.service.constants.RestHeaderConstants.API_KEY
import com.service.error.customexception.ApiKeyInvalidException
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiKeyVerificationInterceptor(private val serviceApiKey: String) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val apiKey: String? = request.getHeader(API_KEY)
        apiKey?.let {
            if (serviceApiKey != apiKey) {
                throw ApiKeyInvalidException(API_KEY_NOT_VALID)
            }
        } ?: throw ApiKeyInvalidException(API_KEY_NOT_PROVIDED)

        return true
    }
}

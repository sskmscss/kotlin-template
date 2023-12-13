package com.service.error

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

data class ErrorResponse(@JsonIgnore
                         val code: HttpStatus
                         , val status: String
                         , val error: String
                         , val message: String
                         , val timestamp: Date
                         , val bindingErrors: List<String>) {


    constructor(status: HttpStatus, message: String) : this(status, status.value().toString(), status.reasonPhrase, message, Date(), ArrayList<String>())

}

class ErrorResponseEntity : ResponseEntity<ErrorResponse> {

    constructor(body: ErrorResponse) : super(body, body.code)


    companion object {

        fun badReqeust(message: String) = ErrorResponseEntity(ErrorResponse(HttpStatus.BAD_REQUEST, message))
        fun notFound(message: String) = ErrorResponseEntity(ErrorResponse(HttpStatus.NOT_FOUND, message))
        fun serverError(message: String) = ErrorResponseEntity(ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message))
        fun forbidden(message: String) = ErrorResponseEntity(ErrorResponse(HttpStatus.FORBIDDEN, message))

    }

}
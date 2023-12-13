package com.service.error

import com.service.error.ErrorResponseEntity.Companion.badReqeust
import com.service.error.ErrorResponseEntity.Companion.forbidden
import com.service.error.ErrorResponseEntity.Companion.notFound
import com.service.error.customexception.ApiKeyInvalidException
import com.service.error.customexception.DataNotFoundException
import com.service.error.customexception.InvalidInputException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.apache.logging.log4j.kotlin.logger

@ControllerAdvice
class ExceptionHandlers {


    /**
     * Handler Method for Data Not Found
     */
    @ExceptionHandler(DataNotFoundException::class)
    fun resourceNotFoundException(exception: DataNotFoundException) =
            exception.message?.let {
                logger().error(exception.printStackTrace())
                notFound(it)
            }

    /**
     * Handler Method for Data Not Found
     */
    @ExceptionHandler(InvalidInputException::class)
    fun invalidInputException(exception: InvalidInputException) =
            exception.message?.let {
                logger().error(exception.printStackTrace())
                badReqeust(it)
            }

    /**
     * Handler Method for Generic Exception
     */
    @ExceptionHandler(Exception::class)
    fun internalErrorException(exception: Exception) =
            exception.message?.let {
                logger().error(exception.printStackTrace())
                ErrorResponseEntity.serverError(it)
            }

    /**
     * Handler Method for ApiKeyInvalidException
     */
    @ExceptionHandler(ApiKeyInvalidException::class)
    fun apiKeyInvalidException(exception: ApiKeyInvalidException) =
            exception.message?.let {
                logger().error(exception.printStackTrace())
                forbidden(it)
            }

}
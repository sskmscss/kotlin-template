package com.service.controller.v1

import com.service.constants.ExceptionMessage.BAD_REQUEST
import com.service.constants.ExceptionMessage.CREATED
import com.service.constants.ExceptionMessage.MULTI_STATUS_POST
import com.service.constants.ExceptionMessage.MULTI_STATUS_PUT
import com.service.constants.ExceptionMessage.NO_DATA_FOUND
import com.service.constants.ExceptionMessage.OK
import com.service.constants.RestHeaderConstants.CONTINUATION_TOKEN
import com.service.constants.ServiceLabels.API_TAG_DESC
import com.service.constants.ServiceLabels.API_TAG_NAME
import com.service.constants.ServiceLabels.DATA_FOUND
import com.service.constants.ServiceLabels.MEDIA_TYPE
import com.service.constants.ServiceLabels.OPENAPI_GET_BY_ID_DEF
import com.service.constants.ServiceLabels.OPENAPI_GET_DEF
import com.service.constants.ServiceLabels.OPENAPI_POST_DEF
import com.service.constants.ServiceLabels.OPENAPI_PUT_DEF
import com.service.constants.ServiceLabels.ZONE
import com.service.constants.URLPath.API_PATH
import com.service.constants.URLPath.API_PATH_BY_ID
import com.service.constants.URLPath.BASE_URI
import com.service.model.SearchParameters
import com.service.model.TemplateModel
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.logging.log4j.kotlin.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.service.services.ContinuationTokenService
import com.service.services.Service
import java.net.URI
import java.time.LocalDateTime
import java.time.ZoneId
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(BASE_URI)
@Tag(name = API_TAG_NAME, description = API_TAG_DESC)
class Controller (
    private val service: Service,
    @Value("\${service.pagination.page-size.default:100}") private val defaultPageSize: Int,
    private val continuationTokenService: ContinuationTokenService
)
{
    @Operation(summary = OPENAPI_GET_DEF, description = OPENAPI_GET_DEF, tags = [API_TAG_NAME])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = DATA_FOUND, content = [
                    (Content(
                        mediaType = MEDIA_TYPE, array = (
                                ArraySchema(schema = Schema(implementation = TemplateModel::class)))
                    ))]
            ),
            ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
            ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = [API_PATH], method = [RequestMethod.GET], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(
        continuationToken: String?,
        limit: Int?,
        response: HttpServletResponse
    ): ResponseEntity<MutableList<TemplateModel>> {
        val param = SearchParameters(
            limit = limit ?: defaultPageSize,
            continuationToken = continuationToken?.run {
                continuationTokenService.decodeContinuationToken(this)
            }
        )

        return ResponseEntity.ok(service.get(param)).also {
            param.continuationToken?.let {
                response.setHeader(CONTINUATION_TOKEN, continuationTokenService.encodeContinuationToken(it))
            }
        }
    }

    @Operation(summary = OPENAPI_GET_BY_ID_DEF, description = OPENAPI_GET_BY_ID_DEF, tags = [API_TAG_NAME])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = DATA_FOUND,
                content = [Content(schema = Schema(implementation = TemplateModel::class))]
            ),
            ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
            ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(
        value = [API_PATH_BY_ID],
        method = [RequestMethod.GET],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getById(
        @PathVariable id: String,
    ): ResponseEntity<TemplateModel> {
        logger().info("GETApiById-Start ${LocalDateTime.now(ZoneId.of(ZONE))}")



        return ResponseEntity.ok(service.getById(id)).also {
            logger().info("GETApiById-End ${LocalDateTime.now(ZoneId.of(ZONE))}")
        }
    }

    /**
     * This is a sample of the POST Endpoint
     */
    @Operation(summary = OPENAPI_POST_DEF, description = OPENAPI_POST_DEF, tags = [API_TAG_NAME])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = CREATED, content = [Content()]),
            ApiResponse(responseCode = "207", description = MULTI_STATUS_POST, content = [Content()]),
            ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
            ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(value = [API_PATH], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun post(@RequestHeader headers: Map<String, String>, @RequestBody model: TemplateModel): ResponseEntity<String> {




        logger().info("POST-Start ${LocalDateTime.now(ZoneId.of(ZONE))}")

        service.save(model)

        return ResponseEntity.created(URI("")).body("Data Successfully Created").also {
            logger().info("POST-End ${LocalDateTime.now(ZoneId.of(ZONE))}")
        }
    }

    @Operation(summary = OPENAPI_PUT_DEF, description = OPENAPI_PUT_DEF, tags = [API_TAG_NAME])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = OK, content = [Content()]),
            ApiResponse(responseCode = "207", description = MULTI_STATUS_PUT, content = [Content()]),
            ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
            ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(
        value = [API_PATH_BY_ID],
        method = [RequestMethod.PUT],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun put(
        @PathVariable id: String,
        @RequestHeader headers: Map<String, String>, @RequestBody model: TemplateModel
    ): ResponseEntity<String> {


        logger().info("PUT-Start ${LocalDateTime.now(ZoneId.of(ZONE))}")

        service.update(id, model)

        return ResponseEntity.ok().body("Data Successfully Updated").also {
            logger().info("PUT-End ${LocalDateTime.now(ZoneId.of(ZONE))}")
        }
    }

    @Operation(summary = "DELETE Template", description = "DELETE Template", tags = [API_TAG_NAME])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Deleted", content = [Content()]),
            ApiResponse(responseCode = "400", description = BAD_REQUEST, content = [Content()]),
            ApiResponse(responseCode = "404", description = NO_DATA_FOUND, content = [Content()])]
    )
    @RequestMapping(
        value = [API_PATH_BY_ID],
        method = [RequestMethod.DELETE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun delete(@PathVariable id: String): ResponseEntity<String> {
        service.delete(id)
        return ResponseEntity.ok().body("Data Successfully Deleted")
    }
}
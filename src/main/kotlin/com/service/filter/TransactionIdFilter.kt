package com.service.filter

import com.service.constants.RestHeaderConstants.TRANSACTION_ID_HEADER
import com.service.utility.generateUUID
import org.apache.logging.log4j.ThreadContext
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class TransactionIdFilter : Filter {

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, chain: FilterChain) {
        val request = servletRequest as HttpServletRequest
        val response = servletResponse as HttpServletResponse

        try {
            val transactionId = request.getHeader(TRANSACTION_ID_HEADER) ?: generateUUID()
            transactionId.let {
                ThreadContext.put(TRANSACTION_ID_HEADER, transactionId)
                response.setHeader(TRANSACTION_ID_HEADER, transactionId)
                MDC.put(TRANSACTION_ID_HEADER, transactionId)
            }
            chain.doFilter(request, response)
        } finally {
            MDC.clear()
        }
    }
}

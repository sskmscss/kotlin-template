package com.service.services

import com.service.repositories.Repository
import java.util.*
import org.springframework.stereotype.Service

@Service
class ServiceDuplicate(private val repo: Repository,
                       private val continuationTokenService: ContinuationTokenService
)
{
    fun generateUUID(): String =
        UUID.randomUUID().toString()
}
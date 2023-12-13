package com.service.configuration

import com.service.converter.LocalDateTimeToStringWriteConverter
import com.service.converter.LocalDateToStringWriteConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@Configuration
class MongoConfig {

    @Bean
    fun mongoCustomConversions() = MongoCustomConversions(
        mutableListOf(
            LocalDateToStringWriteConverter(),
            LocalDateTimeToStringWriteConverter()
        )
    )
}

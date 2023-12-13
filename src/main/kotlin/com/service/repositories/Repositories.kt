package com.service.repositories

import com.service.model.TemplateModel
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface Repository : MongoRepository<TemplateModel, String>, CustomRepository {

}
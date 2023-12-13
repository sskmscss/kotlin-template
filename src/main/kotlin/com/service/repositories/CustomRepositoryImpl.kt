package com.service.repositories

import com.service.model.TemplateModel
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class CustomRepositoryImpl(private val mongoTemplate: MongoTemplate) : CustomRepository {


    override fun getAllByDesc(modDesc: String): List<TemplateModel>{
        return mongoTemplate.find(Query(Criteria.where("name").`is`(modDesc)),
            TemplateModel::class.java)
    }

    override fun getById(id: String): TemplateModel? {
        return mongoTemplate.find(Query(Criteria.where("id").`is`(id)),
            TemplateModel::class.java).firstOrNull()
    }

    override fun getAll(limit: Int, startFromId: String?): MutableList<TemplateModel>? {
        return mongoTemplate.find(
            Query().with(Sort.by(Sort.Direction.ASC, "id"))
            .limit(limit)
            .apply {
                startFromId?.let {
                    this.addCriteria(Criteria().and("id").gt(it))
                }
            },
            TemplateModel::class.java
        )
    }
}
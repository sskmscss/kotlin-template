package com.service.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "template-master")
data class TemplateModel(
    @JsonIgnore
    @Id
    var _id: ObjectId = ObjectId(),
    @Indexed
    val id: String,
    val name: String
)

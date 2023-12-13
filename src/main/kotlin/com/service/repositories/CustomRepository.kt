package com.service.repositories

import com.service.model.TemplateModel


interface CustomRepository {

    fun getAllByDesc(name: String): List<TemplateModel>
    fun getById(id: String):TemplateModel?
    fun getAll(limit: Int, startFromId: String?): MutableList<TemplateModel>?

}
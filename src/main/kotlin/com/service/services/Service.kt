package com.service.services

import com.service.constants.ExceptionMessage
import com.service.error.customexception.DataNotFoundException
import com.service.model.SearchParameters
import com.service.model.TemplateModel
import com.service.repositories.Repository
import org.springframework.stereotype.Service

@Service
class Service(private val repo: Repository,
              private val continuationTokenService: ContinuationTokenService
) {

    fun getById(id: String): TemplateModel {
        return repo.findById(id).orElseThrow {
            throw DataNotFoundException(ExceptionMessage.NO_DATA_FOUND)
        }
    }
    fun get(param: SearchParameters): MutableList<TemplateModel> {
        val result = mutableListOf<TemplateModel>()
        repo.getAll(
            param.limit!!,
            param.continuationToken?.startFromId
        ).also {
            continuationTokenService.updateContinuationToken(
                it!!, param
            )
        }?.parallelStream()?.forEach {
                result.add(it)
            }

        return result
    }

    fun update(id: String, entity: TemplateModel) {
        val templateModel: TemplateModel? = repo.getById(entity.id) ?:
                                                throw DataNotFoundException(ExceptionMessage.NO_DATA_FOUND)
        entity._id = templateModel!!._id
        repo.save(entity)
    }

    fun save(entity: TemplateModel) {
        repo.save(entity)
    }

    fun delete(id: String) {
        repo.delete(repo.findById(id).get())
    }
}

package pbs.agile.webapi.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pbs.agile.webapi.models.entities.Files

@Repository
interface FilesRepository : JpaRepository<Files, Long> {
    fun findAllByProject_Id(projectId: Long): List<Files>
}
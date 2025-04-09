package pbs.agile.webapi.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import pbs.agile.webapi.models.entities.Project
import pbs.agile.webapi.models.entities.User

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
    fun findAllByTitle(projectName: String): List<Project>
    fun findAllByUsers_Id(userId: Long): List<Project>
}

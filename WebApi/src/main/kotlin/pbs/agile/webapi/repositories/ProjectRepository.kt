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

    @Query(
        """
        SELECT p FROM Project p 
        WHERE p.owner.id = :userId 
           OR :memberId IN (SELECT u.id FROM p.users u)
        """
    )
    fun findAllByUserAssociation(@Param("userId") userId: Long?, @Param("memberId") memberId: Long?): List<Project>
}

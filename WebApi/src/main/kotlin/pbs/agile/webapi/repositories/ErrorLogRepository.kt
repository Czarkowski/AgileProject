package pbs.agile.webapi.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pbs.agile.webapi.models.entities.ErrorLog

@Repository
interface ErrorLogRepository : JpaRepository<ErrorLog, Long>
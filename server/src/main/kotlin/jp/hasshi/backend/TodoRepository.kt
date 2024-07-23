package jp.hasshi.backend

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TodoRepository: JpaRepository<TodoRecord, UUID> {
}
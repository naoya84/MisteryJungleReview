package jp.hasshi.backend

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TodoRepository: JpaRepository<TodoRecord, UUID> {
}
package jp.hasshi.backend

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity(name = "todos")
data class TodoRecord(
    @Id
    val id: UUID = UUID.randomUUID(),
    val todo: String
)

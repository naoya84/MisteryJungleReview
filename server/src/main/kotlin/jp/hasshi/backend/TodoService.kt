package jp.hasshi.backend

import org.springframework.stereotype.Service

interface TodoService {
    fun getTodos(): List<TodoRecord>
}

@Service
class DefaultTodoService(private val todoRepository: TodoRepository) : TodoService {
    override fun getTodos(): List<TodoRecord> {
        val todoRecords = todoRepository.findAll()
        return todoRecords
    }
}
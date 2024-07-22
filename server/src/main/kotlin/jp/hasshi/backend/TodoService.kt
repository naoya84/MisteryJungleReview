package jp.hasshi.backend

import org.springframework.stereotype.Service

interface TodoService {
    fun getTodos(): List<Todo>
}

@Service
class DefaultTodoService : TodoService {
    override fun getTodos(): List<Todo> {
        return emptyList()
    }
}
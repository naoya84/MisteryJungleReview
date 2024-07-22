package jp.hasshi.backend

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {
    @GetMapping
    fun getTodos(): List<Todo> {
        return todoService.getTodos()
    }
}
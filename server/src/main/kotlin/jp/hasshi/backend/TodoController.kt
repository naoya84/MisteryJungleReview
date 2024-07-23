package jp.hasshi.backend

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {
    @GetMapping
    fun getTodos(): List<TodoRecord> {
        return todoService.getTodos()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodo(@RequestBody todo: String): List<TodoRecord> {
        return todoService.postTodo(todo)
    }
}
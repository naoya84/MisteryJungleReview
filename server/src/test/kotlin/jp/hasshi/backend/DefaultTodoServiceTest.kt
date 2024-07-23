package jp.hasshi.backend

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class DefaultTodoServiceTest {
    @Autowired
    private lateinit var todoRepository: TodoRepository

    @Nested
    inner class GetTodos {
        @Test
        fun todoServiceのgetTodosを呼ぶと全てのTodoを返す() {
            todoRepository.saveAll(
                listOf(
                    TodoRecord(todo = "髪を切る"),
                    TodoRecord(todo = "自転車に乗る"),
                )
            )

            val todoService = DefaultTodoService(todoRepository)
            val todos = todoService.getTodos()

            assertEquals(2, todos.size)
            assertEquals("髪を切る", todos.first().todo)
            assertEquals("自転車に乗る", todos.last().todo)
        }
    }

    @Nested
    inner class PostTodo {
        @Test
        fun todoServiceのpostTodoを呼ぶとtodoが保存され全てのTodoを返す() {
            todoRepository.save(TodoRecord(todo = "髪を切る"))

            val todoService = DefaultTodoService(todoRepository)
            val todos = todoService.postTodo("自転車に乗る")

            val savedTodos = todoRepository.findAll()

            assertEquals(2, todos.size)
            assertEquals("髪を切る", todos.first().todo)
            assertEquals("自転車に乗る", todos.last().todo)
            assertEquals(2, savedTodos.size)
        }
    }
}
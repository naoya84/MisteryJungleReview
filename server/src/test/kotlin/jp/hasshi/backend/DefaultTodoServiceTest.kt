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

            assertEquals(todos.size, 2)
            assertEquals(todos.first().todo,"髪を切る")
            assertEquals(todos.last().todo,"自転車に乗る")
        }
    }
}
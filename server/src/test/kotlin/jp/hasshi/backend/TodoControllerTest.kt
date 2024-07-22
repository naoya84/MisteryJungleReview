package jp.hasshi.backend

import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
class TodoControllerTest {
    @Nested
    inner class GetTodos {
        @Test
        fun `GET api_todosにリクエストを送るとgetTodosが呼ばれる`() {
            val spyTodoService = SpyTodoService()
            val todoController = TodoController(spyTodoService)
            val mockMvc = MockMvcBuilders
                .standaloneSetup(todoController)
                .build()

            val result = mockMvc.perform(get("/api/todos"))
            result.andExpect(status().isOk)
            assertTrue(spyTodoService.getTodos_wasCalled)
        }

        @Test
        fun `getTodosの戻り値を返す`(){
            val stubTodoService = StubTodoService()
            stubTodoService.getTodos_returnValue = listOf(
                TodoRecord(todo = "カレーを食べる"),
                TodoRecord(todo = "お皿を片付ける")
            )

            val todoController = TodoController(stubTodoService)
            val mockMvc = MockMvcBuilders
                .standaloneSetup(todoController)
                .build()

            val result = mockMvc.perform(get("/api/todos"))

            result
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[0].todo", equalTo("カレーを食べる")))
                .andExpect(jsonPath("$[1].todo", equalTo("お皿を片付ける")))
        }
    }
}
package jp.hasshi.backend

import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.match.MockRestRequestMatchers.content
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.servlet.function.RequestPredicates.contentType
import kotlin.test.assertEquals

@SpringBootTest
class TodoControllerTest {
    @Nested
    inner class GetTodos {
        @Test
        fun `GET api_todosにリクエストを送るとTodoServiceのgetTodosが呼ばれる`() {
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
        fun `TodoServiceのgetTodosの戻り値を返す`(){
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

    @Nested
    inner class PostTodo {
        @Test
        fun `POST post_todoにリクエストを送るとTodoServiceのpostTodoが呼ばれる`(){
            val spyTodoService = SpyTodoService()
            val todoController = TodoController(spyTodoService)

            val mockMvc = MockMvcBuilders
                .standaloneSetup(todoController)
                .build()

//            val result = mockMvc.perform(
//                post("/api/todos")
//                    .contentType(MediaType.TEXT_PLAIN)
//                    .content("歯を磨く")
//                    .characterEncoding("UTF-8")
//            )
            val result2 = mockMvc.post("/api/todos") {
                contentType = MediaType.TEXT_PLAIN
                content = "歯を磨く"
                characterEncoding = "UTF-8"
            }


            result2.andExpect { status { isCreated() } }
//            result.andExpect(status().isCreated)
            assertEquals("歯を磨く", spyTodoService.postTodo_argument_todo)
        }

        @Test
        fun `POST TodoServiceのpostTodoの返り値を返す`(){
            val stubTodoService = StubTodoService()
            stubTodoService.postTodo_returnValue = listOf(
                TodoRecord(todo = "ランニングする"),
                TodoRecord(todo = "ウォーキングする")
            )
            val todoController = TodoController(stubTodoService)

            val mockMvc = MockMvcBuilders
                .standaloneSetup(todoController)
                .build()

            val result = mockMvc.post("/api/todos") {
                contentType = MediaType.TEXT_PLAIN
                content = "dummy"
                characterEncoding = "UTF-8"
            }

            result.andExpect { jsonPath("$[0].todo") { value("ランニングする") } }
            result.andExpect { jsonPath("$[1].todo") { value("ウォーキングする") } }
        }
    }
}
package jp.hasshi.backend

class SpyTodoService: TodoService {
    var getTodos_wasCalled: Boolean = false

    override fun getTodos(): List<TodoRecord> {
        getTodos_wasCalled = true
        return emptyList()
    }

    var postTodo_argument_todo: String? = null

    override fun postTodo(todo: String): List<TodoRecord> {
        postTodo_argument_todo = todo
        return emptyList()
    }
}

class StubTodoService: TodoService {
    var getTodos_returnValue: List<TodoRecord> = emptyList()

    override fun getTodos(): List<TodoRecord> {
        return getTodos_returnValue
    }

    var postTodo_returnValue: List<TodoRecord> = emptyList()
    override fun postTodo(todo: String): List<TodoRecord> {
        return postTodo_returnValue
    }
}
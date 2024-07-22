package jp.hasshi.backend

class SpyTodoService: TodoService {
    var getTodos_wasCalled: Boolean = false

    override fun getTodos(): List<TodoRecord> {
        getTodos_wasCalled = true
        return emptyList()
    }
}

class StubTodoService: TodoService {
    var getTodos_returnValue: List<TodoRecord> = emptyList()

    override fun getTodos(): List<TodoRecord> {
        return getTodos_returnValue
    }
}
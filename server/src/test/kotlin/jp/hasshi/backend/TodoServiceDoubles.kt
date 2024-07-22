package jp.hasshi.backend

class SpyTodoService: TodoService {
    var getTodos_wasCalled: Boolean = false

    override fun getTodos(): List<Todo> {
        getTodos_wasCalled = true
        return emptyList()
    }
}

class StubTodoService: TodoService {
    var getTodos_returnValue: List<Todo> = emptyList()

    override fun getTodos(): List<Todo> {
        return getTodos_returnValue
    }
}
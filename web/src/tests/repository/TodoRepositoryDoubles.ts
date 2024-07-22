import {TodoRepository} from "../../repository/TodoRepository.ts";
import {TodoResponse} from "../../model/TodoResponse.ts";

export class DummyTodoRepository implements TodoRepository {
    getTodos(): Promise<TodoResponse[]> {
        return Promise.resolve([{todo: "dummy-todo"}]);
    }
}

export class SpyTodoRepository implements TodoRepository {
    getTodos_wasCalled: boolean = false

    getTodos(): Promise<TodoResponse[]> {
        this.getTodos_wasCalled = true
        return Promise.resolve([{todo: "spy-todo"}])
    }
}

export class StubTodoRepository implements TodoRepository {
    getTodos_returnValue: Promise<TodoResponse[]> = Promise.resolve([{todo: ""}])
    getTodos(): Promise<TodoResponse[]> {
        return this.getTodos_returnValue
    }
}
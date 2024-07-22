import {TodoResponse} from "../model/TodoResponse.ts";
import {Http, NetworkHttp} from "../http/NetworkHttp.ts";

export interface TodoRepository {
    getTodos(): Promise<TodoResponse[]>
}

export class DefaultTodoRepository implements TodoRepository {
    http: Http

    constructor(http: Http = new NetworkHttp()) {
        this.http = http
    }

    getTodos(): Promise<TodoResponse[]> {
        return this.http.get('/api/todos') as Promise<TodoResponse[]>
    }
}
import {describe, expect, test} from "vitest";
import {DefaultTodoRepository} from "../../repository/TodoRepository.ts";
import {SpyHttp, StubHttp} from "../http/HttpDoubles.ts";

describe('ToDoRepository', () => {
    describe('getTodos', () => {

        test('httpに正しいリクエストを渡す', () => {
            const spyHttp = new SpyHttp()
            const todoRepository = new DefaultTodoRepository(spyHttp)

            todoRepository.getTodos()

            expect(spyHttp.get_argument_url).toEqual('/api/todos')
        })

        test('httpの返り値をキャストして返す', async () => {
            const stubHttp = new StubHttp()
            stubHttp.get_returnValue = Promise.resolve(
                [
                    {todo: '歯を磨く'},
                    {todo: '服を選択する'},
                ]
            )
            const todoRepository = new DefaultTodoRepository(stubHttp)
            const todos = await todoRepository.getTodos()

            expect(todos.length).toEqual(2)
            expect(todos).toEqual([
                {todo: '歯を磨く'},
                {todo: '服を選択する'},
            ])
        })
    })

    describe('postTodo', () => {
        test('httpに正しいリクエストを渡す', () => {
            const spyHttp = new SpyHttp()
            const todoRepository = new DefaultTodoRepository(spyHttp)

            todoRepository.postTodo("歯を磨く")

            expect(spyHttp.post_argument_url).toEqual("/api/todos")
            expect(spyHttp.post_argument_body).toEqual("歯を磨く")
        })

        test('httpの返り値をキャストして返す', async () => {
            const stubHttp = new StubHttp()
            stubHttp.post_returnValue = Promise.resolve(
                [
                    {todo: "ランニングする"},
                    {todo: "ウォーキングする"}
                ]
            )
            const todoRepository = new DefaultTodoRepository(stubHttp)

            const response = await todoRepository.postTodo("歯を磨く")

            expect(response.length).toEqual(2)
            expect(response).toEqual([
                {todo: "ランニングする"},
                {todo: "ウォーキングする"}
            ])
        })
    })
})
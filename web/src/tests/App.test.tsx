import {describe, expect, test} from "vitest";
import App from "../App.tsx";
import {act, render, screen} from "@testing-library/react";
import {DummyTodoRepository, SpyTodoRepository, StubTodoRepository} from "./repository/TodoRepositoryDoubles.ts";
import {TodoRepository} from "../repository/TodoRepository.ts";


describe('App', () => {
    test('TODOという文字が表示されている', async () => {
        await renderApp(new DummyTodoRepository())

        expect(screen.getByText('todo')).toBeInTheDocument()
    })

    test('初期レンダリング時にToDoRepositoryのgetTodosを呼ぶ', async () => {
        const spyTodoRepository = new SpyTodoRepository()
        await renderApp(spyTodoRepository)

        expect(spyTodoRepository.getTodos_wasCalled).toBeTruthy()
    })

    test('初期レンダリング時にTODOを表示する', async () => {
        const stubTodoRepository: StubTodoRepository = new StubTodoRepository()
        stubTodoRepository.getTodos_returnValue = Promise.resolve([{todo: "stub-todo"}])
        await renderApp(stubTodoRepository);

        expect(screen.getByText("stub-todo")).toBeInTheDocument()
    })

    async function renderApp(todoRepository: TodoRepository) {
        await act(async () => {
            render(<App todoRepository={todoRepository}/>)
        })
    }
})
import {describe, expect, test} from "vitest";
import App from "../App.tsx";
import {act, render, screen} from "@testing-library/react";
import {DummyTodoRepository, SpyTodoRepository, StubTodoRepository} from "./repository/TodoRepositoryDoubles.ts";
import {TodoRepository} from "../repository/TodoRepository.ts";
import userEvent from "@testing-library/user-event";


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

    test('フォームと登録ボタンが表示されている', async () => {
        await renderApp(new DummyTodoRepository())

        const inputForm = screen.getByLabelText('New Todo') //labelの中にあるHTML要素(input)を探す
        // screen.getByRole("textbox")
        const button = screen.getByText("Save")

        expect(inputForm).toBeInTheDocument()
        expect(button).toBeInTheDocument()
    })

    test('登録ボタンを押すとTodoRepositoryのpostTodoを呼ぶ', async () => {
        const spyTodoRepository = new SpyTodoRepository()
        await renderApp(spyTodoRepository)

        const inputForm = screen.getByLabelText("New Todo")
        const button = screen.getByText("Save")
        await userEvent.type(inputForm, "歯を磨く")
        await userEvent.click(button)

        expect(spyTodoRepository.postTodo_argument_todo).toEqual("歯を磨く")
    })

    test('登録ボタンを押すとTodoRepositoryのpostTodoの返り値が表示される', async () => {
        const stubTodoRepository = new StubTodoRepository()
        stubTodoRepository.postTodo_returnValue = Promise.resolve([
            {todo: "todo1 repository returns"},
            {todo: "todo2 repository returns"},
        ])

        await renderApp(stubTodoRepository)

        const inputForm = screen.getByLabelText("New Todo")
        const button = screen.getByText("Save")
        await userEvent.type(inputForm, "todo user typed")
        await userEvent.click(button)

        expect(screen.getByText("todo1 repository returns")).toBeInTheDocument()
        expect(screen.getByText("todo2 repository returns")).toBeInTheDocument()
    })


    test('登録ボタンを押すとフォームの中のテキストが消える', async () => {
        await renderApp(new DummyTodoRepository())

        const inputForm= screen.getByLabelText("New Todo") as HTMLInputElement
        const button = screen.getByText("Save")
        await userEvent.type(inputForm, "todo")
        await userEvent.click(button)

        expect(inputForm.value).toBe("")
    })

    async function renderApp(todoRepository: TodoRepository) {
        await act(async () => {
            render(<App todoRepository={todoRepository}/>)
        })
    }
})
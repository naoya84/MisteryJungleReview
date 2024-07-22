import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import {DefaultTodoRepository} from "./repository/TodoRepository.ts";

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App  todoRepository={new DefaultTodoRepository()}/>
  </React.StrictMode>,
)

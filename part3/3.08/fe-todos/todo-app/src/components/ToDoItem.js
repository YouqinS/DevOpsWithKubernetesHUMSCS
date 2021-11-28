import React from "react";

const ToDoItem = ({ todo }) => {

    console.log(todo);
    return (
        <tr>
            <td>{todo.content}</td>
            <td>{todo.status}</td>
        </tr>
    )
}

export default ToDoItem
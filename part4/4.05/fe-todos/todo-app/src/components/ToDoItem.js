import React from "react";
import ToDoService from "../services/ToDoService";

const ToDoItem = ({todo, fetchToDos}) => {

    console.log("ToDoItem")
    console.log(todo);

    function setToDone(todo) {
        ToDoService.setToDone(todo.id).then(r => {
            console.log("ToDoService.setToDone response: ")
            console.log(r)
            fetchToDos()
            }
        )
    }

    return (
        <tr style={{background: todo.status === "Done" ? "lightgrey" : "white"}}>
            <td>{todo.content}</td>
            <td>{todo.status}</td>
            <td>
                <button onClick={() => setToDone(todo)}>OK</button>
            </td>
        </tr>
    )
}

export default ToDoItem
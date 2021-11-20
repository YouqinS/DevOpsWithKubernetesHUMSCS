import React, { useState, useEffect } from 'react'
import ToDoService from './services/ToDoService.js';
import './styles/App.css'
import ToDoItem from './components/ToDoItem'

function App() {
  const todoExample = [{
    "content": "clean the house",
    "status": "to do"
  },
  {
    "content": "go to shop",
    "status": "doing"
  }]

  const [toDos, setToDos] = useState(todoExample)
  const [newToDo, setNewToDo] = useState('')
  const [newStatus, setNewStatus] = useState('Todo')
  const [image, setImage] = useState("")


  useEffect(() => {
    ToDoService.getToDos()
      .then(response => {
        setToDos(response)
      })
  }, [])


  const handleToDoChange = (event) => {
    setNewToDo(event.target.value)
  }


  const handleStatusChange = (event) => {
    setNewStatus(event.target.value)
  }


  const addToDo = (event) => {
    event.preventDefault()

    const newItem = {
      content: newToDo,
      status: newStatus
    }
    console.log("newItem" + newItem);
    // setToDos(toDos.concat(newItem))
    ToDoService.createToDo(newItem).then(
      response => {
        setToDos(toDos.concat(response))
        setNewToDo('')
      })
  }

  const options = [
    {
      label: "Todo",
      value: "Todo",
    },
    {
      label: "Doing",
      value: "Doing",
    },
    {
      label: "Done",
      value: "Done",
    },

  ];

  useEffect(() => {
    ToDoService.getImage()
      .then(response => {
        console.log(response);
        const base64 = btoa(
          new Uint8Array(response).reduce(
            (data, byte) => data + String.fromCharCode(byte),
            '',
          ),
        );
        setImage("data:;base64," + base64);
      })
  }, [])


  return (
    <div className="App">
      <header className="ToDoApp"></header>

      <div>
        {/* <img src="https://picsum.photos/1200" alt="today" height="200"/> */}
        <img src={image} alt="today" height="200" />
      </div>

      <form onSubmit={addToDo}>
        <div className="select-container">
          <input type="text" maxLength="140" value={newToDo} onChange={handleToDoChange} />

          <select value={newStatus} onChange={handleStatusChange}>
            {options.map((option) => (
              <option value={option.value}>{option.label}</option>
            ))}
          </select>
        </div> <br />

        <button type="submit">Create TODO</button>
      </form><br />

      <div>
        <table>
          <table>
            <tr>
              <th>ToDo</th>
              <th>Status</th>
            </tr>
            {toDos.map((todo) => <ToDoItem key={todo.content} todo={todo} />)}
          </table>
        </table>

      </div>
    </div>
  );
}

export default App;

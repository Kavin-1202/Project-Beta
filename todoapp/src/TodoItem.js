import React from 'react';
import './TodoItem.css';

const TodoItem = ({ todo, toggleComplete, removeTodo }) => {
  return (
    <div className={`todo-item ${todo.completed ? 'completed' : ''}`}>
      <span onClick={() => toggleComplete(todo.id)}>
        {todo.text}
      </span>
      <button onClick={() => removeTodo(todo.id)}>X</button>
    </div>
  );
};

export default TodoItem;

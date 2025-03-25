import logo from './logo.svg';
import './App.css';
import TaskList from './components/TaskList';
import { useState, useEffect } from 'react';
import taskData from './tasks-data.json';
import AddTaskForm from "./components/AddTaskForm";
import { v4 } from "uuid";

const LOCAL_STORAGE_KEY = "tasks"; // Klucz do localStorage

function App() {
  const [tasks, setTasks] = useState([]);

  // Wczytywanie zadań z localStorage, jeśli są dostępne
  useEffect(() => {
    const savedTasks = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));

    // Jeśli są zapisane zadania, to je załaduj
    if (savedTasks && savedTasks.length > 0) {
      setTasks(checkOverdueTasks(savedTasks));
    } else {
      // załaduj zadania z pliku
      setTasks(checkOverdueTasks(taskData));
    }
  }, []);

  // Funkcja sprawdzająca, czy zadania są przeterminowane (uwzględnia datę i godzinę)
  const checkOverdueTasks = (taskList) => {
    const now = new Date();
    return taskList.map(task => {
      const taskDeadline = new Date(task.deadline.replace(" ", "T"));
      return task.status === "oczekujące" && taskDeadline < now
        ? { ...task, status: "przeterminowane" }
        : task;
    });
  };
  // Funkcja sprawdzająca przeterminowanie zadań co minutę
  useEffect(() => {
    const intervalId = setInterval(() => {
      const updatedTasks = checkOverdueTasks(tasks);
      setTasks(updatedTasks);
      saveTasksToLocalStorage(updatedTasks);
    }, 10000); // Co 10 000 ms (0.1 minuta)

    // Czyszczenie interwału przy odmontowaniu komponentu
    return () => clearInterval(intervalId);
  }, [tasks]); 


  // Zapisanie zadań do localStorage
  const saveTasksToLocalStorage = (updatedTasks) => {
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(updatedTasks));
  };

  // Zmiana statusu zadania
  const handleStatusChange = (id) => {
    const updatedTasks = tasks.map(task =>
      task.id === id
        ? { ...task, status: task.status === "oczekujące" ? "wykonane" : "oczekujące" }
        : task
    );
    setTasks(updatedTasks);
    saveTasksToLocalStorage(updatedTasks);
  };

  // Funkcja dodawania nowego zadania
  const handleNewTask = (title, details, deadline, status, difficulty) => {
    const newTask = {
      id: v4(),
      title,
      details,
      deadline,
      status,
      difficulty,
    };
    const updatedTasks = checkOverdueTasks([...tasks, newTask]);
    setTasks(updatedTasks);
    saveTasksToLocalStorage(updatedTasks);
  };

  // Ocena trudności
  const handleRateTask = (id, difficulty) => {
    const updatedTasks = tasks.map(task =>
      task.id === id ? { ...task, difficulty } : task
    );
    setTasks(updatedTasks);
    saveTasksToLocalStorage(updatedTasks);
  };

  // Usuwanie zadania
  const handleRemoveTask = (id) => {
    const updatedTasks = tasks.filter(task => task.id !== id);
    setTasks(updatedTasks);
    saveTasksToLocalStorage(updatedTasks);
  };

  return (
    <>
      < AddTaskForm onNewTask={handleNewTask} />

      <TaskList
        tasks={tasks}
        onRateTask={handleRateTask}
        onRemoveTask={handleRemoveTask}
        onStatusChange={handleStatusChange}
      />
    </>
  );
}

export default App;

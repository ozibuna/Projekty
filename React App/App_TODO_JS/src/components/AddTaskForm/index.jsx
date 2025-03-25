import {useInput} from '../hooks';
import { useState } from 'react';
import { FaPlus, FaTimes } from 'react-icons/fa';
import StarRating from '../StarRating';
import '../../index.css';

export default function AddTaskForm ({onNewTask = f => f}) {
    const [title, setTitle] = useState("");
    const [details, setDetails] = useState("");
    const [date, setDate] = useState(""); // Data YYYY-MM-DD
    const [time, setTime] = useState(""); // hh:mm
    const [status, setStatus] = useState("oczekujące");
    const [difficulty, setDifficulty] = useState(1);
    const [showForm, setShowForm] = useState(false);
    
    const submit = (event) => {
        event.preventDefault();
        const deadline = `${date} ${time}`;

        console.log("Dodawane zadanie:", { title, details, deadline, status, difficulty }); 
        onNewTask(title, details, deadline, status, difficulty);
        setTitle("");
        setDetails("");
        setDate("");
        setTime("");
        setStatus("oczekujące");
        setDifficulty(1);
        setShowForm(false);
    };
    
    return (
        <section className="task-form">
            <div className ="task-form-header">
                <h2>Moje TODO</h2>
                <button className="task-button" onClick={() => setShowForm(!showForm)}>
                    {showForm ? <FaTimes /> : <FaPlus />}
                </button>
            </div>

            {/**Ukryty formularz dopóki showForm === true */}
            {showForm &&(
            <form onSubmit={submit} className="task-form-content">
                <div className="form-group">
                    <label>Nazwa zadania:</label>
                    <input
                        type="text"
                        value={title}
                        onChange={(e) =>setTitle(e.target.value)}
                        placeholder="Wpisz nazwę..."
                        required />
                </div>
                <div className="form-group">
                    <label>Opis zadania:</label>
                    <textarea 
                        value={details} 
                        onChange={(e) => setDetails(e.target.value)} 
                        placeholder="Wpisz szczegóły..." 
                        required />
                </div>
                {/* Pole wyboru daty */}
                    <div className="form-group">
                        <label>Data wykonania:</label>
                        <input
                            type="date"
                            value={date}
                            onChange={(e) => setDate(e.target.value)}
                            required/>
                    </div>

                    {/* Pole wyboru godziny */}
                    <div className="form-group">
                        <label>Godzina wykonania:</label>
                        <input
                            type="time"
                            value={time}
                            onChange={(e) => setTime(e.target.value)}
                            required/>
                    </div>
                    
                    <div className="form-group">
                        <label>Status:</label>
                        <select value={status} onChange={(e) => setStatus(e.target.value)}>
                        <option value="oczekujące">Oczekujące</option>
                        <option value="wykonane">Wykonane</option>
                        <option value="przeterminowane">Przeterminowane</option>
                    </select>
                    </div>
                    {/* Poziom trudności */}
                    <div className="form-group">
                        <label>Poziom trudności:</label>
                        <StarRating selectedStars={difficulty} onRate={setDifficulty} />
                </div>
                <button type="submit" className ="task-button">DODAJ</button>
                </form>
                )}
        </section>
    );
}



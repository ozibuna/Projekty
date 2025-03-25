import React from 'react';
import Task from '../Task'

export default function TaskList({ 
        tasks=[],
        onRemoveTask = f => f,
        onRateTask = f => f,
        onStatusChange = f => f
    }) {

        if(!tasks.length) return <div> Brak zadań. (Dodaj zadanie)</div>;
        return (
            <div className="task-list">
                {
                    tasks.map(task => (
                        <Task 
                            key={task.id}
                            {...task}
                            onRemove={onRemoveTask}
                            onRate={onRateTask}
                            onStatus={onStatusChange}
                        />)
                    )
                }
            </div>
        );
}   




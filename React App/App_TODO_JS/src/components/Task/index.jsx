import { FaTrash, FaCheck, FaUndo, FaCheckCircle, FaSpinner, FaExclamationCircle } from 'react-icons/fa';
import StarRating from '../StarRating';
import '../../index.css';


export default function Task({
        id,
        title,
        color,
        details,
        deadline,
        status,
        difficulty,
        onRemove = f => f,
        onRate = f => f,
        onStatus = f => f
}) {

    // Konwersja daty na czytelny format
  const formattedDeadline = new Date(deadline.replace(" ", "T")).toLocaleString("pl-PL", {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit"
  });
    
    // Kolor tła zależny od statusu
  const statusColor = {
    "oczekujące": color,
    "wykonane": "#4CAF50",
    "przeterminowane": "#e74c3c"
  };
  
  // Ikony statusu
  const statusIcon = {
    "oczekujące": <FaSpinner className="spinner" color="#3498db" />,
    "wykonane": <FaCheckCircle color="green" />,
    "przeterminowane": <FaExclamationCircle color="#cc0000" />
    };
    
    return (
         <section className={`task ${status}`} style={{ backgroundColor: statusColor[status] }}>
      <h1>{title} {statusIcon[status]}</h1>
            <p>{details}</p>
            <p><strong>Termin:</strong> {formattedDeadline}</p>
            <p><strong>Status:</strong> {status}</p>

            {/*Przycisk zmiany statusu*/}
            {status !== "przeterminowane" && (
                <button onClick={() => onStatus(id)} className="task-button">
                    {status === "wykonane" ? <FaUndo /> : <FaCheck />} {status ==="wykonane" ? "Cofnij" : "Wykonaj"}
                </button>
            )}

            {/* Przycisk usuwania - tylko dla wykonanych lub przeterminowanych */}
            {(status === "wykonane" || status === "przeterminowane") && (
                <button
                    onClick={() => onRemove(id)} className="task-button delete">
                    <FaTrash /> Usuń
                </button>
            )}

            {/* Ocena trudności - można zmieniać tylko dla "oczekujących" */}
            {status === "oczekujące" && (
                <StarRating
                    selectedStars={difficulty}
                    onRate={(difficulty) => onRate(id, difficulty)}
                />
            )}
        </section>
    );
}



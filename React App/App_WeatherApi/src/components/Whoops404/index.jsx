import React from "react";
import { useLocation } from "react-router";
import { Link } from "react-router-dom";

export function Whoops404() {
    let location = useLocation();
    console.log(location);

    return (
        <div className="App">
        <header className="App-header">
            <h1>Nie znaleziono zasobu pod adresem {location.pathname}</h1>
             {/* Link to Home page */}
                <nav className = "navbar">
                    <Link className ="App-link" to="/" style={{ padding: '10px' }}>Home</Link>
                </nav>
                </header>
        </div>
    )
}


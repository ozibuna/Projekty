import React from "react"
import { Link } from "react-router-dom";;

export function Contact() {
    return (
        <section className="App">
            <header className="App-header">
            <h2>Contact Us</h2>
             {/* Link to Home page */}
            <nav className="navbar">
                <Link className="App-link" to="/" style={{ padding: '10px' }}>Home</Link>
                </nav>
            </header>
            <p>If you have any questions, feel free to reach out!</p>
            <ul>
                <li>Email: support@weathertracker.com</li>
                <li>Address: Mikrus, Warsaw, Poland</li>
            </ul>
        </section>
    );
}




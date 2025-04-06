import React from "react";
import { Link } from "react-router-dom";
import { FaCloudSun, FaListUl, FaTools, FaRocket, FaExternalLinkAlt, FaPhoneAlt } from "react-icons/fa";


export function Home() {
    return (
        <div className="App">
            <header className="App-header">
            <h1><FaCloudSun /> Weather Tracker</h1>
            <nav className = "navbar">
                <Link to="/services" style={{ padding: "10px" }}><FaCloudSun /> Check Weather</Link>
                <Link to="/contact" style={{ padding: "10px" }}><FaPhoneAlt /> Contact Us</Link>
                </nav>
            <p>
                Weather Tracker is a simple web application that allows users to check the current 
                weather conditions for multiple cities. It fetches real-time weather data from 
                <strong> Visual Crossing Weather API </strong> and stores previously searched cities 
                in <strong> local storage </strong> to optimize performance.
            </p>
            </header>

            <h2><FaListUl /> Features</h2>
            <ul>
                <li> Check the weather in various cities around the world</li>
                <li> Add new cities and display their weather information</li>
                <li> Save weather data locally to avoid unnecessary API calls</li>
                <li> Automatically convert temperatures to Celsius</li>
                <li> Navigation bar to access services and contact pages</li>
            </ul>

            <h2><FaRocket /> How It Works?</h2>
            <p><strong>1. Weather Services:</strong> Displays weather information for multiple predefined cities (Warsaw, Kraków, Poznań). Users can add new cities.</p>
            <p><strong>2. Local Storage:</strong> Previously searched cities remain available even after refreshing the page.</p>
            <p><strong>3. Contact Page:</strong> Provides company contact details for support.</p>

            <h2><FaTools /> Technologies Used</h2>
            <ul>
                <li> React.js (Frontend Framework)</li>
                <li> React Router (Navigation)</li>
                <li> Visual Crossing API (Weather Data)</li>
                <li> LocalStorage (Saving searched cities)</li>
                <li> JavaScript, HTML, CSS</li>
            </ul>


           
        </div>
    );
}



import React, {useState} from "react";
import { WeatherInfo } from "../Weather";
import { Link } from "react-router-dom";

export function Services() {
    const [city, setCity] = useState("");  // tutaj zachowujemy dane miasta wpisanego przez usera
    const [cities, setCities] = useState(() => {
  // Sprawdzamy, czy w localStorage są zapisane dane
  const savedCities = localStorage.getItem("weatherCities");

  // Jeśli dane są zapisane w localStorage, używamy ich. W przeciwnym razie, używamy domyślnych miast.
  return savedCities ? JSON.parse(savedCities) : ["Kramatorsk", "Warsaw", "Bangkok"];
});
    // nowe miasta usera
    const handleAddCity = () => {
        if (!city) return;
        if (cities.includes(city)) {
            console.log(`City ${city} is already in the list. Loading cached data.`);
            setCity("");  // Clear input field
            return;
        }
        const newCities = [...cities, city];  // Add new city to cities list
        setCities(newCities);
        localStorage.setItem("weatherCities", JSON.stringify(newCities)); // Save cities list to localStorage

        setCity("");
    };
    
    return (
        <div className = "App">
            <header className = "App-header">
                <h2>Find out what the weather is like in your city</h2>

                {/* Link to Home page */}
                <nav className = "navbar">
                    <Link className ="App-link" to="/" style={{ padding: '10px' }}>Home</Link>
                </nav>
            </header>
                {/* Input for user to add a new city */}
                <input className="weather-card"
                    type="text" 
                    placeholder="Enter city" 
                    value={city} 
                    onChange={(e) => setCity(e.target.value)}  
                />
                <button className="button" onClick={handleAddCity}>Add City</button>
            {/*miasta wypisane z pogodą*/ }
            {cities.map(city => (
                <WeatherInfo key={city} city={city} />
            ))}
                </div> 
    );
}


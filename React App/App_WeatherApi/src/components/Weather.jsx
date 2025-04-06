import React, { useState, useEffect } from "react";

const loadJSON = key => key && JSON.parse(localStorage.getItem(key));
const saveJSON = (key, data) => localStorage.setItem(key, JSON.stringify(data));

export function WeatherInfo({ city }) { 
    const [data, setData] = useState(loadJSON(`weather:${city}`));
    const apiKey = "A294GFTFP6PZGTGTXR7D66E8H";
    
    useEffect(() => {
        if (!city) return;
        //sprawdzam, czy są dane w localstorage
        const cachedData = loadJSON(`weather:${city}`);
        if (cachedData) {
            console.log(`Loading cached data for ${city}`);
            setData(cachedData);
            return;
        }

        console.log(`Fetching new weather data for ${city}`);

        // Fetch data if not available in localStorage
        fetch(`https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/${city}?unitGroup=metric&key=${apiKey}&contentType=json`)
            .then(response =>  response.json())
            .then(responseData => {
                console.log("Received API data:", responseData);

                let temperature = responseData.currentConditions?.temp ?? responseData.days?.[0]?.temp;
                let humidity = responseData.currentConditions?.humidity ?? responseData.days?.[0]?.humidity;
                let description = responseData.currentConditions?.conditions ?? responseData.days?.[0]?.description;

                // Convert temperature to Celsius if API returns Fahrenheit
                if (temperature !== undefined && temperature > 45) {
                    temperature = ((temperature - 32) * 5) / 9;
                }

                const savedData = {
                    address: responseData.address || city,
                    temperature: temperature !== undefined ? temperature.toFixed(1) : "No data",
                    humidity: humidity !== undefined ? humidity.toFixed(1) : "No data",
                    description: description || "No description available"
                };

                console.log("Saving to localStorage:", savedData);
                saveJSON(`weather:${city}`, savedData); // Save to localStorage
                setData(savedData); // Update state with new data
            })
            .catch(error => console.error("Error fetching data:", error));
    }, [city]);

    if (!data) {
        return <div>Loading data...</div>;
    }

    return (
        <div className="weather-card">
        <h2>Weather in: {data.address}</h2>
        <ul>
            <li>Temperature: {data.temperature} °C</li>
            <li>Humidity: {data.humidity} %</li>
            <li>Description: {data.description}</li>
        </ul>
        </div>
    );
    }
import React, { useEffect, useState } from "react";
import { WeatherInfo } from "./components/Weather";
import './App.css';
import {Routes, Route} from "react-router-dom";
import {Home} from "./components/Home";
import {Contact} from "./components/Contact";
import {Whoops404} from "./components/Whoops404";
import { Services } from "./components/Services";

function App() {
  // const cities = [ "Kramatorsk", "Warszawa", "Kraków", "Poznań"];

  return (
    
    <div>
      <Routes>
        <Route path="/" element={<Home/>}/>
        <Route path="/services" element={<Services/>}/>
        <Route path="/contact" element={<Contact/>}/>
        <Route path="*" element={<Whoops404/>}/>
      </Routes>
      {/* {cities.map(city => (
      <WeatherInfo key={city} city={city} />
      ))} */}
    </div>
  );
}

export default App;

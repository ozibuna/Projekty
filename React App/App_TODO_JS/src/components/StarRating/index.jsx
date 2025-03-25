import React, {useState} from 'react';
import Star from '../Star';

const createArray = length => [...Array(length)];

export default function StarRating({
    totalStars=10, 
    selectedStars=0,
    onRate = f => f
}) 
{
    // Obsługuje kliknięcie na gwiazdkę
    const handleSelect = (i) => {
        onRate(i + 1); 
     };
    
    return (
        <div style={{ display: 'flex', gap: '5px' }}>
            
            {createArray(totalStars).map((n,i) => (
                <Star
                    key={i}
                    selected={i < selectedStars}
                    onSelect={()=> handleSelect(i)}
        />
            ))}
        </div>
    );
};




import React from 'react';
import {FaStar} from 'react-icons/fa';

const Star = ({selected = false, onSelect = f => f}) => (
    <>
        <FaStar
            color={selected ? "red" : "grey"} id="star"
            onClick={onSelect}
            style={{cursor: 'pointer', marginTop: '5px', marginRight: '1px'}}
        />
    </>
);

export default Star


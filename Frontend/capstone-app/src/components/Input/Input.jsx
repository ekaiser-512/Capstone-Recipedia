import React from 'react';

const Input = ({ label, type, name, id }) => {
    return (
        <div>
            <label htmlFor={id}>{label}</label>
            <input type={type} name={name} id={id} />
        </div>
    );
};

export default Input;
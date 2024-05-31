import React from 'react';

const Input = ({ label, type, name, id, onChange }) => {
    return (
        <div>
            <label htmlFor={id}>{label}</label>
            <input type={type} name={name} id={id} onChange={onChange}/>
        </div>
    );
};

export default Input;
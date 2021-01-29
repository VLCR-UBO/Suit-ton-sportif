import React from 'react';

export default function Header() {
    return (
        <header style={headerStyle}>
            <h1>Suis ton sportif ! </h1>

        </header>
    )
}

const headerStyle = {
    color : "#000",
    textAlign : "center",
    padding : "10 px"
};
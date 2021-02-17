import React from 'react';
import logout from "../icon/logout.png";

export default class Parametre extends React.Component{
    deconnexion(){
        this.props.chargerSportif(-1);
        this.props.charger('connexion');
    }

    render(){
        return (
            <div style={{textAlign: "right"}}>
                <button onClick={() => this.deconnexion()} style={style.bouton}>
                    <img src={logout} alt="deconnexion" width="30" height="30"></img>
                </button>
            </div>
        );
    }
}

const style={
    bouton : {
        margin : "5px",
        backgroundColor : 'white',
        borderWidth : "0px",
    }
}
import React from 'react';

export default class Resultat extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            listQuestion : ['a', 'b', 'c'],
            listReponse : [1, 2, 3],
            chargement : true
        }
    }

    modifierQuestion(nom){

    }

    envoieQuestionnaire(){

    }

    retourChoixQuestionnaire(){

    }

    render(){
        const listItems = this.state.listQuestion.map((nom) =>
            <button onClick={() => this.modifierQuestion(nom)} className="item">
                <li className="list-group-item" key={nom}>{nom} <br/> -&gt; {nom}</li>
            </button>
        );
        return(
            <div className="container">
                <h2 className="title">Vous avez fini le questionnaire !</h2>
                <p>Vous pouvez cliquer sur une question pour en changer la réponse</p>
                <ul className="list-group" style={style.liste}>
                    {listItems}
                </ul>
                <div style={style.barreBouton}>
                    <button onClick={() => this.envoieQuestionnaire()} style={style.bouton}>Valider</button>
                    <button onClick={() => this.retourChoixQuestionnaire()} style={style.bouton}>Annuler</button>
                </div>
            </div>
        );
    }
}

const style = { 
    liste : {
        padding : "5px"
    },
    barreBouton : {
        textAlign : "center"
    },
    bouton : {
        backgroundColor : "yellowgreen",
        color: "white",
        border: "none",
        padding: "10px",
        fontSize: "16px"
    }
};
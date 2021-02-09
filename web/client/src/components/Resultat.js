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

    fusionListe(){
        let liste = [];
        for(let i=0; i<this.state.listQuestion.length; i++){
            liste.push(<div>{this.state.listQuestion[i]} <br/> -&gt; {this.state.listReponse[i]}</div>);
        }
        return liste;
    }

    modifierQuestion(nom){

    }

    envoieQuestionnaire(){

    }

    retourChoixQuestionnaire(){
        this.props.changer('questionnaire');
    }

    //envoyer les reponses

    render(){
        const listItems = this.fusionListe().map((nom) =>
            <button onClick={() => this.modifierQuestion(nom)} className="item">
                <li className="list-group-item" key={nom}>{nom}</li>
            </button>
        );
        return(
            <div className="container">
                <div style={{textAlign : "right"}}>
                    <button style={style.lien} onClick={() => this.retourChoixQuestionnaire()}>Retour à la liste des questionnaires</button>
                </div>
                <h2 className="title">Vous avez fini le questionnaire !</h2>
                <p>Vous pouvez cliquer sur une question pour en changer la réponse</p>
                <ul className="list-group" style={style.liste}>
                    {listItems}
                </ul>
                <div style={style.barreBouton}>
                    <button onClick={() => this.envoieQuestionnaire()} style={style.bouton}>Valider</button>
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
    },
    lien : {
        border: "none",
        backgroundColor : "transparent",
        fontSize: "12px",
        textDecoration : "underline"
    }
};
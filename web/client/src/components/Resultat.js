import React from 'react';
import Axios from 'axios';

export default class Resultat extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            listQuestion : this.props.question,
            listReponse : this.props.reponses,
            chargement : true
        }
    }

    fusionListe(){
        let liste = [];
        for(let i=0; i<this.state.listQuestion.length; i++){
            liste.push(<div>{this.state.listQuestion[i]} <br/> -&gt; {this.state.listReponse[i] === 0 ? 'Non' : 'Oui'}</div>);
        }
        return liste;
    }

    modifierQuestion(nom){
        let tab = this.fusionListe();
        for(let i=0; i<tab.length; i++){
            if(tab[i].props.children[0] === nom.props.children[0]){
                this.props.changerNumQuestion(i);
                this.props.changerChangement(true);
            }
        }
        this.props.charger('question');
    }

    envoieQuestionnaire(){
        const questions = this.state.listQuestion;
        const reponses = this.state.listReponse;
        const sportif = this.props.sportif;

        const date = new Date();
        const day = date.getDate();
        const month = date.getMonth();
        const year = date.getFullYear();

        const fulldate = {
            day : day,
            month : month,
            year : year
        };

        const reponsesQuestionnaire = {
            question: "",
            reponse:"",
            sportif : sportif,
            date : fulldate

        };
        for(var i=0; i<questions.length; i++){
            reponsesQuestionnaire.question = questions[i];
            reponsesQuestionnaire.reponse = reponses[i];

            Axios.post('/reponse/insertion',{reponsesQuestionnaire})
            .then(res =>{
                if(res.data !== undefined){
                    
                    // switch (res.data){
                    //     case "SUCESS":
                    //         alert("Les réponses ont été enregistrées, retour à la liste des questionnaires");
                    //         /* faire le retour à la liste des questionnaires */
                    //         break;
                    //     default:
                    //         alert("Erreur : les réponses n'ont pas été enregistrées, veuillez recommencer.");
                    //         /* faire le retour à la liste des questionnaires */
                    //         break;
                    // }
                }
            });
            this.props.charger('questionnaire');
            

        }

        
    }

    retourChoixQuestionnaire(){
        this.props.changerChangement(false);
        this.props.charger('questionnaire');
    }

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
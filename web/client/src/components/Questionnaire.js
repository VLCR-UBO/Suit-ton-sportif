import Axios from 'axios';
import React from 'react';
import '../loader.css'

export default class Questionnaire extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            list : [],
            chargement : true
        }
    }
    

    componentDidMount(){
        this.setState({chargement : false});
        this.chargerListe();
    };

    chargerQuestionnaire(nom){
        this.props.nomQuestionnaire(nom);
        this.props.charger('question');
    };

    chargerListe(){
        Axios.get('/questionnaire/listeQuestionnaire')
        .then(res=>{
            if(res.data!==undefined){
                switch (res.data){
                    case "QUESTVIDE":
                        alert("Il n'y a pas de questionnaire, veuillez en ajouter");
                        break;

                    default:
                        var i=0;
                        var tab = [];
                        while(i<res.data.length){
                            tab[i] = res.data[i].intituleQuestionnaire;
                            i++;
                        }
                        
                        this.setState({list : tab});
                        
                }

            }
        });
    }


    render(){
        console.log("La liste : "+this.state.list);
        const listItems = this.state.list.map((nom) =>
            <button onClick={() => this.chargerQuestionnaire(nom)} className="item">
                <li className="list-group-item" key={nom}>{nom}</li>
            </button>
        );
        if(this.state.chargement){
            return(
                <div className="loader">
                </div> 
            );
        }else {
            return (
                <div className="container">
                    <h2 className="title">Choisissez un questionnaire</h2>
                    <ul id="list" className="list-group" style={style.liste}>
                        {listItems}
                    </ul> 
                </div>
            );
        }
    }
}

const style = {
    liste : {
        padding : "5px"
    }
};

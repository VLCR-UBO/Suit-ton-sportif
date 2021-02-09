import React from 'react';
import '../loader.css'

export default class Questionnaire extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            list : ['a', 'b', 'c'],
            chargement : true
        }
    }

    componentDidMount(){
        this.setState({chargement : false});
    }

    chargerQuestionnaire(nom){
        this.props.nomQuestionnaire(nom);
        this.props.charger('question');
    }

    //charger la liste des questions

    render(){
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

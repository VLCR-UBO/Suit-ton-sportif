import React from 'react';
import '../loader.css'

export default class Questionnaire extends React.Component {
    constructor(props) {
        super(props);
        this.liste = React.createRef();  
        this.state = {
            list : ['a', 'b', 'c'],
            chargement : true
        }
    }

    componentDidMount(){
        this.setState({chargement : false});
    }

    chargerQuestionnaire(nom){
        console.log(nom);
    }

    render(){
        const listItems = this.state.list.map((nom) =>
            <button onClick={() => this.chargerQuestionnaire(nom)} style={style.item}>
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
                    <h2 style={style.titleStyle}>Choisissez un questionnaire</h2>
                    <ul ref={this.liste} id="list" className="list-group" style={style.liste}>
                        {listItems}
                    </ul> 
                </div>
            );
        }
    }
}

const style = {
    titleStyle  : {
        color :"#000",
        textAlign : "center"
    },
    liste : {
        padding : "5px"
    },
    item : {
        background: "transparent",
        border: "transparent",
        textAlign : "left"
    }
};

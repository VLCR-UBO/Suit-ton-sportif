import Header from './components/layout/Header';
import Questionnaire from './components/Questionnaire';
import React, {Component} from 'react';
import Login from './components/Login';
import Question from './components/Question';
import Resultat from './components/Resultat';
import './App.css';

class App extends Component {
  constructor(props){
    super(props);
    this.state = {
      charger : 'question'
    }
  }

  changerPage = (valeur) => {
    this.setState({
      charger : valeur
    });
  }

  render(){
    if(this.state.charger === 'connexion'){
      return (
        <div className="App">
          <Header />
          <Login />
        </div>
      );
    }else if(this.state.charger === 'questionnaire'){
      return(
        <Questionnaire/>
      );
    }else if(this.state.charger === 'resultat'){
      return(
        <Resultat/>
      );
    }
    else if(this.state.charger === 'question'){
      return(
        <Question/>
      );
    }
  }
}

export default App;

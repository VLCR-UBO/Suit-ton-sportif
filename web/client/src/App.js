import Header from './components/layout/Header';
import Questionnaire from './components/Questionnaire';
import React, {Component} from 'react';
import Login from './components/Login';
import Resultat from './components/Resultat';
import './App.css';

class App extends Component {
  constructor(props){
    super(props);
    this.state = {
      charger : 'connexion'
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
  }
}

export default App;

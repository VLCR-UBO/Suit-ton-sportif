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
		charger : 'resultat',
		questionnaire : null,
		nbQuestion : -1,
		question : null,
		reponses : null,
		idSportif : -1
		}
	}

	changerPage = (valeur) => {
		this.setState({
		charger : valeur
		});
	}

	chargerSportif = (id) => {
		this.setState({
		idSportif : id
		})
	}

	chargerQuestionnaire = (nom) => {
		this.setState({
		questionnaire : nom
		});
	}

	chargerQuestion = (tab) => {
		this.setState({
		question : tab
		});
	}

	chargerReponse = (tab) => {
		this.setState({
		reponses : tab
		});
	}

	chargerNbQuestions = (nb) =>{
		this.setState({
			nbQuestion : nb
		});
	}

	render(){
		if(this.state.charger === 'connexion'){
		return (
			<div className="App">
			<Header />
			<Login 
				charger={this.changerPage}
				chargerSportif={this.chargerSportif}
			/>
			</div>
		);
		}else if(this.state.charger === 'questionnaire'){
		return(
			<Questionnaire 
				charger={this.changerPage} 
				nomQuestionnaire={this.chargerQuestionnaire}
			/>
		);
		}else if(this.state.charger === 'resultat'){
			return(
				<Resultat 
					charger={this.changerPage}
					sportif={this.state.idSportif}
					question={this.state.question}
					reponses={this.state.reponses}
				/>
			);
		}
		else if(this.state.charger === 'question'){
		return(
			<Question 
				charger={this.changerPage}
				nomQuestionnaire={this.state.questionnaire}
				chargerQuestion={this.chargerQuestion}
				chargerReponse={this.chargerReponse}
			/>
		);
		}
	}
}

export default App;

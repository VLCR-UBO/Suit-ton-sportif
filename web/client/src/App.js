import Header from './components/layout/Header';
import Questionnaire from './components/Questionnaire';
import React, {Component} from 'react';
import Login from './components/Login';
import Question from './components/Question';
import Resultat from './components/Resultat';
import Parametre from './components/Parametre';
import './App.css';

class App extends Component {
	constructor(props){
		super(props);
		this.state = {
		charger : 'connexion',
		questionnaire : null,
		nbQuestion : -1,
		question : [],
		reponses : [],
		numQuestion : 0,
		idSportif : -1,
		changement : false
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

	changerNumQuestion = (num) => {
		this.setState({
			numQuestion : num
		});
	}

	changerChangement = (ch) => {
		this.setState({
			changement : ch
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
			<div>
				<Parametre
					charger={this.changerPage} 
					chargerSportif={this.chargerSportif}
				/>
				<Questionnaire 
					charger={this.changerPage} 
					nomQuestionnaire={this.chargerQuestionnaire}
				/>
			</div>
			
		);
		}else if(this.state.charger === 'resultat'){
			return(
				<div>
					<Parametre
						charger={this.changerPage} 
						chargerSportif={this.chargerSportif}
					/>
					<Resultat 
						charger={this.changerPage}
						changerNumQuestion={this.changerNumQuestion}
						changerChangement={this.changerChangement}
						sportif={this.state.idSportif}
						question={this.state.question}
						reponses={this.state.reponses}
					/>
				</div>
			);
		}
		else if(this.state.charger === 'question'){
			return(
				<div>
					<Parametre
						charger={this.changerPage} 
						chargerSportif={this.chargerSportif}
					/>
					<Question 
						charger={this.changerPage}
						nomQuestionnaire={this.state.questionnaire}
						numQuestion={this.state.numQuestion}
						reponses={this.state.reponses}
						chargerQuestion={this.chargerQuestion}
						chargerReponse={this.chargerReponse}
						changerNumQuestion={this.changerNumQuestion}
						changerChangement={this.changerChangement}
						changement={this.state.changement}
					/>
				</div>
			);
		}
	}
}

export default App;

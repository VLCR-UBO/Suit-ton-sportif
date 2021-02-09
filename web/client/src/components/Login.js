import Axios from 'axios';
import React,{useState} from 'react';

export default function Login() {
    const [pseudo,setPseudo] = useState('');
    const [password , setPassword] = useState('');


    var onChange = (e) =>{
        if(e.target.name === "pseudo"){
            setPseudo(e.target.value);
        }
        else if(e.target.name === "password"){
            setPassword(e.target.value);

        }
    }

    var onSubmit = (e) =>{
        e.preventDefault();
        const user = {
            idUser : pseudo,
            passwordUser : password

        };

        Axios.post('/connexion/sportif',{user})
        .then(res =>{

            if ( res.data !=undefined ){
                switch (res.data) {
                    case "MDPINCORR":
                        alert("Le mot est entré est erronné, veuillez recommencer s'il vous plait");                  
                        break;
                    case "IDINCORR":
                        alert("L'identiant entré n'est pas reconnu dans la base, veuillez recommencer s'il vous plait");    
                        break;

                    default:
                        alert("Bonjour  : "+res.data.prenomSportif+" "+res.data.nomSportif);
                        /*Envoyer les données du sportif au questionnaire */ 
                        break;
                }
            }
        });

    };

        return (
        
            <div class="col-4">
                <div class="card" id="card">
                    <div class="card-header">
                        <center>Se connecter</center>
                    </div>
                    <div class="card-body">
                        <form onSubmit={onSubmit} >
                            <div class="form-group">
                                <center><label for="pseudo">Pseudo</label></center>
                                <input
                                    type="pseudo" 
                                    class="form-control" 
                                    id="pseudo" 
                                    name="pseudo"
                                    value={pseudo}
                                    placeholder = "Entrer votre pseudo"
                                    onChange = {onChange}
                                
                                />
                            </div>
    
                            <div class="form-group">
                                <center><label for="password">Mot de passe</label></center>
                                <input 
                                    type="password" 
                                    class="form-control" 
                                    id="password" 
                                    name="password"
                                    value = {password}
                                    placeholder = "Entrer votre mot de passe"
                                    onChange = {onChange}
    
                                />
    
    
                            </div>
    
                            <center><button type="submit" class="btn btn-primary">Se connecter</button></center>
                        </form>
                    </div>
                </div>
    
            </div>
    
        
        )};



const titleStyle = {
    color :"#000",
    textAlign : "center",
    padding : "#10 px"

};

import Axios from 'axios';
import React,{useState} from 'react';

const Login = (props) => {
    const [pseudo,setPseudo] = useState('');
    const [password , setPassword] = useState('');

    const changerPage = (event) => {
        props.page(event);

    }

    const changerUtilisateur = (event)=> {
        props.utilisateur(event);

    }


    const onChange = (e) =>{
        if(e.target.name === "pseudo"){
            setPseudo(e.target.value);
        }
        else if(e.target.name === "password"){
            setPassword(e.target.value);

        }
    }

    const onSubmit = (e) =>{
        e.preventDefault();
        const user = {
            idUser : pseudo,
            passwordUser : password

        };

        Axios.post('/connexion/sportif',{user})
        .then(res =>{
            if ( res.data !==undefined ){
                switch (res.data) {
                    case "MDPINCORR":
                        alert("Le mot est entré est erronné, veuillez recommencer s'il vous plait");                  
                        break;
                    case "IDINCORR":
                        alert("L'identiant entré n'est pas reconnu dans la base, veuillez recommencer s'il vous plait");    
                        break;

                    default:
                        console.log("Bonjour  : "+res.data.prenomSportif+" "+res.data.nomSportif);
                        changerUtilisateur(res.data);
                        changerPage('questionnaire');
                        break;
                }
            }
        });
    }

        return (        
            <div className="col-4">
                <div className="card" id="card">
                    <div className="card-header">
                        <center>Se connecter</center>
                    </div>
                    <div className="card-body">
                        <form onSubmit={onSubmit} >
                            <div className="form-group">
                                <center><label>Pseudo</label></center>
                                <input
                                    type="pseudo" 
                                    className="form-control" 
                                    id="pseudo" 
                                    name="pseudo"
                                    value={pseudo}
                                    placeholder = "Entrer votre pseudo"
                                    onChange = {onChange}
                                
                                />
                            </div>

                            <div className="form-group">
                                <center><label>Mot de passe</label></center>
                                <input 
                                    type="password" 
                                    className="form-control" 
                                    id="password" 
                                    name="password"
                                    value = {password}
                                    placeholder = "Entrer votre mot de passe"
                                    onChange = {onChange}

                                />
                            </div>

                            <center><button type="submit" className="btn btn-primary">Se connecter</button></center>
                        </form>
                    </div>
                </div>

            </div>
        );
    


}

const titleStyle = {
    color :"#000",
    textAlign : "center",
    padding : "#10 px"

};

export default Login;
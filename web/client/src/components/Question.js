import React, { useState } from 'react';

export default function Question(props) {
    const [numQuestion, setNumQuestion] = useState(props.numQuestion);
    const [questions, setQuestions] = useState(['a', 'b', 'c']);
    const [reponses, setReponses] = useState([]);
    const [pourcentage, setPourcentage] = useState("0%");
    const [changement, setChangement] = useState(props.changement);
    const nbQuestion = 3;

    var suivant = () => {
        reponses[numQuestion] = document.getElementById("oui").checked ? 1 : 0;
        setNumQuestion(numQuestion+1);
        setPourcentage(((((numQuestion+1) / nbQuestion) * 100).toFixed(2)) + "%");

        if(numQuestion === nbQuestion-1 || changement){
            props.chargerQuestion(questions);
            props.chargerReponse(reponses);
            props.charger('resultat');
        }
    }

    var precedent = () => {
        if(numQuestion > 0){
            setNumQuestion(numQuestion-1);
            setPourcentage(((((numQuestion+1) / nbQuestion) * 100).toFixed(2)) + "%");
        }
    }

    var retourChoixQuestionnaire = () =>{
        props.changerNumQuestion(0);
        props.changerChangement(false);
        props.charger('questionnaire');
    }

    //la faut charger la liste de question a partir de props.nomQuestionnaire

    var validation = changement ?
        <div style={{textAlign:"center"}}>
            <button onClick={suivant} style={style.bouton}>Valider</button>
        </div>
        :
        <div>
            <nav aria-label="navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item"><button onClick={precedent} style={style.bouton}>Précédente</button></li>
                    <li class="page-item"><button onClick={suivant} style={style.bouton}>Suivante</button></li>
                </ul>
            </nav>
            <br/>
            <center><h5>Complétion du questionnaire</h5></center>
            <div class="progress">
                <div class="progress-bar " style={{width:pourcentage, backgroundColor:"yellowGreen"}}>{pourcentage}</div>
            </div>
            <br/>
        </div>

    return (
        <body className="Question">
            <div class="col-4">
                <div className="container">
                    <div style={{textAlign : "right"}}>
                        <button style={style.lien} onClick={retourChoixQuestionnaire}>Retour à la liste des questionnaires</button>
                        <br/>
                    </div>

                        <center><h2>{questions[numQuestion]}</h2></center>
                        <br/><br/>

                        <center><div class="form-check-inline">
                            <label class="form-check-label">
                            <input type="radio" class="form-check-input" name="optradio" id="oui"/>OUI</label>
                            </div>
                            <div class="form-check-inline">
                            <label class="form-check-label">
                            <input type="radio" class="form-check-input" name="optradio" checked/>NON</label>
                        </div></center>
                        <br/><br/>

                        {validation}
                </div>
            </div>
        </body>
    )
};

const style = {
    bouton : {
        backgroundColor : "yellowgreen",
        color: "white",
        border: "none",
        padding: "10px",
        fontSize: "16px",
        marginLeft : "10px",
        marginRight : "10px"
    },
    lien : {
        border: "none",
        backgroundColor : "transparent",
        fontSize: "12px",
        textDecoration : "underline"
    }
};
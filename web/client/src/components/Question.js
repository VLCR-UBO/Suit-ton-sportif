import React, { useState } from 'react';

export default function Question(props) {
    const [numQuestion, setNumQuestion] = useState(0);
    const [questions, setQuestions] = useState(['a', 'b', 'c']);
    const [reponses, setReponses] = useState([]);
    const [pourcentage, setPourcentage] = useState("0%");
    const nbQuestion = 3;

    var suivant = () => {
        reponses[numQuestion] = document.getElementById("oui").checked ? 1 : 0;
        setNumQuestion(numQuestion+1);
        setPourcentage((((numQuestion+1) / nbQuestion) * 100) + "%");

        if(numQuestion === nbQuestion-1){
            props.chargerQuestion(questions);
            props.chargerReponse(reponses);
            props.charger('resultat');
        }
    }

    var precedent = () => {
        setNumQuestion(numQuestion-1);
        setPourcentage((((numQuestion+1)/ nbQuestion) * 100) + "%");
    }

    //la faut charger la liste de question a partir de props.nomQuestionnaire

    return (
        <body className="Question">
            <div class="col-4">
                <div class="card" id="card">
                    <div class="container">
                        
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

                        <nav aria-label="navigation">
                            <ul class="pagination justify-content-center">
                                <li class="page-item"><button onClick={precedent}>Précédente</button></li>
                                <li class="page-item"><button onClick={suivant}>Suivante</button></li>
                            </ul>
                        </nav>
                        <br/>
                        <center><h5>Complétion du questionnaire</h5></center>
                        <div class="progress">
                            <div class="progress-bar bg-success" style={{width:pourcentage}}>{pourcentage}</div>
                        </div>
                    </div>
                </div>

            </div>
        </body>
    
    )
};

import React from 'react';

export default function Question() {


    return (
        <body className="Question">
            <div class="col-4">
                <div class="card" id="card">
                    <div class="container">
                        <div class="progress">
                            <div class="progress-bar" style={{width:"70%"}}>70%</div>
                        </div>
                        <br/>
                        <center><h2>Nom de la question</h2></center>
                        <br/><br/>

                        <center><div class="form-check-inline">
                            <label class="form-check-label">
                            <input type="radio" class="form-check-input" name="optradio"/>OUI</label>
                            </div>
                            <div class="form-check-inline">
                            <label class="form-check-label">
                            <input type="radio" class="form-check-input" name="optradio"/>NON</label>
                        </div></center>
                        <br/><br/>
                               
                        <nav aria-label="navigation">
                            <ul class="pagination justify-content-center">
                                <li class="page-item"><a class="page-link" href="#">Précédente</a></li>
                                <li class="page-item"><a class="page-link" href="#">Suivante</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>

            </div>
           
        </body>
    
    )};

const titleStyle = {
    color :"#000",
    textAlign : "center",
    padding : "#10 px"

};

import React from 'react';

export default function Question() {
    return (
        <body className="Question">
            <div class="col-4">
                <div class="card" id="card">
                    <div class="container">
                        <div class="progress">
                            <div class="progress-bar" style="width:70%">70%</div>
                        </div>
                        <h2>Nom de la question</h2>
                                 
                        <nav aria-label="navigation">
                            <ul class="pagination">
                                <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                <li class="page-item"><a class="page-link" href="#">Next</a></li>
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

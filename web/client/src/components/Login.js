import React from 'react';

export default function Login() {
    return (
        <body className="Login">
            <div class="col-4">
                <div class="card" id="card">
                    <div class="card-header">
                        <center>Se connecter</center>
                    </div>
                    <div class="card-body">
                        <form action="/auth/login" method="post">
                            <div class="form-group">
                                <center><label for="pseudo">Pseudo</label></center>
                                <input type="pseudo" class="form-control" id="pseudo" name="pseudo"></input>
                            </div>

                            <div class="form-group">
                                <center><label for="password">Mot de passe</label></center>
                                <input type="password" class="form-control" id="password" name="password"></input>
                            </div>

                            <center><button type="submit" class="btn btn-primary">Se connecter</button></center>
                        </form>
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

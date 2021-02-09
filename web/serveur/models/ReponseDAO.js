const db = require('./connexionBDD.js');
const ReponseDAO = function(){

    this.insert = function(values, callback){
        var request = "INSERT INTO REPONSE VALUES(?, ?, ?, ?, ?)";
        const stmt = [values.idReponse, values.numeroSemaine, values.derniereModification, values.valeurReponse, values.unSportif ];
    }    
};

const dao = new ReponseDAO();
module.exports = dao;
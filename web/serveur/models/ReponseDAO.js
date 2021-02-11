const db = require('./connexionBDD.js');
const ReponseDAO = function(){

    this.insert = function(values, callback){
        var request = "INSERT INTO REPONSE VALUES(?, ?, ?, ?, ?,?)";
        const stmt = [values.idReponse, values.numeroSemaine, values.derniereModification, values.valeurReponse, values.unSportif, values.uneQuestion ];

        request = db.format(request,stmt);
        db.query(request, (err)=>{
            if(err)throw err;
        });
    } 
    
    this.findByKey = function(key, callback){
        db.query("SELECT * FROM REPONSE WHERE uneQuestion = ?", [key], callback);

    }
};

const dao = new ReponseDAO();
module.exports = dao;
const db = require('./connexionBDD.js');
const ReponseDAO = function(){

    this.insert = function(values, callback){
        var request = "INSERT INTO REPONSE VALUES(?, ?, ?, ?, ?,?)";
        const stmt = [values.idReponse, values.numeroSemaine, values.derniereModification, values.valeurReponse, values.unSportif, values.uneQuestion ];

        request = db.format(request,stmt);
        db.query(request, callback);
    } 

    this.update = function(key,key2 ,values, callback){
        var request = " UPDATE REPONSE SET derniereModification = ?, valeurReponse = ? WHERE uneQuestion = ? AND numeroSemaine = "+key2;
        const stmt = [values.derniereModification, values.valeurReponse];

        request = db.format(request, stmt);
        db.query(request,[key],callback);
    }
    
    this.findByKey = function(key,values,callback){
        db.query("SELECT * FROM REPONSE WHERE uneQuestion = ? AND numeroSemaine ="+values , [key], callback);

    }
};

const dao = new ReponseDAO();
module.exports = dao;
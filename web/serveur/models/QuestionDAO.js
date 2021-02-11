const db  = require('./connexionBDD.js');
const QuestionDAO = function(){

    this.findByKey = function(key, callback){
        db.query("SELECT * FROM QUESTION WHERE unQuestionnaire = ?", [key], callback);

    }
};
const dao = new QuestionDAO();
module.exports = dao;

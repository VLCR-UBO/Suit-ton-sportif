const db  = require('./connexionBDD.js');
const QuestionnDAO = function(){

    this.findByKey = function(key, callback){
        db.query("SELECT * FROM QUESTION WHERE unQuestionnaire = ?", [key], callback);
    }
    
};
const dao = new QuestionnaireDAO();
module.exports = dao;

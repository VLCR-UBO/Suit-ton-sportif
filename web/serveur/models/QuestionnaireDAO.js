const db = require('./connexionBDD.js');

const QuestionnaireDAO = function(){

    this.findByKey = function(callback){
        db.query("SELECT DISTINCT intituleQuestionnaire FROM QUESTIONNAIRE, QUESTION WHERE intituleQuestionnaire = unQuestionnaire",[],callback);

    }
};

const dao = new QuestionnaireDAO();
module.exports = dao;

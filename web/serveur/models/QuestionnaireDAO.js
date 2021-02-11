const db = require('./connexionBDD.js');

const QuestionnaireDAO = function(){

    this.findByKey = function(callback){
        db.query("SELECT * FROM QUESTIONNAIRE",[],callback);

    }
};

const dao = new QuestionnaireDAO();
module.exports = dao;

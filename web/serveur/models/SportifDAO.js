const db = require('./connexionBDD.js');
const SportifDAO = function(){

    this.findByKey = function(key, callback){
        db.query("SELECT * FROM SPORTIF WHERE pseudo = ?", [key], callback);
    };
};


const dao = new SportifDAO();

module.exports = dao;
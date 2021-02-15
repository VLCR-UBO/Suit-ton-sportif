const db = require('./connexionBDD.js');
const SportifDAO = function(){

    this.findByKey = function(key, callback){
        db.query("SELECT * FROM SPORTIF WHERE pseudo = ?", [key], callback);
    };

    this.update = function(key, values, callback){
        var request = "UPDATE motDePasseSportif = ? WHERE pseudo = ?";
        const stmt = [values.motDePasseSportif];

        request = db.format(request, stmt);
        db.query(request,[key],callback);
    };
};


const dao = new SportifDAO();

module.exports = dao;
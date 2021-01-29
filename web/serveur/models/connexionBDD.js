const mysql = require('mysql'); 

const db = mysql.createConnection({
    host : process.env.BDD_HOST,
    user : process.env.BDD_USER,
    password : process.env.BDD_PASSWORD,
    database: process.env.BDD_DATABASE
});

db.connect((err)=>{
    if(err) throw err;
    console.log("Connexion réussie ! ");
});

module.exports = db;
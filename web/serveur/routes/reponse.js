const express = require('express');
const router = express.Router();
const reponse_dao = require('../models/ReponseDAO.js');

router.get('/',(req,res)=>{

});

router.post('/', (req,res)=>{
    const nombreQuestion;
    const questions;
    var id;
    var numeroSemaine;
    var derniereModification;
    var valeurReponse;
    var unSportif;


    var i = 0;
    for(i; i<nombreQuestion; i++){
        id = question[i].idReponse;
        numeroSemaine = question[i].numeroSemaine;
        derniereModification = question[i].derniereModification;
        valeurReponse = question[i].valeurReponse;
        unSportif = question[i].unSportif;

        reponse_dao.insert({id,numeroSemaine,derniereModification, valeurReponse, unSportif},(err)=>{
            if(err) throw err;
        });
    }

});

module.exports = router;

const currentWeekNumber = require('current-week-number');
const express = require('express');
const router = express.Router();
const reponse_dao = require('../models/ReponseDAO.js');

router.get('/',(req,res)=>{

});

router.post('/insertion', (req,res)=>{

    const question = req.body.reponsesQuestionnaire.question;
    const numeroSemaine = currentWeekNumber();
    const date = req.body.reponsesQuestionnaire.date;
    const derniereModification = date.year+"-"+date.month+"-"+date.day;
    const valeurReponse = req.body.reponsesQuestionnaire.reponse;
    const unSportif = req.body.reponsesQuestionnaire.sportif;
    const id = 0;

    reponse_dao.findByKey(question,numeroSemaine,(err,rows)=>{
        if(err){
            console.log(err);
        }
        else{
            if(rows.length ===0){
                reponse_dao.insert({idReponse :id,numeroSemaine,derniereModification, valeurReponse, unSportif, uneQuestion : question },(err)=>{
                    if(err){
                        console.log(err);
                    }
                    else{
                         console.log("Insert");
                    }
                });
            }
            else{
                reponse_dao.update(question,numeroSemaine, {derniereModification, valeurReponse},(err)=>{
                    if(err){
                        console.log(err);
                    }
                    else{
                        console.log("Update");
                    }
                });
            }
        }
    });  
});

module.exports = router;

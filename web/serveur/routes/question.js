const { Router } = require('express');
const express = require('express');
const router = express.Router();
const question_dao = require('../models/QuestionDAO');


router.post('/listeQuestions',(req,res)=>{
    const nomQuestionnaire = req.body.nomQuestionnaire;
    console.log("Serveur Outside");
    console.log(nomQuestionnaire);
    question_dao.findByKey(nomQuestionnaire,(err,rows)=>{
        if(err){
            console.log(err);         
        }
        else{
            console.log("Serveur Inside");
            console.log(rows.length);
            if(rows.length !==0 ){
                res.send(rows);
                console.log("Serveur Ok");
            }
            else{
                res.send("LISTQUESTVIDE");

            }
        }
    });

});


router.get('/',(req,res)=>{




});

module.exports = router;
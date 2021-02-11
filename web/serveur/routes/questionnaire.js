const express = require('express');
const router = express.Router();
const questionnaire_dao = require('../models/QuestionnaireDAO');


router.get('/listeQuestionnaire',(req,res)=>{

    questionnaire_dao.findByKey((err, rows)=>{
        if(err){
            console.log(err);
        }
        else{
            if(rows.length !=0){
                res.send(rows);

            }
            else{
                res.send("QUESTVIDE");
            }

        }
    });
});

module.exports = router;
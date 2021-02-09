const express = require ('express');
const router = express.Router();
const sportif_dao = require('../models/SportifDAO');

router.get('/',function(req,res){



});

router.post('/sportif',function(req,res){
    const pseudo = req.body.user.idUser;
    const motDePasseSportif = req.body.user.passwordUser;
    console.log("Pseudo : "+pseudo);
    console.log("Mdp : "+motDePasseSportif);
    
    sportif_dao.findByKey(pseudo,(err,rows)=>{
        if(err){
            console.log(err);
        }
        else{
            if(rows.length > 0 ){
                if(rows[0].motDePasseSportif == motDePasseSportif){
                    console.log("Le sportif : "+rows[0].pseudo+" est connecté ! ");
                    console.log("Bonjour "+rows[0].prenomSportif+" "+rows[0].nomSportif);

                   res.send(rows[0]);

                }
                else{
                    console.log("Mot de passse incorrect");
                    res.send("MDPINCORR");
                }

            }
            else{
                console.log("Identifiant incorrect");
                res.send("IDINCORR");
            }   
        }
    });
    
});

module.exports = router;

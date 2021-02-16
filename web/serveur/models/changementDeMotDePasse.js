const express = require('express');
const router = express.Router();
const sportif_dao = require('../models/SportifDAO');

router.post('/',(req,res)=>{
    const pseudo = req.body.pseudo;
    const mdp = req.body.password;
    const mdpConf = req.body.passwordVerif;

    if(mdp === mdpConf){
        sportif_dao.update(pseudo,mdp,(err)=>{
            if(err){
                console.log(err);
            }
            else{
                console.log("Update");
                res.send("UPDATEMDPSUCCESS")
            }
        });

    }
    else{
        res.send("MDPNONEGALS");
    }

});
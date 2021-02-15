const express = require('express');
const router = express.Router();
const sportif_dao = require('../models/SportifDAO');

router.post('/', (req,res)=>{
    const pseudo = req.body.pseudo;

    sportif_dao.findByKey(pseudo, (err,rows)=>{
        if(err){
            console.log(err);
        }
        else{
            if(rows.length != 0 ){
                const resMdp = {
                    msg : "MDPSUCESS",
                    pseudo : rows[0].pseudo
                };

                res.send(resMdp);


            }
            else{
                res.send("MDPFAILED");

            }

        }



    });



});
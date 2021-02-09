const createError = require('http-errors');
const express = require('express');
const path = require('path');
const cookieParser = require('cookie-parser');
const logger = require('morgan');
const mysql = require('mysql'); 
require('dotenv').config();  


// Gestion des routes
const indexRouter = require('./routes/index');
const usersRouter = require('./routes/users');
const connexionRouter = require('./routes/connexion');



var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'jade');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

//Connexion à la base de donnée
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

app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/connexion',connexionRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;

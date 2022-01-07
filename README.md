# QAEngine
## A machine learning engine for ktor chatbot service

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger).


## How to use

---
First, add repository to your build.gradle file




       Kotlin Gradle Script & Gradle
       maven{
           url = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
       }

---
Second, add QA Engine dependency to build.gradle

1. Gradle
    
       implementation "io.github.ssu-isteam:QAEngine:1.0-SNAPSHOT"

2. Kotlin Gradle Script

       implementation("io.github.ssu-isteam:QAEngine:1.0-SNAPSHOT")



---

## Initialize QA Engine

> All examples are coded in Kotlin

1. Create dataset loader
:Currently supported dataset loader is MINDsLabDataSetLoader



      var loader = MINDsLabDataSetLoader("your_mindslab_dataset.json")
      
      //Also, you can add mutliple dataset loader.
      var loader2 = MINDsLabDataSetLoader("dataset2.json")


2. Prepare your network model and vocab cache path for TfidVectorizer

3. Initialize QAEngine and invoke initEngine() method with parameters prepared above.


       var engine = QAEngine()
       //initEngine has data set loader array parameters to load multiple datasets and merge them to one dataset.
       engine.initEngine(model_path,vocab_cache_path, loader,loader2....)
       
4. Well done! It's now time to get the answer of a question

      var answerText = engine.getAnswer(questionText)

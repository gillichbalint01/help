import express from 'express'
import { notes } from './data_notes.js'
import {v4} from 'uuid'

const PORT=3000
const app=express()
app.use(express.json())

//GET -összes
app.get('/notes',(req,res)=>{
    try {
        if(notes.length==0){
            return res.status(404).json({msg:"Nincs adat!"})
        }
    const sortedNotes=[...notes].sort((obj1,obj2)=>new Date(obj2.date)-new Date(obj1.date))
    res.status(200).send(sortedNotes)
    } catch (error) {
        res.status(500).json({msg:"Szerver hiba!"})
    }

})

//POST új jegyzet hozzáadása:
app.post('/notes',(req,res)=>{
    try {
        const {title,descr,date}=req.body
        /*const title=req.body.title
        const descr=req.body.descr
        const date=req.body.date*/
        if(!title || !descr || !date){
            return res.status(400).json({msg:"Minden adat megadása kötelező!"})
        }
        const existingNote=notes.find(obj=>obj.title==title)
        if(existingNote){
            return res.status(409).json({msg:"Már létezik ilyen című jegyzet!"})
        }
        const newNotes={id:v4(),title,descr,date}
        notes.push(newNotes)
        res.status(201).json({msg:"Sikeres hozzáadás!"})

    } catch (error) {
        res.status(500).json({msg:"Szerver hiba!"})
    }

})

//DELEte id alapján
app.delete('/notes/:id',(req,res)=>{
    try {
       const {id}=req.params
       console.log(id);
       
       const noteIndex=notes.findIndex(obj=>obj.id=== +id) 
       if(noteIndex==-1){
           res.status(404).json({msg:"Nem található a jegyzet!"})
       }
       notes.splice(noteIndex,1)
       res.status(200).json({msg:'Sikeres törlés!'})

    } catch (error) {
        res.status(500).json({msg:"Szerver hiba!"})
    }

})

//GET kérés : egyetlen jegyzet keresése title alapján, URL-ben érkezik az adat
//GET kérés : kulcsszavas keresés



app.listen(PORT,()=>console.log(`server listening on port:${PORT}`))
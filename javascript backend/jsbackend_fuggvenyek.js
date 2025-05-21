
// pnpm i express cors mysql után be is tudjuk importálni
import express from 'express'
import cors from 'cors'
import mysql from 'mysql2/promise'
//import { configDB } from './configDB.js'
// configDB az a fájl ahol leírjuk hogy melyik helyi sql adatbázishoz akarunk csatlakozni
// valahogy így kéne kinéznie:

export const configDB={
    host:'localhost',
    user:'root',
    password:'',
    database:'adatbázis-neve',
    multipleStatements:true
}

//megadjuk melyik porton fusson a szerver, mindegy mi 
const PORT=3000

// ezzel gyors csatlakozunk a configDBben megadott adatbázishoz
let connection
try {
    connection= await mysql.createConnection(configDB)
} catch (error) {
    console.log(error);
    
}

const app=express()
app.use(express.json())
app.use(cors())

//alap GET kérés:
app.get("/adatok", async ( req , res ) =>
{
    try {
        const sql = "SELECT * from adatok"
        const [rows, fields] = await connection.execute(sql)

        if ( rows.length < 1 ) { res.status(404).json({msg:"nincs adat"}) }
        else { res.send(rows) }

    } catch (error) {
        console.log(error);
        res.status(500).json({msg:"valami hiba történt"})
    }
})

//alap POST kérés:
app.post("/adatok", async ( req , res ) => 
{

    const {adat} = req.body

    try {
        const sql = "insert into adatok (adat) values (?)"
        const values=[adat]
        const [rows,fields]=await connection.execute(sql,values)
        console.log(rows,fields);
        res.json({msg:"Sikeres hozzáadás!"})
        

    } catch (error) {
        console.log(error);
        res.status(500).json({msg:"valami hiba történt"})
    }    
})

//alap DELETE kérés:
app.delete("/adatok/:id", async ( req , res ) => 
{
    const {id}=req.params

    try {

        const sql="delete from adatok where id=?"
        const values=[id]
        const [rows]=await connection.execute(sql,values)
        console.log(rows.affectedRows);   
        res.json({msg:`${rows.affectedRows==0?"Nincs törlendő adat!":"Sikeres törlés!"}`})

    } catch (error) {
        console.log(error);
        res.status(500).json({msg:"valami hiba történt"})
    }
})

//alap PUT kérés 
app.put('/adatok/:id', async ( req , res ) =>
{
    const {id} = req.params
    const {adat} = req.params

    try {
        const sql = "update adatok set adat='?' where id=? "
        const values = [adat,id]
        const [rows]=await connection.execute(sql,values)
        res.json({msg:"sikeres frissítés"})

    } catch (error) {
        console.log(error);
        res.status(500).json({msg:"valami hiba történt"})
    }

})

//ez futtatja a szervert, mindig legalul kell lennie
app.listen(PORT,()=>console.log(`server running on port ${PORT}`))
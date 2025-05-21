import express from 'express';

import { body,validationResult } from 'express-validator';

const app = express();
const PORT = 3000;

let users = [
  { id: 1, username: "gyula", email: "gyula@example.com", password: "hashedpassword123" },
  { id: 2, username: "marci", email: "marcoi@example.com", password: "hashedpassword456" }
];

// Middleware
app.use(express.json());

// GET all users
app.get('/users', (req, res) => {
  res.status(200).json(users.map(({username,email})=>({username,email})));
});

// POST a new user
/*app.post('/users', (req, res) => {
  const { username, email, password } = req.body;

  // Validálás
  if (!username || !email || !password) {
    return res.status(400).json({ msg: "Minden mező kitöltése kötelező!" });
  }

  // Egyedi email ellenőrzés
  const existingUser = users.find(user => user.email === email);
  if (existingUser) {
    return res.status(400).json({ msg: "Ez az email már használatban van!" });
  }

  const newUser = {
    id: users.length + 1,
    username,
    email,
    password
  };

  users.push(newUser);
  res.status(201).json({ msg: "Felhasználó sikeresen létrehozva!" });
});
*/
app.post('/users',[body('username').isLength({min:5}),body('email').isEmail(),body('password').isLength({min:6})], (req, res) => {
  const errors=validationResult(req)
  if(!errors.isEmpty()){
    return res.status(400).json({errors:errors.array()})
  }
  const { username, email, password } = req.body;
  // Egyedi email ellenőrzés
  const existingUser = users.find(user => user.email === email);
  if (existingUser) {
    return res.status(400).json({ msg: "Ez az email már használatban van!" });
  }
  const newUser = {
    id: users.length + 1,
    username,
    email,
    password
  };

  users.push(newUser);
  res.status(201).json({ msg: "Felhasználó sikeresen létrehozva!" });
});

// PUT to update a user by ID
app.put('/users/:id', (req, res) => {
  const { username, email } = req.body;
  const user = users.find(u => u.id === +req.params.id);

  if (!user) {
    return res.status(404).json({ msg: "Nem található a felhasználó!" });
  }

  user.username = username || user.username;
  user.email = email || user.email;

  res.status(200).json({ msg: "Felhasználó frissítve!"});
});

// DELETE a user by ID
app.delete('/users/:id', (req, res) => {
  const userIndex = users.findIndex(u => u.id === +req.params.id);

  if (userIndex === -1) {
    return res.status(404).json({ msg: "Nem található a felhasználó!" });
  }

  users.splice(userIndex, 1);
  res.status(200).json({ msg: "Felhasználó törölve!" });
});

// Start the server
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});

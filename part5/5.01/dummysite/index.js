
const express = require("express");
const axios = require('axios');
require('dotenv').config();
const website_url = process.env.WEBSITE_URL;
//const website_url = "https://example.com/";

const app = express();
const port = process.env.PORT || "3000";

app.listen(port, () => {
    console.log(`Listening to requests on http://localhost:${port}`);
});


app.get("*", (req, res) => {
    axios({
        url: website_url,
        method: 'GET',
        responseType: 'text'
    }).then(response => {
        console.log(response.status);
        res.send(response.data)
    }).catch(err => {
        throw err
    });
});


const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const textToSpeech = require('@google-cloud/text-to-speech');
const fs = require('fs');
const util = require('util');

const app = express();
const port = 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// Google Cloud Client
const client = new textToSpeech.TextToSpeechClient({
    keyFilename: 'service-account.json' // Ganti dengan nama file kunci JSON Anda
});

// Endpoint untuk sintesis suara
app.post('/synthesize', async (req, res) => {
    const text = req.body.text;

    if (!text) {
        return res.status(400).send('Text is required');
    }

    const request = {
        input: { text: text },
        voice: { languageCode: 'id-ID', ssmlGender: 'NEUTRAL' },
        audioConfig: { audioEncoding: 'MP3' },
    };

    try {
        const [response] = await client.synthesizeSpeech(request);
        const audioFilePath = 'output.mp3';

        // Simpan audio ke file
        await util.promisify(fs.writeFile)(audioFilePath, response.audioContent, 'binary');
        console.log(`Audio content written to file: ${audioFilePath}`);

        res.sendFile(audioFilePath, { root: __dirname });
    } catch (error) {
        console.error('Error synthesizing speech:', error);
        res.status(500).send('Error synthesizing speech');
    }
});

// Jalankan server
app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
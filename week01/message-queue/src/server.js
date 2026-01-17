const express = require('express');
const mq = require('./rabbitmq');

const app = express();
app.use(express.json());

// API: Send email
app.post('/send-email', async (req, res) => {
    mq.send({ to: req.body.to, subject: req.body.subject });
    res.json({ message: 'Queued' });
});

// Worker: Process emails
async function startWorker() {
    await mq.connect();
    
    mq.consume(async (job) => {
        console.log('Processing:', job);
        await new Promise(r => setTimeout(r, 2000)); // simulate work
        console.log('Done:', job.to);
    });
}

startWorker();
app.listen(3000, () => console.log('Server: http://localhost:3000'));
const amqp = require('amqplib');

let channel;

async function connect() {
    const conn = await amqp.connect('amqp://guest:guest@localhost:5672');
    channel = await conn.createChannel();
    await channel.assertQueue('email_queue', { durable: true });
    console.log('RabbitMQ connected');
}

function send(data) {
    channel.sendToQueue('email_queue', Buffer.from(JSON.stringify(data)), { persistent: true });
}

function consume(handler) {
    channel.consume('email_queue', async (msg) => {
        const data = JSON.parse(msg.content);
        await handler(data);
        channel.ack(msg);
    });
}

module.exports = { connect, send, consume }; 



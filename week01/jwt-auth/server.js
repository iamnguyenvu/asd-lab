const express = require('express');
const jwt = require('jsonwebtoken');
const bcrypt = require('bcryptjs');

const app = express();
app.use(express.json());

const SECRET = 'your-secret-key';
// Mock users with hashed passwords
const users = [
    { id: 1, username: 'admin', password: bcrypt.hashSync('admin123', 10), role: 'admin' },
    { id: 2, username: 'user', password: bcrypt.hashSync('user123', 10), role: 'user' }
];

// Verify JWT token from request header
function authenticate(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];
    
    if (!token) {
        return res.status(401).json({ error: 'Authentication required' });
    }
    
    try {
        req.user = jwt.verify(token, SECRET);
        next();
    } catch (err) {
        res.status(403).json({ error: 'Invalid token' });
    }
}

// Check if user has required role
function authorize(...allowedRoles) {
    return (req, res, next) => {
        if (!allowedRoles.includes(req.user.role)) {
            return res.status(403).json({ error: 'Access denied' });
        }
        next();
    };
}

// Login endpoint - returns JWT token
app.post('/login', (req, res) => {
    const { username, password } = req.body;
    const user = users.find(u => u.username === username);
    
    if (!user || !bcrypt.compareSync(password, user.password)) {
        return res.status(401).json({ error: 'Invalid credentials' });
    }
    
    const payload = { id: user.id, username: user.username, role: user.role };
    const token = jwt.sign(payload, SECRET, { expiresIn: '1h' });
    
    res.json({
        token,
        user: { id: user.id, username: user.username, role: user.role }
    });
});

app.get('/', (req, res) => {
    res.json({ status: 'ok' });
});

// Get current user info
app.get('/profile', authenticate, (req, res) => {
    res.json({ user: req.user });
});

// Admin only endpoint
app.get('/admin', authenticate, authorize('admin'), (req, res) => {
    res.json({ users });
});

// User and admin can create posts
app.post('/posts', authenticate, authorize('user', 'admin'), (req, res) => {
    res.json({ message: 'Created', author: req.user.username });
});

app.listen(3001, () => console.log('Server running on port 3001'));

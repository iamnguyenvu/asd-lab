require('dotenv').config();
const express = require('express');
const { connectDatabase } = require('./config/database');
const { connectRedis } = require('./config/redis');
const productRoutes = require('./routes/products');
const { seedData } = require('./utils/seed');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Logging middleware
app.use((req, res, next) => {
  const start = Date.now();
  res.on('finish', () => {
    const duration = Date.now() - start;
    console.log(`${req.method} ${req.path} - ${res.statusCode} - ${duration}ms`);
  });
  next();
});

// Routes
app.get('/', (req, res) => {
  res.json({
    message: 'eCommerce Mini API - Performance Testing Lab',
    endpoints: {
      slow: '/api/products/slow - API không tối ưu (~170ms)',
      fast: '/api/products/fast - API tối ưu (~7ms)',
      all: '/api/products - Danh sách sản phẩm',
      category: '/api/products/category/:category',
      seed: '/api/seed - Tạo dữ liệu mẫu'
    },
    queryParams: {
      category: 'electronics, fashion, books, home',
      minPrice: 'Giá tối thiểu',
      maxPrice: 'Giá tối đa',
      sort: 'price_asc, price_desc'
    }
  });
});

app.use('/api/products', productRoutes);

// Seed data endpoint
app.post('/api/seed', async (req, res) => {
  try {
    const count = parseInt(req.query.count) || 100;
    await seedData(count);
    res.json({
      success: true,
      message: `Đã tạo ${count} sản phẩm mẫu`
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

// Start server
async function startServer() {
  try {
    console.log('🚀 Starting eCommerce Mini API...\n');
    
    // Connect to MongoDB
    await connectDatabase();
    
    // Connect to Redis (optional, để tăng performance)
    await connectRedis();
    
    app.listen(PORT, () => {
      console.log(`\n✅ Server running on http://localhost:${PORT}`);
      console.log('\n📊 Performance Testing Endpoints:');
      console.log(`   SLOW (no cache): http://localhost:${PORT}/api/products/slow`);
      console.log(`   FAST (cached):   http://localhost:${PORT}/api/products/fast`);
      console.log(`\n💡 Seed data: POST http://localhost:${PORT}/api/seed?count=100`);
    });
    
  } catch (error) {
    console.error('❌ Failed to start server:', error);
    process.exit(1);
  }
}

startServer();

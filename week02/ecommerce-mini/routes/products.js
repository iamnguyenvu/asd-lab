const express = require('express');
const router = express.Router();
const Product = require('../models/Product');
const { cacheMiddleware } = require('../middleware/cache');

/**
 * API KHÔNG TỐI ƯU - Chậm ~170ms
 * - Không có cache
 * - Query không tối ưu
 * - Không sử dụng index hiệu quả
 */
router.get('/slow', async (req, res) => {
  const startTime = Date.now();
  
  try {
    const { category, minPrice, maxPrice, sort } = req.query;
    
    // Query không tối ưu - không dùng index tốt
    let query = {};
    
    if (category) {
      query.category = category;
    }
    
    // Tìm tất cả rồi filter trong memory - RẤT CHẬM
    let products = await Product.find(query).lean();
    
    // Filter price trong memory thay vì database
    if (minPrice) {
      products = products.filter(p => p.price >= parseFloat(minPrice));
    }
    if (maxPrice) {
      products = products.filter(p => p.price <= parseFloat(maxPrice));
    }
    
    // Sort trong memory
    if (sort === 'price_asc') {
      products.sort((a, b) => a.price - b.price);
    } else if (sort === 'price_desc') {
      products.sort((a, b) => b.price - a.price);
    }
    
    // Giả lập thêm delay để đạt ~170ms
    await new Promise(resolve => setTimeout(resolve, 100));
    
    const duration = Date.now() - startTime;
    
    res.json({
      success: true,
      optimized: false,
      count: products.length,
      duration: `${duration}ms`,
      note: 'API không tối ưu - Không cache, query chậm',
      data: products.slice(0, 20) // Giới hạn 20 sản phẩm
    });
    
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

/**
 * API TỐI ƯU - Nhanh ~7ms
 * - Có Redis cache
 * - Query tối ưu với index
 * - Sử dụng lean() và select()
 */
router.get('/fast', cacheMiddleware(60), async (req, res) => {
  const startTime = Date.now();
  
  try {
    const { category, minPrice, maxPrice, sort } = req.query;
    
    // Query tối ưu - sử dụng index
    let query = {};
    
    if (category) {
      query.category = category;
    }
    
    // Filter price ở database level
    if (minPrice || maxPrice) {
      query.price = {};
      if (minPrice) query.price.$gte = parseFloat(minPrice);
      if (maxPrice) query.price.$lte = parseFloat(maxPrice);
    }
    
    // Sort mapping
    let sortOption = {};
    if (sort === 'price_asc') {
      sortOption = { price: 1 };
    } else if (sort === 'price_desc') {
      sortOption = { price: -1 };
    } else {
      sortOption = { createdAt: -1 };
    }
    
    // Query tối ưu: sử dụng index, lean(), select() chỉ lấy field cần thiết
    const products = await Product
      .find(query)
      .sort(sortOption)
      .limit(20)
      .select('name price category stock rating reviews imageUrl')
      .lean(); // Trả về plain object, nhanh hơn mongoose document
    
    const duration = Date.now() - startTime;
    
    res.json({
      success: true,
      optimized: true,
      count: products.length,
      duration: `${duration}ms`,
      note: 'API tối ưu - Cache + Index + Query optimization',
      data: products
    });
    
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

/**
 * Lấy danh sách products với pagination
 */
router.get('/', cacheMiddleware(30), async (req, res) => {
  try {
    const page = parseInt(req.query.page) || 1;
    const limit = parseInt(req.query.limit) || 10;
    const skip = (page - 1) * limit;

    const products = await Product
      .find()
      .sort({ createdAt: -1 })
      .skip(skip)
      .limit(limit)
      .lean();

    const total = await Product.countDocuments();

    res.json({
      success: true,
      data: products,
      pagination: {
        page,
        limit,
        total,
        pages: Math.ceil(total / limit)
      }
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

/**
 * Tìm kiếm sản phẩm theo category
 */
router.get('/category/:category', cacheMiddleware(60), async (req, res) => {
  try {
    const { category } = req.params;
    
    const products = await Product
      .find({ category })
      .sort({ rating: -1 })
      .limit(20)
      .lean();

    res.json({
      success: true,
      category,
      count: products.length,
      data: products
    });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

module.exports = router;

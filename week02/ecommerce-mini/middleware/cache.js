const { getRedisClient } = require('../config/redis');

/**
 * Middleware cache Redis - Tăng performance từ 170ms xuống 7ms
 * @param {number} duration - Thời gian cache (giây)
 */
function cacheMiddleware(duration = 300) {
  return async (req, res, next) => {
    // Chỉ cache GET requests
    if (req.method !== 'GET') {
      return next();
    }

    const redisClient = getRedisClient();
    
    // Nếu Redis không available, skip cache
    if (!redisClient || !redisClient.isOpen) {
      console.log('⚠️ Redis not available, skipping cache');
      return next();
    }

    try {
      // Tạo cache key từ URL và query params
      const cacheKey = `cache:${req.originalUrl || req.url}`;
      
      // Kiểm tra cache
      const cachedData = await redisClient.get(cacheKey);
      
      if (cachedData) {
        console.log(`✅ Cache HIT: ${cacheKey}`);
        const data = JSON.parse(cachedData);
        return res.json({
          ...data,
          cached: true,
          cacheKey
        });
      }

      console.log(`❌ Cache MISS: ${cacheKey}`);

      // Ghi đè res.json để cache response
      const originalJson = res.json.bind(res);
      res.json = async (body) => {
        // Lưu vào cache
        try {
          await redisClient.setEx(cacheKey, duration, JSON.stringify(body));
          console.log(`💾 Cached: ${cacheKey} (${duration}s)`);
        } catch (err) {
          console.error('Cache save error:', err);
        }
        
        return originalJson(body);
      };

      next();
    } catch (error) {
      console.error('Cache middleware error:', error);
      next();
    }
  };
}

module.exports = { cacheMiddleware };

# eCommerce Mini - Performance Testing Lab

**Môn:** Kiến trúc dự án  
**Tuần:** 2 - Bài tập 1: Scalability, Performance, Security  
**Sinh viên:** Nguyen Hoang Nguyen Vu

## 📝 Mô tả

Dự án demo đơn giản về tối ưu hóa performance cho API eCommerce, minh họa việc giảm thời gian response từ **~170ms xuống ~7ms** thông qua:

- ✅ **Redis Caching** - Cache kết quả truy vấn
- ✅ **Database Indexing** - Index các field thường xuyên query
- ✅ **Query Optimization** - Tối ưu MongoDB queries
- ✅ **Lean Queries** - Sử dụng `.lean()` để tăng tốc
- ✅ **Field Selection** - Chỉ lấy fields cần thiết

## 🏗️ Kiến trúc

```
ecommerce-mini/
├── config/
│   ├── database.js      # MongoDB connection
│   └── redis.js         # Redis connection
├── models/
│   └── Product.js       # Product schema với indexes
├── routes/
│   └── products.js      # API endpoints (slow vs fast)
├── middleware/
│   └── cache.js         # Redis caching middleware
├── utils/
│   └── seed.js          # Tạo dữ liệu mẫu
├── server.js            # Main server
├── test-performance.js  # Performance testing script
└── docker-compose.yml   # MongoDB + Redis containers
```

## 🚀 Cài đặt

### 1. Cài đặt dependencies

```bash
cd week02/ecommerce-mini
npm install
```

### 2. Khởi động MongoDB và Redis (Docker)

```bash
docker-compose up -d
```

### 3. Khởi động server

```bash
npm start
```

Server sẽ chạy tại: `http://localhost:3000`

### 4. Tạo dữ liệu mẫu

```bash
# Tạo 100 sản phẩm mẫu
curl -X POST http://localhost:3000/api/seed?count=100
```

Hoặc dùng browser/Postman: `POST http://localhost:3000/api/seed?count=100`

## 📊 Testing Performance

### Cách 1: Sử dụng script tự động

```bash
npm run test:performance
```

Script này sẽ chạy autocannon để test và so sánh:
- API SLOW (không tối ưu)
- API FAST (không cache)
- API FAST (có cache)

### Cách 2: Test thủ công

**API KHÔNG TỐI ƯU (~170ms):**
```bash
curl "http://localhost:3000/api/products/slow?category=electronics&minPrice=100&maxPrice=1000"
```

**API TỐI ƯU (~7ms):**
```bash
curl "http://localhost:3000/api/products/fast?category=electronics&minPrice=100&maxPrice=1000"
```

## 🎯 So sánh Performance

### ❌ API SLOW (Không tối ưu)

**Vấn đề:**
- Không có cache
- Query không sử dụng index hiệu quả
- Filter và sort trong memory (không phải database)
- Load toàn bộ documents rồi mới filter

**Kết quả:** ~170ms/request

### ✅ API FAST (Tối ưu)

**Cải tiến:**
1. **Redis Cache** - Cache 60 giây, lần gọi thứ 2+ chỉ mất ~5-7ms
2. **Database Indexes** - Index trên `category`, `price`, `rating`
3. **Query Optimization** - Filter ngay ở database level
4. **Lean Queries** - `.lean()` trả về plain objects
5. **Field Selection** - Chỉ lấy fields cần thiết

**Kết quả:**
- Lần đầu (cache miss): ~30-50ms
- Lần sau (cache hit): ~5-7ms
- **Cải thiện: 95%+ faster** 🚀

## 📈 Endpoints

| Endpoint | Method | Mô tả | Cache |
|----------|--------|-------|-------|
| `/` | GET | API documentation | ❌ |
| `/api/products/slow` | GET | API không tối ưu | ❌ |
| `/api/products/fast` | GET | API tối ưu | ✅ 60s |
| `/api/products` | GET | Danh sách sản phẩm (pagination) | ✅ 30s |
| `/api/products/category/:category` | GET | Lọc theo category | ✅ 60s |
| `/api/seed` | POST | Tạo dữ liệu mẫu | ❌ |

## 🔧 Query Parameters

- `category` - electronics, fashion, books, home, sports, toys
- `minPrice` - Giá tối thiểu
- `maxPrice` - Giá tối đa
- `sort` - price_asc, price_desc
- `page` - Trang (pagination)
- `limit` - Số items/trang

## 🛠️ Technologies

- **Node.js** + **Express** - Backend framework
- **MongoDB** + **Mongoose** - Database
- **Redis** - Caching layer
- **Docker** - Containerization
- **Autocannon** - Performance testing

## 📚 Kỹ thuật tối ưu đã áp dụng

### 1. Redis Caching
```javascript
// Cache middleware - tự động cache GET requests
router.get('/fast', cacheMiddleware(60), async (req, res) => {
  // Response sẽ được cache 60 giây
});
```

### 2. Database Indexing
```javascript
// Schema với indexes
productSchema.index({ category: 1, price: 1 });
productSchema.index({ rating: -1, reviews: -1 });
```

### 3. Query Optimization
```javascript
// ❌ Chậm - filter trong memory
let products = await Product.find();
products = products.filter(p => p.price >= minPrice);

// ✅ Nhanh - filter ở database
const products = await Product.find({
  price: { $gte: minPrice, $lte: maxPrice }
});
```

### 4. Lean & Select
```javascript
// ✅ Chỉ lấy fields cần thiết, trả về plain object
const products = await Product
  .find(query)
  .select('name price category')
  .lean();
```

## 📝 Kết luận

Dự án này minh họa việc áp dụng các kỹ thuật tối ưu performance cơ bản:

1. **Caching** - Giảm 95% thời gian response
2. **Indexing** - Tăng tốc độ query lên 10-100x
3. **Query Optimization** - Xử lý ở database thay vì application
4. **Data Selection** - Chỉ lấy data cần thiết

**Kết quả:** Response time giảm từ **~170ms → ~7ms** (24x nhanh hơn) ⚡

## 🔗 Resources

- [MongoDB Indexing Best Practices](https://www.mongodb.com/docs/manual/indexes/)
- [Redis Caching Strategies](https://redis.io/docs/manual/patterns/)
- [Node.js Performance Optimization](https://nodejs.org/en/docs/guides/simple-profiling/)

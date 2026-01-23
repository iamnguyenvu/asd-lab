# 🚀 HƯỚNG DẪN CHẠY VÀ TEST PERFORMANCE

## Bước 1: Cài đặt Dependencies

```powershell
cd t:\asd-lab\week02\ecommerce-mini
npm install
```

## Bước 2: Khởi động Database (MongoDB + Redis)

### Option A: Sử dụng Docker (Recommended)

```powershell
docker-compose up -d
```

Kiểm tra containers đang chạy:
```powershell
docker ps
```

### Option B: Cài đặt local (nếu không dùng Docker)

- Cài MongoDB: https://www.mongodb.com/try/download/community
- Cài Redis: https://redis.io/download (hoặc dùng WSL trên Windows)

## Bước 3: Khởi động Server

```powershell
npm start
```

Bạn sẽ thấy:
```
✅ MongoDB connected successfully
✅ Redis connected successfully
✅ Server running on http://localhost:3000
```

## Bước 4: Tạo dữ liệu mẫu (Seed Data)

**Option A: Dùng curl (PowerShell)**
```powershell
curl -Method POST http://localhost:3000/api/seed?count=100
```

**Option B: Dùng browser**
- Mở: http://localhost:3000/api/seed?count=100 (trong Postman hoặc browser extension)

**Option C: Dùng PowerShell Invoke-WebRequest**
```powershell
Invoke-WebRequest -Uri "http://localhost:3000/api/seed?count=100" -Method POST
```

## Bước 5: Test Performance

### Test tự động (Recommended)

```powershell
npm run test:performance
```

Kết quả sẽ hiển thị:
- ⏱️ Latency trung bình của từng API
- 📊 Số requests/giây
- 📈 So sánh % cải thiện

### Test thủ công

**Test API SLOW (không tối ưu):**
```powershell
# PowerShell
Invoke-WebRequest -Uri "http://localhost:3000/api/products/slow?category=electronics&minPrice=100&maxPrice=1000"

# hoặc dùng browser
http://localhost:3000/api/products/slow?category=electronics&minPrice=100&maxPrice=1000
```

**Test API FAST (tối ưu - lần đầu):**
```powershell
Invoke-WebRequest -Uri "http://localhost:3000/api/products/fast?category=electronics&minPrice=100&maxPrice=1000"
```

**Test API FAST (tối ưu - lần 2, có cache):**
```powershell
# Gọi lại lần nữa, sẽ thấy cached: true
Invoke-WebRequest -Uri "http://localhost:3000/api/products/fast?category=electronics&minPrice=100&maxPrice=1000"
```

## 📊 Kết quả mong đợi

### API SLOW (Không tối ưu)
```json
{
  "success": true,
  "optimized": false,
  "duration": "~170ms",
  "note": "API không tối ưu - Không cache, query chậm"
}
```

### API FAST (Tối ưu - Cache Miss)
```json
{
  "success": true,
  "optimized": true,
  "duration": "~30-50ms",
  "note": "API tối ưu - Cache + Index + Query optimization"
}
```

### API FAST (Tối ưu - Cache Hit)
```json
{
  "success": true,
  "optimized": true,
  "duration": "~5-7ms",
  "cached": true,
  "note": "API tối ưu - Cache + Index + Query optimization"
}
```

## 🎯 Demo Scenarios

### Scenario 1: So sánh trực tiếp

1. Gọi SLOW API 5 lần → Xem response time
2. Gọi FAST API 5 lần → Xem response time
3. So sánh kết quả

### Scenario 2: Test với load

```powershell
# Cài autocannon global
npm install -g autocannon

# Test SLOW API
autocannon -d 10 -c 10 http://localhost:3000/api/products/slow

# Test FAST API
autocannon -d 10 -c 10 http://localhost:3000/api/products/fast
```

### Scenario 3: Monitoring Redis Cache

Kết nối vào Redis CLI:
```powershell
docker exec -it ecommerce-redis redis-cli

# Xem tất cả cache keys
KEYS cache:*

# Xem nội dung 1 cache key
GET "cache:/api/products/fast?category=electronics&minPrice=100&maxPrice=1000"

# Xóa cache để test lại
FLUSHDB
```

## 🔍 Troubleshooting

### Lỗi: MongoDB connection failed

**Giải pháp:**
```powershell
# Kiểm tra MongoDB đang chạy
docker ps | findstr mongo

# Restart container
docker-compose restart mongodb
```

### Lỗi: Redis connection failed

**Giải pháp:**
```powershell
# Kiểm tra Redis đang chạy
docker ps | findstr redis

# Restart container
docker-compose restart redis

# Server vẫn hoạt động nhưng không có cache
```

### Lỗi: Port 3000 đã được sử dụng

**Giải pháp:**
```powershell
# Thay đổi port trong .env
PORT=3001
```

## 📝 Checklist Hoàn thành

- [ ] Cài đặt dependencies (`npm install`)
- [ ] Khởi động MongoDB + Redis (`docker-compose up -d`)
- [ ] Khởi động server (`npm start`)
- [ ] Seed dữ liệu (`POST /api/seed?count=100`)
- [ ] Test API SLOW (response ~170ms)
- [ ] Test API FAST lần 1 (response ~30-50ms)
- [ ] Test API FAST lần 2 (response ~5-7ms, cached: true)
- [ ] Chạy performance test (`npm run test:performance`)
- [ ] Screenshot kết quả so sánh
- [ ] Viết báo cáo

## 📸 Screenshots cần chụp cho báo cáo

1. ✅ Server khởi động thành công
2. ✅ Seed data thành công
3. ✅ API SLOW response time (~170ms)
4. ✅ API FAST response time (~7ms, cached)
5. ✅ Kết quả performance test (autocannon)
6. ✅ Redis cache keys

## 🎓 Giải thích cho báo cáo

### Kỹ thuật 1: Redis Caching
- **Trước:** Mỗi request đều query database
- **Sau:** Cache kết quả 60 giây trong Redis
- **Kết quả:** Response time giảm từ 50ms → 7ms

### Kỹ thuật 2: Database Indexing
- **Trước:** Full table scan
- **Sau:** Index trên category, price
- **Kết quả:** Query time giảm từ 100ms → 20ms

### Kỹ thuật 3: Query Optimization
- **Trước:** Lấy tất cả data, filter trong memory
- **Sau:** Filter ngay ở database với index
- **Kết quả:** Giảm memory usage và tăng tốc độ

### Tổng kết
**170ms → 7ms = Cải thiện 95.9% = 24x nhanh hơn** 🚀

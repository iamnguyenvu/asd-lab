const autocannon = require('autocannon');

console.log('🔥 Performance Testing Tool - eCommerce Mini API\n');

const baseUrl = 'http://localhost:3000';

async function runTest(name, url, duration = 10) {
  console.log(`\n${'='.repeat(60)}`);
  console.log(`📊 Testing: ${name}`);
  console.log(`🎯 URL: ${url}`);
  console.log(`⏱️  Duration: ${duration}s`);
  console.log('='.repeat(60));

  return new Promise((resolve, reject) => {
    const instance = autocannon({
      url: url,
      duration: duration,
      connections: 10,
      pipelining: 1,
      workers: 2
    }, (err, result) => {
      if (err) {
        reject(err);
      } else {
        resolve(result);
      }
    });

    autocannon.track(instance, {
      renderProgressBar: true,
      renderResultsTable: true
    });
  });
}

async function main() {
  try {
    // Test 1: API không tối ưu (slow)
    const slowResult = await runTest(
      'API KHÔNG TỐI ƯU (No Cache, Slow Query)',
      `${baseUrl}/api/products/slow?category=electronics&minPrice=100&maxPrice=1000`,
      10
    );

    console.log('\n⏱️  Average Latency (SLOW):', slowResult.latency.mean.toFixed(2), 'ms');
    console.log('📈 Requests/sec (SLOW):', slowResult.requests.average);

    // Đợi 3 giây
    console.log('\n⏳ Waiting 3 seconds before next test...\n');
    await new Promise(resolve => setTimeout(resolve, 3000));

    // Test 2: API tối ưu (fast) - Lần đầu không cache
    console.log('\n🔄 Running FAST API test (first run - no cache)...');
    const fastResultNoCache = await runTest(
      'API TỐI ƯU (Cache Miss First)',
      `${baseUrl}/api/products/fast?category=electronics&minPrice=100&maxPrice=1000`,
      10
    );

    console.log('\n⏱️  Average Latency (FAST - No Cache):', fastResultNoCache.latency.mean.toFixed(2), 'ms');
    console.log('📈 Requests/sec (FAST - No Cache):', fastResultNoCache.requests.average);

    // Đợi 2 giây
    await new Promise(resolve => setTimeout(resolve, 2000));

    // Test 3: API tối ưu với cache
    console.log('\n🔄 Running FAST API test (with cache)...');
    const fastResultCached = await runTest(
      'API TỐI ƯU (With Redis Cache)',
      `${baseUrl}/api/products/fast?category=electronics&minPrice=100&maxPrice=1000`,
      10
    );

    console.log('\n⏱️  Average Latency (FAST - Cached):', fastResultCached.latency.mean.toFixed(2), 'ms');
    console.log('📈 Requests/sec (FAST - Cached):', fastResultCached.requests.average);

    // So sánh kết quả
    console.log('\n\n' + '='.repeat(60));
    console.log('📊 PERFORMANCE COMPARISON RESULTS');
    console.log('='.repeat(60));
    
    console.log('\n🐌 SLOW API (No Optimization):');
    console.log(`   Average Latency: ${slowResult.latency.mean.toFixed(2)}ms`);
    console.log(`   Requests/sec: ${slowResult.requests.average.toFixed(2)}`);
    
    console.log('\n⚡ FAST API (No Cache):');
    console.log(`   Average Latency: ${fastResultNoCache.latency.mean.toFixed(2)}ms`);
    console.log(`   Requests/sec: ${fastResultNoCache.requests.average.toFixed(2)}`);
    console.log(`   Improvement: ${((slowResult.latency.mean - fastResultNoCache.latency.mean) / slowResult.latency.mean * 100).toFixed(1)}% faster`);
    
    console.log('\n🚀 FAST API (With Cache):');
    console.log(`   Average Latency: ${fastResultCached.latency.mean.toFixed(2)}ms`);
    console.log(`   Requests/sec: ${fastResultCached.requests.average.toFixed(2)}`);
    console.log(`   Improvement: ${((slowResult.latency.mean - fastResultCached.latency.mean) / slowResult.latency.mean * 100).toFixed(1)}% faster`);
    console.log(`   Speed Up: ${(slowResult.latency.mean / fastResultCached.latency.mean).toFixed(1)}x faster`);

    console.log('\n✅ Performance testing completed!\n');

  } catch (error) {
    console.error('❌ Error during testing:', error);
    process.exit(1);
  }
}

// Kiểm tra server đã chạy chưa
console.log('🔍 Checking if server is running...');
fetch(baseUrl)
  .then(() => {
    console.log('✅ Server is running, starting tests...\n');
    main();
  })
  .catch(() => {
    console.error('❌ Server is not running!');
    console.log('Please start the server first: npm start');
    process.exit(1);
  });

const Product = require('../models/Product');

const categories = ['electronics', 'fashion', 'books', 'home', 'sports', 'toys'];

const productNames = [
  'Laptop', 'Smartphone', 'Headphones', 'Camera', 'Monitor',
  'T-Shirt', 'Jeans', 'Sneakers', 'Jacket', 'Watch',
  'Novel', 'Textbook', 'Magazine', 'Comics', 'Cookbook',
  'Sofa', 'Table', 'Chair', 'Lamp', 'Bed',
  'Basketball', 'Football', 'Tennis Racket', 'Yoga Mat', 'Dumbbells',
  'Action Figure', 'Board Game', 'Puzzle', 'Doll', 'RC Car'
];

function randomElement(array) {
  return array[Math.floor(Math.random() * array.length)];
}

function randomNumber(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function randomPrice(min, max) {
  return Math.round((Math.random() * (max - min) + min) * 100) / 100;
}

async function seedData(count = 100) {
  try {
    // Xóa dữ liệu cũ
    await Product.deleteMany({});
    console.log('🗑️  Cleared existing products');

    const products = [];

    for (let i = 0; i < count; i++) {
      const category = randomElement(categories);
      const baseName = randomElement(productNames);
      
      products.push({
        name: `${baseName} ${randomElement(['Pro', 'Premium', 'Deluxe', 'Classic', 'Plus'])} ${i + 1}`,
        description: `High-quality ${baseName.toLowerCase()} for ${category}`,
        price: randomPrice(10, 2000),
        category: category,
        stock: randomNumber(0, 100),
        rating: randomPrice(3, 5),
        reviews: randomNumber(0, 500),
        imageUrl: `https://picsum.photos/seed/${i}/300/300`,
        createdAt: new Date(Date.now() - randomNumber(0, 365) * 24 * 60 * 60 * 1000)
      });
    }

    await Product.insertMany(products);
    console.log(`✅ Successfully seeded ${count} products`);
    
    return { success: true, count };
  } catch (error) {
    console.error('❌ Seed error:', error);
    throw error;
  }
}

module.exports = { seedData };

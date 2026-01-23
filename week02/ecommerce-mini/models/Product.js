const mongoose = require('mongoose');

const productSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true,
    index: true // Index để tăng performance tìm kiếm
  },
  description: String,
  price: {
    type: Number,
    required: true,
    index: true // Index cho việc sort và filter
  },
  category: {
    type: String,
    required: true,
    index: true // Index cho category filter
  },
  stock: {
    type: Number,
    default: 0
  },
  rating: {
    type: Number,
    default: 0,
    min: 0,
    max: 5
  },
  reviews: {
    type: Number,
    default: 0
  },
  imageUrl: String,
  createdAt: {
    type: Date,
    default: Date.now,
    index: true
  }
});

// Compound index cho queries phổ biến
productSchema.index({ category: 1, price: 1 });
productSchema.index({ rating: -1, reviews: -1 });

module.exports = mongoose.model('Product', productSchema);

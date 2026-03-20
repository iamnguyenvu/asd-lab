import { useState } from 'react';

const API_BASE = import.meta.env.VITE_GATEWAY_BASE || 'http://localhost:8084/api';

export default function App() {
  const [items, setItems] = useState([]);
  const [itemId, setItemId] = useState('1');
  const [orderId, setOrderId] = useState('');
  const [statusUpdate, setStatusUpdate] = useState('CONFIRMED');
  const [orderResult, setOrderResult] = useState(null);
  const [form, setForm] = useState({
    customerName: '',
    deliveryAddress: '',
    quantity: 1
  });

  const loadCatalog = async () => {
    const res = await fetch(`${API_BASE}/catalog/items`);
    const data = await res.json();
    setItems(data);
  };

  const createOrder = async (e) => {
    e.preventDefault();
    const res = await fetch(`${API_BASE}/orders`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        customerName: form.customerName,
        deliveryAddress: form.deliveryAddress,
        itemId: Number(itemId),
        quantity: Number(form.quantity)
      })
    });
    const data = await res.json();
    setOrderResult(data);
    setOrderId(data.id ?? '');
  };

  const trackOrder = async () => {
    if (!orderId) {
      return;
    }
    const res = await fetch(`${API_BASE}/orders/${orderId}`);
    const data = await res.json();
    setOrderResult(data);
  };

  const updateOrderStatus = async () => {
    if (!orderId) {
      return;
    }
    const res = await fetch(`${API_BASE}/orders/${orderId}/status?status=${statusUpdate}`, {
      method: 'PATCH'
    });
    const data = await res.json();
    setOrderResult(data);
  };

  return (
    <main className="container">
      <h1>Online Food Delivery - Microservices</h1>
      <p>Gateway + Catalog Service + Order Service + PostgreSQL</p>

      <section className="card">
        <h2>1. View Catalog</h2>
        <button onClick={loadCatalog}>Load Catalog</button>
        <ul>
          {items.map((item) => (
            <li key={item.id}>
              #{item.id} - {item.name} ({item.price} VND)
            </li>
          ))}
        </ul>
      </section>

      <section className="card">
        <h2>2. Place Order</h2>
        <form onSubmit={createOrder}>
          <input
            placeholder="Customer name"
            value={form.customerName}
            onChange={(e) => setForm({ ...form, customerName: e.target.value })}
            required
          />
          <input
            placeholder="Delivery address"
            value={form.deliveryAddress}
            onChange={(e) => setForm({ ...form, deliveryAddress: e.target.value })}
            required
          />
          <input
            placeholder="Item ID"
            value={itemId}
            onChange={(e) => setItemId(e.target.value)}
            required
          />
          <input
            type="number"
            min="1"
            value={form.quantity}
            onChange={(e) => setForm({ ...form, quantity: e.target.value })}
            required
          />
          <button type="submit">Create Order</button>
        </form>
      </section>

      <section className="card">
        <h2>3. Track and Update Order</h2>
        <div className="row">
          <input
            placeholder="Order ID"
            value={orderId}
            onChange={(e) => setOrderId(e.target.value)}
          />
          <button onClick={trackOrder}>Track</button>
        </div>
        <div className="row">
          <select value={statusUpdate} onChange={(e) => setStatusUpdate(e.target.value)}>
            <option value="CONFIRMED">CONFIRMED</option>
            <option value="PREPARING">PREPARING</option>
            <option value="DELIVERING">DELIVERING</option>
            <option value="DELIVERED">DELIVERED</option>
          </select>
          <button onClick={updateOrderStatus}>Update Status</button>
        </div>
        {orderResult && <pre>{JSON.stringify(orderResult, null, 2)}</pre>}
      </section>
    </main>
  );
}

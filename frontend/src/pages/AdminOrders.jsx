import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  getAllOrders,
  confirmOrder,
  updateOrderStatus,
  cancelOrder,
} from "../services/orderService";
import "../scss/AdminOrders.scss";

function AdminOrders() {
  const navigate = useNavigate();

  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(false);

  const loadOrders = async () => {
    try {
      const response = await getAllOrders();
      setOrders(response.data);
    } catch (error) {
      console.error("Error fetching orders:", error);
    }
  };

  useEffect(() => {
    loadOrders();
  }, []);

  const handleConfirm = async (orderId) => {
    try {
      setLoading(true);

      await confirmOrder(orderId);

      await loadOrders();

      alert("Order confirmed successfully");
    } catch (error) {
      console.error("Error confirming order:", error);
      alert("Failed to confirm order");
    } finally {
      setLoading(false);
    }
  };

  const handleDelivered = async (orderId) => {
    try {
      setLoading(true);

      await updateOrderStatus(orderId, "DELIVERED");

      await loadOrders();

      alert("Order marked as delivered");
    } catch (error) {
      console.error("Error updating order:", error);
      alert("Failed to update order");
    } finally {
      setLoading(false);
    }
  };

  const handleCancel = async (orderId) => {
    const confirmed = window.confirm(
      "Are you sure you want to cancel this order?",
    );

    if (!confirmed) {
      return;
    }

    try {
      setLoading(true);

      await cancelOrder(orderId);

      await loadOrders();

      alert("Order cancelled successfully");
    } catch (error) {
      console.error("Error cancelling order:", error);
      alert("Failed to cancel order");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="admin-orders-page">
      <div className="admin-orders-header">
        <div>
          <h1>Customer Orders</h1>
          <p>View and manage all FreshNest orders</p>
        </div>

        <button type="button" onClick={() => navigate("/admin")}>
          Back to Dashboard
        </button>
      </div>

      {orders.length === 0 ? (
        <div className="empty-orders">
          <h2>No Orders Found</h2>
          <p>Customer orders will appear here.</p>
        </div>
      ) : (
        <div className="admin-orders-list">
          {orders.map((order) => (
            <div className="admin-order-card" key={order.orderId}>
              <div className="order-header">
                <div>
                  <h2>Order #{order.orderId}</h2>

                  <p>{new Date(order.orderDate).toLocaleDateString()}</p>
                </div>

                <span className="order-status">{order.orderStatus}</span>
              </div>

              <div className="customer-info">
                <span>Customer</span>

                <strong>{order.customerName || "Customer"}</strong>
              </div>

              <div className="order-items">
                {order.orderItems.map((item) => (
                  <div className="order-item" key={item.productId}>
                    <div>
                      <strong>{item.productName}</strong>

                      <span>Quantity: {item.quantity}</span>
                    </div>

                    <strong>₹{item.totalPrice}</strong>
                  </div>
                ))}
              </div>

              <div className="order-total">
                <span>Total Amount</span>

                <strong>₹{order.totalAmount}</strong>
              </div>

              <div className="order-actions">
                {order.orderStatus === "CREATED" && (
                  <button
                    type="button"
                    onClick={() => handleConfirm(order.orderId)}
                    disabled={loading}
                    className="confirm-button"
                  >
                    Confirm Order
                  </button>
                )}

                {order.orderStatus === "CONFIRMED" && (
                  <button
                    type="button"
                    onClick={() => handleDelivered(order.orderId)}
                    disabled={loading}
                    className="delivered-button"
                  >
                    Mark Delivered
                  </button>
                )}

                {(order.orderStatus === "CREATED" ||
                  order.orderStatus === "CONFIRMED") && (
                  <button
                    type="button"
                    onClick={() => handleCancel(order.orderId)}
                    disabled={loading}
                    className="cancel-button"
                  >
                    Cancel Order
                  </button>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default AdminOrders;

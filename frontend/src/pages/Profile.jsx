import { useNavigate } from "react-router-dom";
import "../scss/Profile.scss";

function Profile() {
  const navigate = useNavigate();

  const customer = JSON.parse(localStorage.getItem("customer"));

  const handleLogout = () => {
    localStorage.removeItem("customer");
    window.dispatchEvent(new Event("customerChanged"));
    navigate("/login");
  };

  return (
    <div className="profile-page">
      <div className="profile-header">
        <div>
          <h1>My Account</h1>
          <p>Manage your FreshNest account</p>
        </div>
      </div>

      <div className="profile-content">
        <div className="profile-card">
          <div className="profile-card-header">
            <div className="profile-avatar">
              {customer?.customerName?.charAt(0)?.toUpperCase() || "U"}
            </div>

            <div>
              <h2>{customer?.customerName || "Customer"}</h2>
              <p>{customer?.email || "No email available"}</p>
            </div>
          </div>

          <div className="profile-details">
            <div className="detail-item">
              <span className="detail-label">Full Name</span>
              <span className="detail-value">
                {customer?.customerName || "—"}
              </span>
            </div>

            <div className="detail-item">
              <span className="detail-label">Email</span>
              <span className="detail-value">{customer?.email || "—"}</span>
            </div>

            <div className="detail-item">
              <span className="detail-label">Phone Number</span>
              <span className="detail-value">{customer?.phone || "—"}</span>
            </div>
          </div>
        </div>

        <div className="account-options">
          <h2>Account</h2>

          <button type="button" onClick={() => navigate("/orders")}>
            <span className="option-icon">📦</span>

            <span className="option-content">
              <strong>My Orders</strong>
              <small>View your previous orders</small>
            </span>

            <span className="option-arrow">→</span>
          </button>

          <button type="button" onClick={() => navigate("/cart")}>
            <span className="option-icon">🛒</span>

            <span className="option-content">
              <strong>My Basket</strong>
              <small>View items in your cart</small>
            </span>

            <span className="option-arrow">→</span>
          </button>

          <button type="button" onClick={() => navigate("/")}>
            <span className="option-icon">🏠</span>

            <span className="option-content">
              <strong>Continue Shopping</strong>
              <small>Browse fresh products</small>
            </span>

            <span className="option-arrow">→</span>
          </button>

          <button
            type="button"
            onClick={handleLogout}
            className="logout-option"
          >
            <span className="option-icon">↪</span>

            <span className="option-content">
              <strong>Logout</strong>
              <small>Sign out of your account</small>
            </span>

            <span className="option-arrow">→</span>
          </button>
        </div>
      </div>
    </div>
  );
}

export default Profile;

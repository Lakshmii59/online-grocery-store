import "../scss/Footer.scss";

function Footer() {
  return (
    <footer className="footer">
      <div className="footer-content">
        <div className="footer-section">
          <h2>FreshNest</h2>
          <p>
            Fresh groceries and daily essentials delivered right to your
            doorstep.
          </p>
        </div>

        <div className="footer-section">
          <h3>Quick Links</h3>
          <p>Home</p>
          <p>Products</p>
          <p>Categories</p>
        </div>

        <div className="footer-section">
          <h3>Customer Support</h3>
          <p>My Account</p>
          <p>My Orders</p>
          <p>Contact Us</p>
        </div>

        <div className="footer-section">
          <h3>Contact</h3>
          <p>Email: support@freshnest.com</p>
          <p>Phone: +91 98765 43210</p>
        </div>
      </div>

      <div className="footer-bottom">
        <p>© 2026 FreshNest. All rights reserved.</p>
      </div>
    </footer>
  );
}

export default Footer;

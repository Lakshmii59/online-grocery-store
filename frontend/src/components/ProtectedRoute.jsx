import { Navigate } from "react-router-dom";

function ProtectedRoute({ children, role }) {
  const customer = JSON.parse(localStorage.getItem("customer"));

  if (!customer) {
    return <Navigate to="/login" replace />;
  }

  if (role && customer.role !== role) {
    return <Navigate to="/" replace />;
  }

  return children;
}

export default ProtectedRoute;

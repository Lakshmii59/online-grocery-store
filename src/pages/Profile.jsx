import { useNavigate } from "react-router-dom";

function Profile(){
    const navigate = useNavigate()
    const customer = JSON.parse(localStorage.getItem("customer"))

    const handleLogout = () => {
        localStorage.removeItem("customer")
        window.dispatchEvent(new Event("customerChanged"))
        navigate("/login")
    }

    return(
        <div className="profile">
            <h2>My Account</h2>
            <p><strong>Name :</strong>{customer?.customerName}</p>
            <p><strong>Email :</strong>{customer?.email}</p>
            <p><strong>Phone :</strong>{customer?.phone}</p>
            <hr/>
            <p>My Orders</p>
            <p>Contact Us</p>
            <p onClick={() => navigate("/cart")}>My Basket</p>
            <button onClick={handleLogout}>Logout</button>
        </div>
    )
}

export default Profile
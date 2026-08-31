import { BrowserRouter, Routes , Route} from "react-router-dom"
import Categories from "./pages/Categories"
import Products from "./pages/Products"
import { CartProvider } from "./CartContext"
import Profile from "./pages/Profile"
import Navbar from "./components/Navbar"
import Home from "./pages/Home"
import Cart from "./pages/Cart"
import Login from "./pages/Login"
import Checkout from "./pages/Checkout"
import Register from "./pages/Register"


function App() {
  
  return (
    <CartProvider>
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />}/>
        <Route path="/products" element={<Products />} />
        <Route path="/categories" element={<Categories/>} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/login" element={<Login />}/>
        <Route path="/register" element={<Register />}/>
        <Route path="/profile" element={<Profile />}/>
        <Route path="/checkout" element={<Checkout />}/>
      </Routes>    
    </BrowserRouter>
    </CartProvider>
  )
}

export default App

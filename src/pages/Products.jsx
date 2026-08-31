import { useEffect, useState } from "react"
import { getAllProducts } from "../services/productService"
import { useSearchParams } from "react-router-dom"
import ProductCard from "../components/ProductCard"
import appleImage from "../assets/apple.jpg"
import carrotImage from "../assets/carrot.jpg"
import spoonsImage from "../assets/spoons.jpg"
import "../scss/Products.scss"
                                                            
function Products(){
    const [products, setProducts] = useState([])
    const [searchParams] = useSearchParams()
    const categoryId = searchParams.get("category")
    const searchTerm = searchParams.get("search") || ""
    const productImages = {
        Apple : appleImage,
        Carrot : carrotImage,
        Spoon : spoonsImage,
    }

    useEffect(() => {
        getAllProducts().then((response) => {
            const allProducts = response.data

            let filteredProducts = allProducts

            if(categoryId) {
                filteredProducts = filteredProducts.filter(
                    (product) => product.categoryId == categoryId)
            }

            if (searchTerm){
                filteredProducts = filteredProducts.filter(
                    (product) => product.productName
                    .toLowerCase()
                    .includes(searchTerm.toLowerCase()))
            }

            setProducts(filteredProducts)
        })
        .catch((error) => {
            console.error("Error fetching products: ", error)
        })
    },[categoryId, searchTerm])

    return(
        <div className="products-container">
            <h1>Products</h1>
            <div className="products-grid">
                {products.map((product) => (
                <ProductCard key={product.productId} product={product} image={productImages[product.productName]}/>
            ))}
            </div>
        </div>
    )
}

export default Products
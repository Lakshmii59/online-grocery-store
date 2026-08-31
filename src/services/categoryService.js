import api from "./api";

export const getAllCategories = () => {
    return api.get("/api/categories")
}

export const getCategoryById = (categoryId) => {
    return api.get(`/api/categories/${categoryId}`)
}

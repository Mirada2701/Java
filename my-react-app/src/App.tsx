import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import HomePage from "./pages/HomePage";
import AboutPage from "./pages/AboutPage";
import ContactPage from "./pages/ContactPage";
import CategoryListPage from "./pages/category/CategoryListPage.tsx";
import CategoryCreatePage from "./pages/category/CategoryCreatePage.tsx";
import EditCategoryPage from "./pages/category/EditCategoryPage.tsx";
import EditProductPage from "./pages/product/EditProductPage.tsx";
import ProductListPage from "./pages/product/ProductListPage.tsx";
import CreateProductPage from "./pages/product/CreateProductPage.tsx";
import RegisterPage from "./pages/auth/RegisterPage.tsx";
import LoginPage from "./pages/auth/LoginPage.tsx";


const App: React.FC = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<HomePage />} />
                    <Route path="about" element={<AboutPage />} />
                    <Route path="contact" element={<ContactPage />} />
                    <Route path="categories">
                        <Route index element={<CategoryListPage />} />
                        <Route path="create" element={<CategoryCreatePage />} />
                        <Route path="edit/:id" element={<EditCategoryPage />} />
                    </Route>

                    <Route path="products">
                        <Route index element={<ProductListPage />} />
                        <Route path="create" element={<CreateProductPage />} />
                        <Route path="edit/:id" element={<EditProductPage />} />
                    </Route>

                    <Route path="register" element={<RegisterPage />} />
                    <Route path="login" element={<LoginPage />} />
                </Route>
            </Routes>
        </Router>
    );
};

export default App;